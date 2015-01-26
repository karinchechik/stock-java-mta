package il.ac.mta.servlet;

import il.ac.mta.dto.NasdaqSymbolDto;
import il.ac.mta.service.MarketService;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hanan.gitliz@gmail.com
 * @since Jan 12, 2015
 */
public class MarketServlet extends AbstractAlgoServlet {

	private static final long serialVersionUID = 1L;

	private MarketService marketService;

	@Override
	public void init() throws ServletException {
		super.init();
		marketService = MarketService.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/json");
		List<NasdaqSymbolDto> nasdaqSymbols = marketService.getNasdaqSymbols();
		resp.getWriter().print(withoutNullObjects().toJson(nasdaqSymbols));
	}
	
	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			resp.setContentType("application/json");
			String symbol = req.getParameter("symbol");
			portfolioService.addStock(symbol);
			resp.getWriter().print(withoutNullObjects().toJson(new StatusDto()));
		} catch (StockAlreadyExistsException | PortfolioFullException | StockNotExistsException e) {
			log.severe("Error: " + e.getMessage());
			resp.getWriter().print(e.getMessage());
		}
	}*/

}
