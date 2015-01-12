package il.ac.mta.exception;

/**
 * This class creates a message with an explanation.
 * @author karin
 */
public class StockNotExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public StockNotExistException()
	{
		super("This Stock Does not exists");
	}
}
