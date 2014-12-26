package il.ac.mta.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import il.ac.mta.model.Portfolio;
import il.ac.mta.model.StockStatus;

public class PortfolioService {
	
	/**
	 * This method returns a new portfolio - updated.
	 * @return
	 */
	public Portfolio getPortfolio()
	{
		Portfolio myPortfolio = new Portfolio("Exercise 8 portfolio");
		
		//Date:
		Calendar basicDate = Calendar.getInstance();
		basicDate.set(Calendar.ERA, GregorianCalendar.AD);
		basicDate.set(2014,11,15);
		java.util.Date date = basicDate.getTime();
	    
		StockStatus stock1 = new StockStatus("PIH", 10f, 8.5f,date);
		StockStatus stock2 = new StockStatus("AAL", 30f, 25.5f, date);
		StockStatus stock3 = new StockStatus("CAAS", 20f, 15.5f, date);
		
		//Add the stocks to the portfolio:
		myPortfolio.addStockStatus(stock1);
		myPortfolio.addStockStatus(stock2);
		myPortfolio.addStockStatus(stock3);
		
		myPortfolio.updateBalance(10000f);
		
		myPortfolio.buyStock("PIH", 20);
		myPortfolio.buyStock("AAL", 30);
		myPortfolio.buyStock("CAAS", 40);
		
		// Requested actions from ex07:
		myPortfolio.sellStock("AAL", -1);
		myPortfolio.removeStock("CAAS");
		
		return myPortfolio;		
	}
}
