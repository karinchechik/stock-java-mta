package il.ac.mta.service;

import il.ac.mta.model.Portfolio;
import il.ac.mta.model.Stock;
import il.ac.mta.model.Portfolio.StockStatus;

public class PortfolioService {
	
	@SuppressWarnings("deprecation")
	
	/**
	 * This method returns a new portfolio - updated.
	 * @return
	 */
	public Portfolio getPortfolio()
	{
		Portfolio myPortfolio = new Portfolio("Exercise 7 portfolio");
		
		//Date:
		java.util.Date date = new java.util.Date();
	    date.setYear(2014);
	    date.setMonth(12); 
	    date.setDate(15);
	    
	    Stock stock1 = new Stock("PIH", 10f, 8.5f,date);
		Stock stock2 = new Stock("AAL", 30f, 25.5f, date);
		Stock stock3 = new Stock("CAAS", 20f, 15.5f, date);
		
		//Add the stocks to the portfolio:
		myPortfolio.addStock(stock1);
		myPortfolio.addStock(stock2);
		myPortfolio.addStock(stock3);
		
		myPortfolio.updateBalance(10000f);
		myPortfolio.buyStock("PIH", 20);
		myPortfolio.buyStock("AAL", 30);
		myPortfolio.buyStock("CAAS", 40);
		
		return myPortfolio;		
	}

}
