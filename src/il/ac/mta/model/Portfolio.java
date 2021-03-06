package il.ac.mta.model;

import il.ac.mta.exception.*;

import java.util.*;
/**
 * This is a class of portfolio.
 * Every portfolio can hold stocks.
 * @author karin
 *
 */
public class Portfolio {
	
	public static final int MAX_PORTFOLIO_SIZE = 5;
	public static final int SELL_ALL = -1;
	public static final int BUY_ALL = -1;
	
	private int portfolioSize;
	private StockStatus[] stockStatus;
	private String title;
	private float balance;
	
	public enum ALGO_RECOMMENDATION {
		DO_NOTHING, BUY, SELL;		
	}
	
	/**
	 *c'tor definition of portfolio.
	 *creates new portfolio and initializes it's members.
	 */
	public Portfolio()
	{
		portfolioSize = 0;
		stockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
		balance = 0;
		this.setTitle("New Portfolio");
	}

	/**
	 * c'tor second definition - receives a title.
	 * calls the first c'tor and adds a title.
	 * @param title
	 */
	public Portfolio(String title)
	{
		this();
		this.setTitle(title);
	}
	
	/**
	 * copy constructor.
	 * Receives an instance of portfolio and copies it.
	 * @param portfolio
	 * @throws BalanceException 
	 */
	public Portfolio(Portfolio portfolio) throws BalanceException
	{
		this(portfolio.getTitle());
		setPortfolioSize(portfolio.getPortfolioSize());
		
		for(int i = 0; i < portfolioSize; i++)
		{
			stockStatus[i] = new StockStatus(portfolio.getStockStatus()[i]);
		}

		updateBalance(portfolio.getBalance());
	}

	//Getters & Setters:
	public String getTitle(){
		return title;
	}
	
