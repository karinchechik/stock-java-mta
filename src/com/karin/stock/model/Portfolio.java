package com.karin.stock.model;

import java.util.Date;
import il.ac.mta.*;

public class Portfolio {
	public class StockStatus{
		public final int DO_NOTHING = 0;
		public final int BUY = 1;
		public final int SELL = 2;
		
		public String symbol;
		public float currentBid, currentAsk;
		public Date date;
		public int recommendation;
		public int stockQuantity;
			
			
	}
	public final int MAX_PORTFOLIO_SIZE = 5;
	
	int portfolioSize = 0;
	NewStock[] stocks;
	StockStatus[] stockStatus;
	
	public Portfolio()
	{
		stocks = new NewStock[MAX_PORTFOLIO_SIZE];
		stockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
	}
	
	//METHOD:
	public void addStock(NewStock stock)
	{
		stocks[portfolioSize] = stock;
		portfolioSize++;
	}
	
	public NewStock[] getStocks()
	{
		return stocks;
	}
}
