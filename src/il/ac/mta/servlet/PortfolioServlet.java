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
		
		//Portfolio newPort = new Portfolio(portfolio);
		
		resp.setContentType("text/html");
		resp.getWriter().println(portfolio.getHtmlString());
		/*resp.getWriter().println("</br>");
		resp.getWriter().println(newPort.getHtmlString());*/
		
	}
}
