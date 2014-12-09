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
		Portfolio myPortfolio = new Portfolio("portfolio");
		
		//Date:
		java.util.Date date = new java.util.Date();
	    date.setYear(2014);
	    date.setMonth(11); 
	    date.setDate(15);
	    
	    Stock stock1 = new Stock("PIH", 12.4f, 13.1f,date);
		Stock stock2 = new Stock("AAL", 5.5f, 5.78f, date);
		Stock stock3 = new Stock("CAAS", 31.5f, 31.2f, date);
		
		
		//Add the stocks to the portfolio:
		myPortfolio.addStock(stock1);
		myPortfolio.addStock(stock2);
		myPortfolio.addStock(stock3);
		
		return myPortfolio;		
	}

}
