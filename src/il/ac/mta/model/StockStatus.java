package il.ac.mta.model;

import java.util.Date;

import il.ac.mta.model.Portfolio.ALGO_RECOMMENDATION;

/**
 * This is a class of stock status.
 * StockStatus extends Stock.
 * @author karin
 *
 */
public class StockStatus extends Stock {

	private ALGO_RECOMMENDATION recommendation;
	private int stockQuantity;
	
	/**
	 * c'tor of stockStatus - initiates the members.
	 */
	public StockStatus(){
		this.symbol = "None";
		this.bid = 0;
		this.ask = 0;
		this.date = null;
		this.recommendation = ALGO_RECOMMENDATION.DO_NOTHING;
		this.stockQuantity = 0;
	}
	
	/**
	 * c'tor of stockStatus - update members with values received.
	 * @param symbol
	 * @param bid
	 * @param ask
	 * @param date
	 * @param recom
	 * @param stockQuantity
	 */
	public StockStatus(String symbol, float bid, float ask, Date date, ALGO_RECOMMENDATION recommendation, int stockQuantity){
		this.symbol = symbol;
		this.bid = bid;
		this.ask = ask;
		this.date = date;
		this.recommendation = recommendation;
		this.stockQuantity = stockQuantity;
	}
	
	/**
	 * copy c'tor of stockStatus that receives a stock.
	 * @param stock
	 */
	public StockStatus(Stock stock){
		super(stock);
		this.recommendation = ALGO_RECOMMENDATION.DO_NOTHING;
		this.stockQuantity = 0;
	}
	
	/**
	 * copy c'tor of stockStatus.
	 * @param stockStatus
	 */
	public StockStatus(StockStatus stockStatus){
		this.symbol = stockStatus.symbol;
		this.ask = stockStatus.ask;
		this.bid = stockStatus.bid;
		this.date = new Date(stockStatus.date.getTime());
		this.recommendation = stockStatus.recommendation;
		this.stockQuantity = stockStatus.stockQuantity;
	}

	//Getters & Setters
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

}
