package il.ac.mta.dto;

import java.util.Date;

public class PortfolioTotalStatus implements Comparable<PortfolioTotalStatus> {

	private final Date date;
	private final float value;
	
	public PortfolioTotalStatus(Date date, float value) {
		this.date = date;
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public float getValue() {
		return value;
	}

	@Override
	public int compareTo(PortfolioTotalStatus o) {
		return this.getDate().compareTo(o.getDate());
	}
}
