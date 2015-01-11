package il.ac.mta.exception;

/**
 * This class creates a message with an explanation.
 * @author karin
 */
public class PortfolioFullException extends Exception {
	private static final int MAX_PORTFOLIO_SIZE = 5;

	public PortfolioFullException(){
		super("Can't add new stock, portfolio can have only " + MAX_PORTFOLIO_SIZE + " stocks");
	}
}
