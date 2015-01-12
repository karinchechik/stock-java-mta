package il.ac.mta.exception;

/**
 * This class creates a message with an explanation.
 * @author karin
 */
public class BalanceException extends Exception{

	private static final long serialVersionUID = 1L;

	public BalanceException()
	{
		super("Not enough balance to complete purchase!");
	}
}
