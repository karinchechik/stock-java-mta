package il.ac.mta.exception;

/**
 * This class creates a message with an explanation.
 * @author karin
 *
 */
public class StockAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public StockAlreadyExistsException()
	{
		super("Cannot add new stock - Stock Already Exists!");
	}
}
