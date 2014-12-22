package il.ac.mta.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
		/*java.util.Date date = new java.util.Date();
	    date.setYear(2014);
	    date.setMonth(11); 
	    date.setDate(15);*/
		
		Calendar basicDate = Calendar.getInstance();
		basicDate.set(Calendar.ERA, GregorianCalendar.AD);
		basicDate.set(2014,11,15);
		java.util.Date date = basicDate.getTime();
		
		Calendar basicDate2 = Calendar.getInstance();
		basicDate2.set(Calendar.ERA, GregorianCalendar.AD);
		basicDate2.set(2014,12,23);
		java.util.Date newDate = basicDate2.getTime();
	    
	    Stock stock1 = new Stock("PIH", 10f, 8.5f,date);
		Stock stock2 = new Stock("AAL", 30f, 25.5f, date);
		Stock stock3 = new Stock("CAAS", 20f, 15.5f, date);
		Stock stock4 = new Stock(stock1);
		stock4.setStockSymbol("test");

		
		//Add the stocks to the portfolio:
		myPortfolio.addStock(stock1);
		myPortfolio.addStock(stock2);
		myPortfolio.addStock(stock3);
		myPortfolio.addStock(stock4);
		
		myPortfolio.updateBalance(10000f);
		
		myPortfolio.buyStock("PIH", 20);
		myPortfolio.buyStock("AAL", 30);
		myPortfolio.buyStock("CAAS", 40);
		myPortfolio.buyStock("test", 2);
		
		myPortfolio.getStocks()[0].setDate(newDate);
		
		// Requested actions from ex07.
		myPortfolio.sellStock("AAL", -1);
		myPortfolio.removeStock("CAAS");
		
		return myPortfolio;		
	}

}
