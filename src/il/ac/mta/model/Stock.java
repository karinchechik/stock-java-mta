package il.ac.mta.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This is a class of stock.
 * Every stock has a unique symbol.
 * @author karin
 *
 */
public class Stock {
	//Members:
		protected String stockSymbol;
		protected float ask;
		protected float bid;
		protected Calendar basicDate = Calendar.getInstance();
		protected java.util.Date date = basicDate.getTime(); 
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		/**
		 * c'tor that initializes all Stock members.
		 */
		public Stock(){
			this.stockSymbol = "";
			this.ask = 0;
			this.bid = 0;
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
		public Stock(String stockSymbol, float ask, float bid, Date date) {
			this();
			
			setStockSymbol(stockSymbol);
			setAsk(ask);
			setBid(bid);
			setDate(new Date(date.getTime()));
		}
		
		/**
		 * copy constructor of Stock.
		 * @param stock
		 */
		public Stock(Stock stock) {
			this(stock.stockSymbol, stock.ask, stock.bid, new Date(stock.date.getTime()));			
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
		public void setDate(Date date) {
			this.date = date;
		}
		
		/**
		 * This method returns a string with all the stock details.
		 * @return
		 */

		public String getHtmlDescription() {
			String dateStr = df.format(date.getTime()) ;
			String str = "<b>" + "Stock symbol: " + "</b>" + stockSymbol + ", " + "<b>" + "Ask: " + "</b>" + ask + ", " + "<b>" + "Bid: " + "</b>" + bid + ", " + "<b>" + "Date: " + "</b>" + dateStr;
			return str;
		}
}
