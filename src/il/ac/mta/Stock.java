package il.ac.mta;

public class Stock {
	//Members:
	private String stockSymbol;
	private float ask;
	private float bid;
	private java.util.Date date;
	
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
	
	//Method:
	@SuppressWarnings("deprecation")
	public String getHtmlDescription() {
		String dateStr = date.getMonth() + "/" + date.getDate() + "/" + date.getYear();
		String str = "<b>" + "Stock symbol: " + "</b>" + stockSymbol + ", " + "<b>" + "Ask: " + "</b>" + ask + ", " + "<b>" + "Bid: " + "</b>" + bid + ", " + "<b>" + "Date: " + "</b>" + dateStr;
		return str;
	}
}