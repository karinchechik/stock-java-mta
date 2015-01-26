package il.ac.mta.model;

import java.util.Date;

import il.ac.mta.model.Portfolio.ALGO_RECOMMENDATION;
/**
 * This is a class of stock status.
 * Stock status is an extend of Stock.
 * @author karin
 *
 */
public class StockStatus extends Stock{
	private ALGO_RECOMMENDATION recommendation;
	private int stockQuantity;
	
	/**
	 * c'tor of stockStatus:
	 */
	public StockStatus() {
		stockSymbol = "None";
		ask = 0;
		bid = 0;
		recommendation = ALGO_RECOMMENDATION.DO_NOTHING;
		stockQuantity = 0;
	}
	
	/**
	 * copy c'tor of stockStatus.
	 * @param stockStatus
	 */
	public StockStatus(StockStatus stockStatus)
	{
		this();
		setStockSymbol(stockStatus.stockSymbol);
		setAsk(stockStatus.ask);
		setBid(stockStatus.bid);
		this.date = new Date(stockStatus.date.getTime());
		setRecommendation(stockStatus.recommendation);
		setStockQuantity(stockStatus.stockQuantity);
	}
	
	//Getters & Setters:
	public ALGO_RECOMMENDATION getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
		this.recommendation = recommendation;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	
	public StockStatus(String stockSymbol, float ask, float bid, Date date) {
		this();
		
		setStockSymbol(stockSymbol);
		setAsk(ask);
		setBid(bid);
		setDate(new Date(date.getTime()));
	}

	public StockStatus(Stock stock) {
		this();
		setStockSymbol(stockSymbol);
		setAsk(ask);
		setBid(bid);
		setDate(new Date(date.getTime()));
	}
}
