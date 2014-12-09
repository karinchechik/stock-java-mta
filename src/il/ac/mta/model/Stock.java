package il.ac.mta.model;

import java.util.Date;

public class Stock {
	//Members:
		private String stockSymbol;
		private float ask;
		private float bid;
		private java.util.Date date;
		
		/**
		 * c'tor that initializes all Stock members.
		 */
		public Stock(){
			this.stockSymbol = "";
			this.ask = 0;
			this.bid = 0;
			this.date = new Date();
		}
		
		/**
		 * 
		 * c'tor that uses the first c'tor
		 * and receives values to implement in the stock.  
		 * @param stockSymbol
		 * @param ask
		 * @param bid
		 * @param date
		 */
		public Stock(String stockSymbol, float ask, float bid, java.util.Date date) {
			this();
			
			setStockSymbol(stockSymbol);
			setAsk(ask);
			setBid(bid);
			setDate(date);
		}
		
		/**
		 * copy constructor of Stock.
		 * @param stock
		 */
		public Stock(Stock stock) {
			this(stock.stockSymbol, stock.ask, stock.bid, stock.date);			
		}
		
		//Getters:
		public String getStockSymbol(){
			return stockSymbol;	
		}
		public float getAsk(){
			return ask;
		}
		public float getBid() {
			return bid;
		}
		public java.util.Date getDate(){
			return date;
		}
		
		//Setters:
		public void setStockSymbol(String symbol){
			stockSymbol = symbol;
		}
		public void setAsk(float ask) {
			this.ask = ask;
		}
		public void setBid(float bid) {
			this.bid = bid;
		}
		public void setDate(java.util.Date date) {
			this.date = date;
		}
		
		/**
		 * This method returns a string with all the stock details.
		 * @return
		 */
		@SuppressWarnings("deprecation")
		public String getHtmlDescription() {
			String dateStr = date.getMonth() + "/" + date.getDate() + "/" + date.getYear();
			String str = "<b>" + "Stock symbol: " + "</b>" + stockSymbol + ", " + "<b>" + "Ask: " + "</b>" + ask + ", " + "<b>" + "Bid: " + "</b>" + bid + ", " + "<b>" + "Date: " + "</b>" + dateStr;
			return str;
		}
}
