package il.ac.mta.dto;

import il.ac.mta.model.StockStatus;

import java.util.List;

/**
 * @author hanan.gitliz@gmail.com
 * @since Jan 18, 2015
 */
public class PortfolioEditDto {

	private String title;
	private List<StockStatus> stockStatusList;
	private float balance;
	
	public String getTitle() {
		return title;
	}
	public float getBalance() {
		return balance;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}	
	public List<StockStatus> getStockStatusList() {
		return stockStatusList;
	}
	public void setStockStatusList(List<StockStatus> stockStatusList) {
		this.stockStatusList = stockStatusList;
	}
}
