package il.ac.mta.model;

import java.util.Date;
/**
 * This is a class of portfolio.
 * Every portfolio can hold stocks.
 * Every stock has stock status that is private to the portfolio holder.
 * @author karin
 *
 */
public class Portfolio {
	
	public static final int MAX_PORTFOLIO_SIZE = 5;
	public static final int SELL_ALL = -1;
	public static final int BUY_ALL = -1;
	
	private int portfolioSize;
	private StockStatus[] stockStatus;
	private String title;
	private float balance;
	
	public enum ALGO_RECOMMENDATION {
		DO_NOTHING, BUY, SELL;		
	}
	
	/**
	 *c'tor definition of portfolio.
	 *creates new portfolio and initializes it's members.
	 */
	public Portfolio()
	{
		portfolioSize = 0;
		stockStatus = new StockStatus[MAX_PORTFOLIO_SIZE];
		balance = 0;
		this.setTitle("New Portfolio");
	}

	/**
	 * c'tor second definition - receives a title.
	 * calls the first c'tor and adds a title.
	 * @param title
	 */
	public Portfolio(String title)
	{
		this();
		this.setTitle(title);
	}
	
	/**
	 * copy constructor.
	 * Receives an instance of portfolio and copies it.
	 * @param portfolio
	 */
	public Portfolio(Portfolio portfolio)
	{
		this(portfolio.getTitle());
		setPortfolioSize(portfolio.getPortfolioSize());
		
		for(int i = 0; i < portfolioSize; i++)
		{
			stockStatus[i] = new StockStatus(portfolio.getStockStatus()[i]);
		}
		updateBalance(portfolio.getBalance());
	}

	//Getters & Setters:
	public String getTitle(){
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public int getPortfolioSize() {
		return portfolioSize;
	}

	public void setPortfolioSize(int portfolioSize) {
		this.portfolioSize = portfolioSize;
	}
	
	/**
	 * This method adds a new stock to the portfolio.
	 * @param stock
	 */
	public void addStock(Stock stock)
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stockStatus[i].getStockSymbol().equals(stock.getStockSymbol()))
			{
				return;
			}
		}
		
		if(portfolioSize < MAX_PORTFOLIO_SIZE)
		{
			this.stockStatus[portfolioSize] = new StockStatus();
			this.stockStatus[portfolioSize].setStockSymbol(stock.getStockSymbol());
			this.stockStatus[portfolioSize].setAsk(stock.getAsk());
			this.stockStatus[portfolioSize].setBid(stock.getBid());
			this.stockStatus[portfolioSize].setDate(new Date(stock.date.getTime()));
			portfolioSize++;
		}
		else
		{
			System.out.println("Can't add new stock, portfolio can have only " + MAX_PORTFOLIO_SIZE + " stocks");
		}
	}
	
	/**
	 * This method receives a stock and removes it from the portfolio.
	 * @param stock
	 */
	public boolean removeStock(String stockSymbol)
	{
		for(int i=0; i < portfolioSize; i++)
		{
			if(this.stockStatus[i].getStockSymbol().equals(stockSymbol))
			{
				if(this.stockStatus[i].getStockQuantity() != 0)
				{
					sellStock(stockSymbol, SELL_ALL);
				}
				
				if(portfolioSize == 1)
				{
					this.stockStatus[i] = null;
				}
				else
				{
					this.stockStatus[i] = this.stockStatus[portfolioSize-1];
					this.stockStatus[portfolioSize-1] = null;
				}
				this.portfolioSize--;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method sells stock (from 1 to all the stocks). 
	 * @param symbol
	 * @param quantity
	 * @return
	 */
	public boolean sellStock(String symbol, int quantity)
	{
		int tQuantity;
		int maxQuantity;
		if(quantity > -2 && quantity != 0) //-1 and above are valid values except 0
		{
			for(int i=0; i < portfolioSize; i++)
			{
				if(this.stockStatus[i].getStockSymbol().equals(symbol))
				{
					maxQuantity = stockStatus[i].getStockQuantity();
					tQuantity = quantity;
					if(quantity == SELL_ALL)
					{
						tQuantity = maxQuantity;
					}
					else if(quantity > maxQuantity)
					{
						System.out.println("Not enough stocks to sell!");
						return false;
					}

					updateBalance(tQuantity * stockStatus[i].getBid());
					stockStatus[i].setStockQuantity(stockStatus[i].getStockQuantity()-tQuantity);
					return true;
				}
			}
		}
		return false;
	}

	
	/**
	 * This method buys stocks (from 1 to as many as possible according to the balance).
	 * @param symbol
	 * @param quantity
	 * @return
	 */
	public boolean buyStock(String symbol, int quantity)
	{
		int maxQuantity; 
		int tQuantity;
		
		if(quantity > -2 && quantity != 0) //-1 and above are valid values except 0
		{
			for(int i=0; i < portfolioSize; i++)
			{
				if(this.stockStatus[i].getStockSymbol().equals(symbol))
				{
					maxQuantity = (int)(balance / stockStatus[i].getAsk());
					tQuantity = quantity;
					if (quantity == BUY_ALL)
					{
						tQuantity = maxQuantity;
					}
					else if (quantity > maxQuantity){
						System.out.println("Not enough balance to complete purchase!");
						return false;
					}
					
					updateBalance(-tQuantity * stockStatus[i].getAsk());
					stockStatus[i].setStockQuantity(stockStatus[i].getStockQuantity()+tQuantity);
					
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * The get stocks value methods return the value of all the stocks in this portfolio.
	 * @return
	 */
	public float getStocksValue()
	{
		float sum = 0;
		for(int i=0; i < portfolioSize; i++)
		{
			sum += stockStatus[i].getStockQuantity() * stockStatus[i].getBid();
		}
		return sum;
	}
	
	public float getBalance(){
		return balance;
	}
	
	/**
	 * This method returns the value of the portfolio = balance + value of the stocks.
	 * @return
	 */
	public float getTotalValue()
	{
		return getStocksValue() + getBalance();
	}
	
	public StockStatus[] getStockStatus() {
		return stockStatus;
	}
	
	/**
	 * A method that updates the balance of the stock.
	 * @param amount
	 */
	public void updateBalance(float amount){
		if (balance + amount < 0){
			System.out.println("Error - Not enough balance to complete purchase!");
		}
		else
		{
			balance += amount;
			System.out.println("The amount was added/reduced successfully.");
		}
	}
	
	/**
	 * this method return an html string that includes the title of the portfolio
	 * and the description of it's stocks.
	 * @return
	 */
	public String getHtmlString()
	{
		String str = "<h1><font color=turquoise>" + getTitle() + "</font></h1>" + "<br/>";
		str += "<b>Total Portfolio Value:</b> " + getTotalValue() +
				"$, <b>Total Stocks Value:</b> " + getStocksValue() +
				"$, <b>Balance:</b> " + getBalance() + "$<br/>";
		for(int i = 0; i < portfolioSize; i++)
		{
			str += stockStatus[i].getHtmlDescription() + ", <b>Quantity:</b> " + stockStatus[i].getStockQuantity() +"<br/>";
		}
		return str;
	}

}
