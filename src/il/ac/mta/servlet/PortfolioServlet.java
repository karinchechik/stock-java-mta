package il.ac.mta.servlet;

import il.ac.mta.dto.PortfolioDto;
import il.ac.mta.dto.PortfolioTotalStatus;
import il.ac.mta.model.StockStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PortfolioServlet extends AbstractAlgoServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("application/json");
		
		PortfolioTotalStatus[] totalStatus = portfolioService.getPortfolioTotalStatus();
		StockStatus[] stockStatusArray = portfolioService.getPortfolio().getStocks();
		List<StockStatus> stockStatusList = new ArrayList<>();
		for (StockStatus ss : stockStatusArray) {
			if(ss != null)
				stockStatusList.add(ss);
		}
		
		PortfolioDto pDto = new PortfolioDto();
		pDto.setTitle(portfolioService.getPortfolio().getTitle());
		pDto.setTotalStatus(totalStatus);
		pDto.setStockTable(stockStatusList);
		resp.getWriter().print(withNullObjects().toJson(pDto));
	}
}
