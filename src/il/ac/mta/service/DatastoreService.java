package il.ac.mta.service;

import il.ac.mta.exception.BalanceException;
import il.ac.mta.model.Portfolio;
import il.ac.mta.model.Portfolio.ALGO_RECOMMENDATION;
import il.ac.mta.model.Stock;
import il.ac.mta.model.StockStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;

/**
 * Handles all data persistence aspects.
 *
 * @author hanan.gitliz@gmail.com
 * @since Jan 5, 2015
 */
public class DatastoreService {

	private static final String SYMBOL = "symbol";
	private static final String DAY = "day";
	private static final String BID = "bid";
	private static final String ASK = "ask";
	private static final String RECOMMENDATION = "recommendation";
	private static final String STOCK_QUANTITY = "stockQuantity";
	private static final String NAMESPACE_STOCK = "stock";
	private static final String NAMESPACE_STOCK_SYMBOL = "stock_symbol";

	private static final String NAMESPACE_ACCOUNT = "account";
	private static final String BALANCE = "balance";
	private static final String PASSWORD = "password";
	private static final String USERNAME = "username";

	private static final String NAMESPACE_PORTFOLIO = "portfolio";
	private static final String TITLE = "title";
	private static final String PORTFOLIO_BALANCE = "balance";
	private static final String SYMBOL_LIST = "symbol_array";

	private final Logger log = Logger.getLogger(DatastoreService.class.getSimpleName()); 

	private static DatastoreService instance = new DatastoreService();

	public static DatastoreService getInstance() {
		return instance;
	}

	private DatastoreService() {}

	/**
	 * Get stock trend
	 * @param symbol the symbol of the stock.
	 * @param days trend days (from now back for number of days)
	 * @return a list of stocks, all of same symbol, one per day.
	 */
	public List<StockStatus> getStockHistory(String symbol, int days) {

		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter fSymbol = new FilterPredicate(SYMBOL, FilterOperator.EQUAL, symbol);
		final long oneDay = TimeUnit.DAYS.toMillis(1);
		final long daysAgo = System.currentTimeMillis() - oneDay*days;
		Filter fdays = new FilterPredicate(DAY, FilterOperator.GREATER_THAN_OR_EQUAL, daysAgo);

		Query q = new Query(NAMESPACE_STOCK).setFilter(CompositeFilterOperator.and(fSymbol, fdays)).addSort(DAY, SortDirection.DESCENDING);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		List<StockStatus> ret = new ArrayList<StockStatus>();
		for (Entity result : pq.asIterable()) {
			StockStatus stock = fromStockEntry(result);

			if(stock != null) ret.add(stock);
		}

		return ret;
	}

