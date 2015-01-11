package il.ac.mta.servlet;

import il.ac.mta.exception.BalanceException;
import il.ac.mta.exception.PortfolioFullException;
import il.ac.mta.exception.StockAlreadyExistsException;
import il.ac.mta.exception.StockNotExistException;
import il.ac.mta.model.Portfolio;
import il.ac.mta.service.PortfolioService;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class is used for adding output to the HTML page at the user's browser.
 */
@SuppressWarnings("serial")
public class PortfolioServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PortfolioService portfolioService = new PortfolioService();
		Portfolio portfolio;
		try {
			portfolio = portfolioService.getPortfolio();
			resp.setContentType("text/html");
			resp.getWriter().println(portfolio.getHtmlString());
			resp.getWriter().println("</br>");
		} catch (PortfolioFullException | StockAlreadyExistsException
				| StockNotExistException | BalanceException e) {
			resp.getWriter().println(e.getMessage());
			e.printStackTrace();
		}
			
	}
}
