package il.ac.mta.model;

import java.util.Date;

import il.ac.mta.*;

public class Portfolio {
	
	public final int MAX_PORTFOLIO_SIZE = 5;
	
	private int portfolioSize = 0;
	public Stock[] stocks;
	public StockStatus[] stockStatus;
	public int i = 0;
	public String title = "portfolio";
	
	public Portfolio()
	{
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		stockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
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
		String str = "<h1>" + title + "</h1>" + "<br/>";
		
		for(i = 0; i < portfolioSize; i++)
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