	public Portfolio(List<StockStatus> stockStatuses){
		this();
		int arraySize = stockStatuses.size();
		setPortfolioSize(arraySize);
		if(stockStatuses.size() > MAX_PORTFOLIO_SIZE)
		{
			arraySize = MAX_PORTFOLIO_SIZE;
		}
		for(int i=0; i < arraySize; i++)
		{
			stockStatus[i] = stockStatuses.get(i);
		}
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public int getPortfolioSize() {
		return portfolioSize;
	}

	public void setPortfolioSize(int portfolioSize) {
		this.portfolioSize = portfolioSize;
	}
	
	/**
	 * This method adds a new stock to the portfolio.
	 * @param stock
	 */
	public void addStock(Stock stock) throws PortfolioFullException, StockAlreadyExistsException
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stockStatus[i].getSymbol().equals(stock.getSymbol()))
			{
				throw new StockAlreadyExistsException(stock.getSymbol());
			}
		}
		
		if(portfolioSize < MAX_PORTFOLIO_SIZE)
		{
			this.stockStatus[portfolioSize] = new StockStatus();
			this.stockStatus[portfolioSize].setSymbol(stock.getSymbol());
			this.stockStatus[portfolioSize].setAsk(stock.getAsk());
			this.stockStatus[portfolioSize].setBid(stock.getBid());
			this.stockStatus[portfolioSize].setDate(new Date(stock.date.getTime()));
			if(stock instanceof StockStatus)
				this.stockStatus[portfolioSize].setRecommendation(((StockStatus) stock).getRecommendation());
			portfolioSize++;
		}
		else
		{
			throw new PortfolioFullException();
		}
	}
	
	public StockStatus[] getStocks(){
		return stockStatus;
	}

	
	/**
	 * This method receives a stock and removes it from the portfolio.
	 * @param stock
	 * @throws BalanceException 
	 * @throws IllegalQuantityException 
	 * @throws StockNotExistException 
	 */
	public void removeStock(String stockSymbol) throws StockNotExistsException, BalanceException, IllegalQuantityException
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stockStatus[i].getSymbol().equals(stockSymbol))
			{
				if(this.stockStatus[i].getStockQuantity() != 0)
				{
					sellStock(stockSymbol, SELL_ALL);
				}
				
				if(portfolioSize == 1)
				{
					this.stockStatus[i] = null;
				}
				else
				{
					this.stockStatus[i] = this.stockStatus[portfolioSize-1];
					this.stockStatus[portfolioSize-1] = null;
				}
				this.portfolioSize--;
				return;
			}
		}
		throw new StockNotExistsException(stockSymbol);
	}
	
	/**
	 * This method sells stock (from 1 to all the stocks). 
	 * @param symbol
	 * @param quantity
	 * @return
	 * @throws BalanceException 
	 * @throws IllegalQuantityException 
	 * @throws StockNotExistException 
	 */
	public void sellStock(String symbol, int quantity) throws StockNotExistsException, BalanceException, IllegalQuantityException
	{
		int tQuantity;
		int maxQuantity;
		if(quantity < -1)
		{
			throw new IllegalQuantityException();
		}
		
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stockStatus[i].getSymbol().equals(symbol))
			{
				maxQuantity = stockStatus[i].getStockQuantity();
				tQuantity = quantity;
				if(quantity == SELL_ALL)
				{
					tQuantity = maxQuantity;
				}

				updateBalance(tQuantity * stockStatus[i].getBid());
				stockStatus[i].setStockQuantity(stockStatus[i].getStockQuantity()-tQuantity);
				return;
			}
		}
		throw new StockNotExistsException(symbol);
	}

	/**
	 * This method buys stocks (from 1 to as many as possible according to the balance).
	 * @param symbol
	 * @param quantity
	 * @return
	 * @throws BalanceException 
	 * @throws IllegalQuantityException 
	 * @throws StockNotExistException 
	 */
	public void buyStock(String symbol, int quantity) throws BalanceException, StockNotExistsException, IllegalQuantityException
	{
		int maxQuantity; 
		int tQuantity;
		if(quantity < -1)
		{
			throw new IllegalQuantityException();
		}
		
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stockStatus != null && this.stockStatus[i].getSymbol().equalsIgnoreCase(symbol))
			{
				maxQuantity = (int)(balance / stockStatus[i].getAsk());
				tQuantity = quantity;
				if (quantity == BUY_ALL)
				{
					tQuantity = maxQuantity;
				}
				else if (quantity > maxQuantity){
					throw new BalanceException();
				}
				
				updateBalance(-tQuantity * stockStatus[i].getAsk());
				stockStatus[i].setStockQuantity(stockStatus[i].getStockQuantity()+tQuantity);
				
				return;
			}
		}
		throw new StockNotExistsException(symbol);
	}
	
	/**
	 * The get stocks value methods return the value of all the stocks in this portfolio.
	 * @return
	 */
	public float getStocksValue(StockStatus stockStatus[])
	{
		float sum = 0;
		for(int i=0; i < portfolioSize; i++)
		{
			sum += stockStatus[i].getStockQuantity() * stockStatus[i].getBid();
		}
		return sum;
	}
	
	public float getBalance(){
		return balance;
	}
	
	/**
	 * This method returns the value of the portfolio = balance + value of the stocks.
	 * @return
	 */
	public float getTotalValue()
	{
		return (getStocksValue(stockStatus) + getBalance());
	}
	
	public StockStatus[] getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * A method that updates the balance of the stock.
	 * @param amount
	 */
	public void updateBalance(float amount) throws BalanceException{
		if (balance + amount < 0){
			throw new BalanceException();
		}
		else
		{
			balance += amount;
		}
	}
	
	/**
	 * this method return an html string that includes the title of the portfolio
	 * and the description of it's stocks.
	 * @return
	 */
	public String getHtmlString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<div style=\"margin-left: auto;margin-right: auto;width: 90%;"
				+ "background-color: #FF5050 ;text-align:center;font-weight:bold;font-size:200%\">"
				+ getTitle()+ "</div><p>");
		sb.append("<b>Total Portfolio Value:</b> " + getTotalValue() +
				"$, <b>Total Stocks Value:</b> " + getStocksValue(stockStatus) +
				"$, <b>Balance:</b> " + getBalance() + "$<br/>");
		for(int i = 0; i < portfolioSize; i++)
		{
			sb.append(stockStatus[i].getHtmlDescription() + ", <b>Quantity:</b> " + stockStatus[i].getStockQuantity() +"<br/>");
		}
		return sb.toString();
	}

	public StockStatus findBySymbol(String symbol) {
		for(int i=0; i<portfolioSize; i++)
		{
			if(stockStatus[i] != null)
			{
				if(this.stockStatus[i].getSymbol().equalsIgnoreCase(symbol))
				{
					return this.stockStatus[i];
				}
			}
		}
		return null;
	}

}
