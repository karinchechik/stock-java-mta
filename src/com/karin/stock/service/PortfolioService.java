package com.karin.stock.service;

import com.karin.stock.model.Portfolio;

import il.ac.mta.*;

public class PortfolioService {
	
	public Portfolio getPortfolio()
	{
		Portfolio myPortfolio = new Portfolio();
		NewStock stock1 = new NewStock();
		NewStock stock2 = new NewStock();
		NewStock stock3 = new NewStock();
		
		//Date:
		java.util.Date date = new java.util.Date();
	    date.setYear(2014);
	    date.setMonth(11); 
	    date.setDate(15);
		
	    //stock1:
		stock1.setStockSymbol("PIH");
		stock1.setAsk(12.4f);
		stock1.setBid(13.1f);
		stock1.setDate(date);
		
		//stock2:
		stock2.setStockSymbol("AAL");
		stock2.setAsk(5.5f);
		stock2.setBid(5.78f);
		stock2.setDate(date);
		
		//stock3:
		stock3.setStockSymbol("CAAS");
		stock3.setAsk(31.5f);
		stock3.setBid(31.2f);
		stock3.setDate(date);
		
		//Add the stocks to the portfolio:
		myPortfolio.addStock(stock1);
		myPortfolio.addStock(stock2);
		myPortfolio.addStock(stock3);
		
		return myPortfolio;		
	}

}