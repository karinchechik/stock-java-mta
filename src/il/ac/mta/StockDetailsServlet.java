package il.ac.mta;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StockDetailsServlet extends HttpServlet {
		
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		Stock stock1 = new Stock();
		Stock stock2 = new Stock();
		Stock stock3 = new Stock();
		
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
		
		resp.setContentType("text/html");
		resp.getWriter().println(stock1.getHtmlDescription());
		resp.getWriter().println("<br/>");
		resp.getWriter().println(stock2.getHtmlDescription());
		resp.getWriter().println("<br/>");
		resp.getWriter().println(stock3.getHtmlDescription());
	}
}
