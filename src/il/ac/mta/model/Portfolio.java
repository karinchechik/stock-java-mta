package il.ac.mta.model;

import java.util.Date;

import il.ac.mta.*;

public class Portfolio {
	
	public final int MAX_PORTFOLIO_SIZE = 5;
	
	private int portfolioSize;
	private Stock[] stocks;
	private StockStatus[] stockStatus;
	private String title;
	
	public Portfolio()
	{
		portfolioSize = 0;
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		stockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
		this.setTitle("New Portfolio");
	}

	public Portfolio(String title)
	{
		this();
		this.setTitle(title);
	}
	
	//copy constructor:
	public Portfolio(Portfolio portfolio)
	{
		this(portfolio.getTitle());
		setPortfolioSize(portfolio.portfolioSize);
		
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
	
	//METHOD:
	public void addStock(Stock stock)
	{
		if(portfolioSize < MAX_PORTFOLIO_SIZE)
		{
			stocks[portfolioSize] = stock;
			stockStatus[portfolioSize] = new StockStatus();
			portfolioSize++;
		}
	}
	
	public Stock[] getStocks()
	{
		return stocks;
	}
	
	public StockStatus[] getStockStatus() {
		return stockStatus;
	}
	
	public String getHtmlString()
	{
		String str = "<h1>" + getTitle() + "</h1>" + "<br/>";
		
		for(int i = 0; i < portfolioSize; i++)
		{
			str += stocks[i].getHtmlDescription() + "<br/>";
		}
		return str;
	}
	
	public class StockStatus{
		private final static int DO_NOTHING = 0;
		private final static int BUY = 1;
		private final static int SELL = 2;
		
		private String symbol;
		private float currentBid, currentAsk;
		private Date date;
		private int recommendation;
		private int stockQuantity;
		
		public StockStatus() {
			symbol = "Non";
			currentAsk = 0;
			currentBid = 0;
			date = new Date();
			recommendation = 0;
			stockQuantity = 0;
		}
		
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