	/**
	 * Persist stock daily data.
	 */
	public void saveToDataStore(List<StockStatus> stockList) {
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		List<Entity> store = new LinkedList<Entity>();
		for (StockStatus stock : stockList) {
			store.add(stockToEntity(stock));
		}

		try {
			datastore.put(store);
		}catch(Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}
	
	public void saveStock(StockStatus stock) {
		try {
			com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			datastore.put(stockToEntity(stock));
		}catch(Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}

	/**
	 * This list contains all selected symbols.
	 * This method first deletes all namespace and than repopulate it with new symbols
	 * @param array - new selected symbols list.
	 */
	/*public void updateStockList(List<String> toPersist) {

		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		//delete persisted symbols
		Query q = new Query(NAMESPACE_STOCK_SYMBOL);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		List<Key> keys = new ArrayList<Key>();
		for (Entity result : pq.asIterable()) {
			keys.add(result.getKey());
		}

		datastore.delete(keys);

		//convert java list to google API entities.
		List<Entity> store = new LinkedList<Entity>();
		for (String s : toPersist) {
			Key key = KeyFactory.createKey(NAMESPACE_STOCK_SYMBOL, s);

			Entity entity = new Entity(key);
			entity.setProperty("id", s);

			store.add(entity);
		}

		//make store
		datastore.put(store);
	}*/

	/**
	 * get past marked symbols as selected.
	 */
	public List<String> fetchPersistedSymbols() {
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(NAMESPACE_STOCK_SYMBOL);

		PreparedQuery pq = datastore.prepare(q);

		List<String> ret = new ArrayList<String>();
		for (Entity result : pq.asIterable()) {
			String s = (String) result.getProperty("id");
			if(s != null) ret.add(s);
		}

		return ret;
	}

	/*public Account loadAccount() {
		Account account = new Account();
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey(NAMESPACE_ACCOUNT, 1);
		try {
			Entity entity = datastore.get(key);
			account.setUsername((String)entity.getProperty(USERNAME));
			account.setPassword((String)entity.getProperty(PASSWORD));
			Double balance = (Double)entity.getProperty(BALANCE);
			account.setBalance(balance.floatValue());
		} catch (EntityNotFoundException e) {
			//no account details found - create a new object and store it to db.
			account.setUsername("");
			account.setPassword("");
			account.setBalance(Account.DEFAULT_BALANCE);
			Entity entity = accountToEntity(account);
			datastore.put(entity);
		}

		return account;
	}*/

	public Portfolio loadPortfolilo() {
		Portfolio portfolio;
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Key key = KeyFactory.createKey(NAMESPACE_PORTFOLIO, 1);
		try {
			Entity entity = datastore.get(key);
			
			@SuppressWarnings("unchecked")
			List<String> symbolArray = (List<String>) entity.getProperty(SYMBOL_LIST);
			List<StockStatus> stockStatuses = new ArrayList<StockStatus>();
			if(symbolArray != null) {
				for (String symbol : symbolArray) {
					List<StockStatus> stockHistory = getStockHistory(symbol, 30);
					stockStatuses.add(stockHistory.get(0));
				}
				
				portfolio = new Portfolio(stockStatuses);
			}else {
				portfolio = new Portfolio();
			}
			
			portfolio.setTitle((String)entity.getProperty(TITLE));
			try {
				portfolio.updateBalance(((Double)entity.getProperty(PORTFOLIO_BALANCE)).floatValue());
			} 
			catch (BalanceException e) {
				//won't never happen
			}

		} catch (EntityNotFoundException e) {
			//no account details found - create a new object and store it to db.
			portfolio = new Portfolio();
			Entity entity = portfolioToEntity(portfolio);
			datastore.put(entity);
		}

		return portfolio;
	}

	/**
	 * <h3>The easiest way to update account:</h3>
	 * <ul>
	 * 	<li>use {@link #getAccount()} to get {@link Account} object.
	 * 	<li>update object with new data.
	 * 	<li>hand update object as argument to this method.
	 * </ul>
	 * @param updated
	 */
/*	public void updateAccount(Account updated) {
		updateEntity(accountToEntity(updated));
	}*/

	public void updatePortfolio(Portfolio portfolio) {
		updateEntity(portfolioToEntity(portfolio));
		updateStocks(Lists.newArrayList(portfolio.getStocks()));
	}

	private void updateEntity(Entity entity) {
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(entity);
	}

	private StockStatus fromStockEntry(Entity stockEntity) {
		StockStatus ret = new StockStatus();
		ret.setStockSymbol((String) stockEntity.getProperty(SYMBOL));
		ret.setAsk(((Double) stockEntity.getProperty(ASK)).floatValue());
		ret.setBid(((Double) stockEntity.getProperty(BID)).floatValue());
		ret.setDate((Date) stockEntity.getProperty(DAY));
		Long quantity = (Long) stockEntity.getProperty(STOCK_QUANTITY);
		ret.setStockQuantity(quantity.intValue());
		ret.setRecommendation(ALGO_RECOMMENDATION.valueOf((String) stockEntity.getProperty(RECOMMENDATION)));

		return ret;
	}
	
	private Entity stockToEntity(StockStatus stock) {
		Key parent = KeyFactory.createKey("date", stock.getDate().getTime());
		Key key = KeyFactory.createKey(parent, NAMESPACE_STOCK, stock.getSymbol());

		Entity entity = new Entity(key);
		entity.setProperty(SYMBOL, stock.getSymbol());
		entity.setProperty(ASK, stock.getAsk());
		entity.setProperty(BID, stock.getBid());
		entity.setProperty(DAY, stock.getDate());
		entity.setProperty(STOCK_QUANTITY, stock.getStockQuantity());
		entity.setProperty(RECOMMENDATION, stock.getRecommendation().name());
		
		return entity;
	}

	/*private Entity accountToEntity(Account account) {
		Entity entity = new Entity(NAMESPACE_ACCOUNT, 1);
		entity.setProperty(USERNAME, account.getUsername());
		entity.setProperty(PASSWORD, account.getPassword());
		entity.setProperty(BALANCE, account.getBalance());

		return entity;
	}*/

	private Entity portfolioToEntity(Portfolio portfolio) {
		Entity entity = new Entity(NAMESPACE_PORTFOLIO, 1);
		entity.setProperty(TITLE, portfolio.getTitle());
		entity.setProperty(PORTFOLIO_BALANCE, portfolio.getBalance());

		Stock[] stocks = portfolio.getStocks();
		List<String> symbols = new ArrayList<>();
		for (int i = 0; i < stocks.length; i++) {

			Stock stock = stocks[i];
			if(stock != null)
				symbols.add(stock.getSymbol());
		}

		entity.setProperty(SYMBOL_LIST, symbols);		
		return entity;
	}
	
	private void updateStocks(List<StockStatus> stockList) {
		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		
		for (StockStatus stockStatus : stockList) {
			updateStock(stockStatus);
		}

		List<Entity> store = new LinkedList<Entity>();
		for (StockStatus stock : stockList) {
			store.add(stockToEntity(stock));
		}

		try {
			datastore.put(store);
		}catch(Exception e) {
			log.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private void updateStock(StockStatus stockStatus) {

		com.google.appengine.api.datastore.DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Filter fSymbol = new FilterPredicate(SYMBOL, FilterOperator.EQUAL, stockStatus.getSymbol());
		final long oneDay = TimeUnit.DAYS.toMillis(1);
		final long daysAgo = System.currentTimeMillis() - oneDay*30;
		Filter fdays = new FilterPredicate(DAY, FilterOperator.GREATER_THAN_OR_EQUAL, daysAgo);

		Query q = new Query(NAMESPACE_STOCK).setFilter(CompositeFilterOperator.and(fSymbol, fdays)).addSort(DAY, SortDirection.DESCENDING);

		// Use PreparedQuery interface to retrieve results
		PreparedQuery pq = datastore.prepare(q);

		for (Entity entity : pq.asIterable()) {
			entity.setProperty(STOCK_QUANTITY, stockStatus.getStockQuantity());
			entity.setProperty(RECOMMENDATION, stockStatus.getRecommendation().name());
			datastore.put(entity);
			break;
		}
	}
}
