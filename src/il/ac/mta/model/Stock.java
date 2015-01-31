package il.ac.mta.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is a class of stock.
 * Every stock has a unique symbol.
 * @author karin
 *
 */
public class Stock{

	protected String symbol;
	protected float ask;
	protected float bid;
	protected Date date;
	
	/**
	 * c'tor that initializes all Stock members.
	 */
	public Stock(){
		this.symbol = " ";
		this.ask = 0;
		this.bid = 0;
	}

	/**
	 * c'tor that updates the stock's members with received values.
	 * @param stockSymbol
	 * @param ask
	 * @param bid
	 * @param date
	 */
	public Stock(String stockSymbol, float ask, float bid, Date date) {
			setSymbol(stockSymbol);
			setAsk(ask);
			setBid(bid);
			setDate(new Date(date.getTime()));
	}

	/**
	 * copy constructor of Stock. 
	 * @param stock
	 */
	public Stock(Stock stock){
		this(stock.getSymbol(),stock.getAsk(),stock.getBid(),new Date(stock.getDate().getTime()));
	}

	//Getters:
	public String getSymbol (){
		return symbol;
	}

	public float getAsk (){
		return ask;
	}

	public float getBid (){
		return bid;
	}

	public Date getDate (){
		return date;
	}


	//Setters:
	public void setSymbol (String symbol){
		this.symbol = symbol;
	}

	public void setAsk (float ask){
		this.ask = ask;
	}

	public void setBid (float bid){
		this.bid = bid;
	}

	public void setDate (Date date){
		this.date = date;
	}

	/**
	 * This method returns a string with all the stock details.
	 * @return
	 */
	public String getHtmlDescription(){
		String stockHtmlDetailsString = "<b>Stock symbol</b>: " + getSymbol() 
				+ " , <b>ask</b>: " + getAsk() + " , <b>bid</b>: "
				+getBid()+ " , <b>date</b>: " +new SimpleDateFormat("dd-MM-yyyy").format(date);
		return stockHtmlDetailsString;
	}
}