package il.ac.mta.exception;

/**
 * This class creates a message with an explanation.
 * @author karin
 */
public class StockNotExistException extends Exception {
	public StockNotExistException()
	{
		super("This Stock Does not exists");
	}
}
