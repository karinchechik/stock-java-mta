package il.ac.mta.model;

import java.util.Date;

import il.ac.mta.*;

public class Portfolio {
	
	public final int MAX_PORTFOLIO_SIZE = 5;
	
	private int portfolioSize;
	private Stock[] stocks;
	private StockStatus[] stockStatus;
	private String title;
	
	/**
	 *c'tor definition of portfolio.
	 *creates new portfolio and initializes it's members.
	 */
	public Portfolio()
	{
		portfolioSize = 0;
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		stockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
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
		if(portfolioSize < MAX_PORTFOLIO_SIZE)
		{
			stocks[portfolioSize] = stock;
			stockStatus[portfolioSize] = new StockStatus();
			portfolioSize++;
		}
	}
	
	/**
	 * This method receives a stock and removes it from the portfolio.
	 * @param stock
	 */
	public void removeStock(Stock stock)
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stocks[i].getStockSymbol().equals(stock.getStockSymbol()))
			{
				if(i != portfolioSize-1 && portfolioSize > 1)
					for(int j = i; j < portfolioSize-1; j++)
					{
						this.stocks[j] = new Stock(this.stocks[j+1]);
					}
			}
		}
		this.portfolioSize--;
	}
	
	public Stock[] getStocks()
	{
		return stocks;
	}
	
	public StockStatus[] getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * this method return an html string that includes the title of the portfolio
	 * and the description of it's stocks.
	 * @return
	 */
	public String getHtmlString()
	{
		String str = "<h1>" + getTitle() + "</h1>" + "<br/>";
		
		for(int i = 0; i < portfolioSize; i++)
		{
			str += stocks[i].getHtmlDescription() + "<br/>";
		}
		return str;
	}
	
	/**
	 * inner class that saves the stock's status.
	 * @author karin
	 *
	 */
	public class StockStatus{
		private final static int DO_NOTHING = 0;
		private final static int BUY = 1;
		private final static int SELL = 2;
		
		private String symbol;
		private float currentBid, currentAsk;
		private Date date;
		private int recommendation;
		private int stockQuantity;
		
		/**
		 * c'tor of stockStatus.
		 */
		public StockStatus() {
			symbol = "None";
			currentAsk = 0;
			currentBid = 0;
			date = new Date();
			recommendation = 0;
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
			setDate(stockStatus.date);
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

		public int getRecommendation() {
			return recommendation;
		}

		public void setRecommendation(int recommendation) {
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
