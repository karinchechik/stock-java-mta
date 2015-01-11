package il.ac.mta.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import il.ac.mta.exception.BalanceException;
import il.ac.mta.exception.PortfolioFullException;
import il.ac.mta.exception.StockAlreadyExistsException;
import il.ac.mta.exception.StockNotExistException;
import il.ac.mta.model.Portfolio;
import il.ac.mta.model.Stock;
import il.ac.mta.model.StockStatus;

/**
 * This class is used for making a portfolio and using it.
 * @author karin
 */
public class PortfolioService {
	
	/**
	 * This method returns a new portfolio - updated.
	 * @return
	 * @throws PortfolioFullException 
	 * @throws StockAlreadyExistsException
	 * @throws StockNotExistException 
	 * @throws BalanceException 
	 */
	public Portfolio getPortfolio() throws PortfolioFullException, StockAlreadyExistsException, StockNotExistException, BalanceException
	{
		Portfolio myPortfolio = new Portfolio("Exercise 8 portfolio");
		
		//Date:
		Calendar basicDate = Calendar.getInstance();
		basicDate.set(Calendar.ERA, GregorianCalendar.AD);
		basicDate.set(2014,11,15);
		java.util.Date date = basicDate.getTime();
	    
		Stock stock1 = new StockStatus("PIH", 10f, 8.5f,date);
		Stock stock2 = new StockStatus("AAL", 30f, 25.5f, date);
		Stock stock3 = new StockStatus("CAAS", 20f, 15.5f, date);
		
		//Add the stocks to the portfolio:
		myPortfolio.addStock(stock1);
		myPortfolio.addStock(stock2);
		myPortfolio.addStock(stock3);
		myPortfolio.addStock(stock3);
		
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
