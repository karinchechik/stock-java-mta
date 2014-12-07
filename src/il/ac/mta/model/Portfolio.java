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
	}
	
	//copy constructor:
	public Portfolio(Portfolio portfolio)
	{
		this();
		
		for (int i = 0; i < stocks.length; i++) {
			stocks[i] = new Stock(portfolio.getStocks()[i]);
		}
		
		this.stockStatus = portfolio.stockStatus;
		this.setTitle(portfolio.getTitle());
	}
	
	//Getter to portfolio title:
	public String getTitle(){
		return title;
	}
	
	//Setter to portfolio title:
	public void setTitle(String title){
		this.title = title;
	}
	
	//METHOD:
	public void addStock(Stock stock)
	{
		if(portfolioSize < MAX_PORTFOLIO_SIZE)
		{
			stocks[portfolioSize] = stock;
			portfolioSize++;
		}
	}
	
	public Stock[] getStocks()
	{
		return stocks;
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
		
		public String symbol;
		public float currentBid, currentAsk;
		public Date date;
		public int recommendation;
		public int stockQuantity;

	}
}
