package il.ac.mta.model;

import java.util.Date;
/**
 * This is a class of portfolio.
 * Every portfolio can hold stocks.
 * Every stock has stock status that is private to the portfolio holder.
 * @author karin
 *
 */
public class Portfolio {
	
	public final int MAX_PORTFOLIO_SIZE = 5;
	
	private int portfolioSize;
	private Stock[] stocks;
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
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
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
	 * Receives an instance and copies it.
	 * @param portfolio
	 */
	public Portfolio(Portfolio portfolio)
	{
		this(portfolio.getTitle());
		setPortfolioSize(portfolio.getPortfolioSize());
		
		for (int i = 0; i < portfolioSize; i++) {
			stocks[i] = new Stock(portfolio.getStocks()[i]);
		}
		
		for(int i = 0; i < portfolioSize; i++)
		{
			stockStatus[i] = new StockStatus(portfolio.getStockStatus()[i]);
		}
		updateBalance(portfolio.getBalance());
	}

	//Getter to portfolio title:
	public String getTitle(){
		return title;
	}
	
	//Setter to portfolio title:
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
	 * 
	 * @param stock
	 */
	public void addStock(Stock stock)
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stocks[i].getStockSymbol().equals(stock.getStockSymbol()))
			{
				return;
			}
		}
		
		if(portfolioSize < MAX_PORTFOLIO_SIZE)
		{
			stocks[portfolioSize] = stock;
			stockStatus[portfolioSize] = new StockStatus();
			stockStatus[portfolioSize].setSymbol(stock.getStockSymbol());
			stockStatus[portfolioSize].setCurrentAsk(stock.getAsk());
			stockStatus[portfolioSize].setCurrentBid(stock.getBid());
			stockStatus[portfolioSize].setDate(new Date(stock.date.getTime()));
			portfolioSize++;
		}
		else
		{
			System.out.println("Can't add new stock, portfolio can have only " + MAX_PORTFOLIO_SIZE + " stocks");
		}
	}
	
	/**
	 * This method receives a stock and removes it from the portfolio.
	 * @param stock
	 */
	public boolean removeStock(String stockSymbol)
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stocks[i].getStockSymbol().equals(stockSymbol))
			{
				if(this.stockStatus[i].getStockQuantity() != 0)
				{
					sellStock(stockSymbol, -1);
				}
				
				if(portfolioSize == 1)
				{
					this.stocks[i] = null;
					this.stockStatus[i] = null;
				}
				else
				{
					this.stocks[i] = this.stocks[portfolioSize-1];
					this.stockStatus[i] = this.stockStatus[portfolioSize-1];
					this.stocks[portfolioSize-1] = null;
					this.stockStatus[portfolioSize-1] = null;
				}
				this.portfolioSize--;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method sells stock (from 1 to all the stocks). 
	 * @param symbol
	 * @param quantity
	 * @return
	 */
	public boolean sellStock(String symbol, int quantity)
	{
		int tQuantity;
		int maxQuantity;
		if(quantity > -2 && quantity != 0) //-1 and above are valid values except 0
		{
			for(int i=0; i < portfolioSize; i++)
			{
				if(this.stocks[i].getStockSymbol().equals(symbol))
				{
					maxQuantity = stockStatus[i].getStockQuantity();
					tQuantity = quantity;
					if(quantity == -1)
					{
						tQuantity = maxQuantity;
					}
					else if(quantity > maxQuantity)
					{
						System.out.println("Not enough stocks to sell!");
						return false;
					}

					updateBalance(tQuantity * stockStatus[i].getCurrentBid());
					stockStatus[i].setStockQuantity(stockStatus[i].getStockQuantity()-tQuantity);
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * This method buys stocks (from 1 to as many as possible according to the balance).
	 * @param symbol
	 * @param quantity
	 * @return
	 */
	public boolean buyStock(String symbol, int quantity)
	{
		int maxQuantity; 
		int tQuantity;
		
		if(quantity > -2 && quantity != 0) //-1 and above are valid values except 0
		{
			for(int i=0; i < portfolioSize; i++)
			{
				if(this.stocks[i].getStockSymbol().equals(symbol))
				{
					maxQuantity = (int)(balance / stockStatus[i].getCurrentAsk());
					tQuantity = quantity;
					if (quantity == -1)
					{
						tQuantity = maxQuantity;
					}
					else if (quantity > maxQuantity){
						System.out.println("Not enough balance to complete purchase!");
						return false;
					}
					
					updateBalance(-tQuantity * stockStatus[i].getCurrentAsk());
					stockStatus[i].setStockQuantity(stockStatus[i].getStockQuantity()+tQuantity);
					
					return true;
				}
			}
		}
		return false;
	}
	
	//TODO: add java doc + bid or current bid?
	public float getStocksValue()
	{
		float sum = 0;
		for(int i=0; i < portfolioSize; i++)
		{
			sum += stockStatus[i].getStockQuantity() * stockStatus[i].getCurrentBid();
		}
		return sum;
	}
	
	public float getBalance(){
		return balance;
	}
	
	public float getTotalValue()
	{
		return getStocksValue() + getBalance();
	}
	
	public Stock[] getStocks()
	{
		return stocks;
	}
	
	public StockStatus[] getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * A method that updates the balance of the stock.
	 * @param amount
	 */
	public void updateBalance(float amount){
		if (balance + amount < 0){
			System.out.println("Error - Not enough balance to complete purchase!");
		}
		else
		{
			balance += amount;
			System.out.println("The amount was added/reduced successfully.");
		}
	}
	
	/**
	 * this method return an html string that includes the title of the portfolio
	 * and the description of it's stocks.
	 * @return
	 */
	public String getHtmlString()
	{
		String str = "<h1><font color=green>" + getTitle() + "</font></h1>" + "<br/>";
		str += "Total Portfolio Value: " + getTotalValue() +
				"$, Total Stocks Value: " + getStocksValue() +
				"$, Balance: " + getBalance() + "<br/>";
		for(int i = 0; i < portfolioSize; i++)
		{
			str += stocks[i].getHtmlDescription() + ", Quantity: " + stockStatus[i].getStockQuantity() +"<br/>";
		}
		return str;
	}
	
	/**
	 * inner class that saves the stock's status.
	 * @author karin
	 *
	 */
	public class StockStatus{
		
		private String symbol;
		private float currentBid, currentAsk;
		private Date date;
		private ALGO_RECOMMENDATION recommendation;
		private int stockQuantity;
		
		/**
		 * c'tor of stockStatus.
		 */
		public StockStatus() {
			symbol = "None";
			currentAsk = 0;
			currentBid = 0;
			recommendation = ALGO_RECOMMENDATION.DO_NOTHING;
			stockQuantity = 0;
		}
		
		/**
		 * copy c'tor of stockStatus.
		 * @param stockStatus
		 */
		public StockStatus(StockStatus stockStatus)
		{
			this();
			setSymbol(stockStatus.symbol);
			setCurrentAsk(stockStatus.currentAsk);
			setCurrentBid(stockStatus.currentBid);
			this.date = new Date(stockStatus.date.getTime());
			setRecommendation(stockStatus.recommendation);
			setStockQuantity(stockStatus.stockQuantity);
		}

		public String getSymbol() {
			return symbol;
		}

		public void setSymbol(String symbol) {
			this.symbol = symbol;
		}

		public float getCurrentBid() {
			return currentBid;
		}

		public void setCurrentBid(float currentBid) {
			this.currentBid = currentBid;
		}

		public float getCurrentAsk() {
			return currentAsk;
		}

		public void setCurrentAsk(float currentAsk) {
			this.currentAsk = currentAsk;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public ALGO_RECOMMENDATION getRecommendation() {
			return recommendation;
		}

		public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
			this.recommendation = recommendation;
		}

		public int getStockQuantity() {
			return stockQuantity;
		}

		public void setStockQuantity(int stockQuantity) {
			this.stockQuantity = stockQuantity;
		}

	}

}
