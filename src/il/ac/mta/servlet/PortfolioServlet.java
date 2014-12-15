package il.ac.mta.servlet;

import il.ac.mta.model.Portfolio;
import il.ac.mta.model.Stock;
import il.ac.mta.service.PortfolioService;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortfolioServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PortfolioService portfolioService = new PortfolioService();
		Portfolio portfolio = portfolioService.getPortfolio();
		
		//unused for now: Stock[] stocks = portfolio.getStocks();
		
		//create a new portfolio that is a copy of the first portfolio.
		//Portfolio newPortfolio = new Portfolio(portfolio);
		//set a new title for the new portfolio.
		//newPortfolio.setTitle("Portfolio #2");
		
		//print the answer.
		resp.setContentType("text/html");
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("</br>");
		//resp.getWriter().println(newPortfolio.getHtmlString());
		
		//removes the first stock of portfolio.
		portfolio.removeStock(portfolio.getStocks()[0].getStockSymbol());
		
		//print again.
		resp.setContentType("text/html");
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("</br>");
		//resp.getWriter().println(newPortfolio.getHtmlString());
		
		//change the bid value of stock3 at the new portfolio.
		//newPortfolio.getStocks()[2].setBid(55.55f);
		
		//print again.
		resp.setContentType("text/html");
		resp.getWriter().println(portfolio.getHtmlString());
		resp.getWriter().println("</br>");
		//resp.getWriter().println(newPortfolio.getHtmlString());
	}
}
