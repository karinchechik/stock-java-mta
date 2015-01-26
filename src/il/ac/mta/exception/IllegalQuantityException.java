package il.ac.mta.exception;

/**
 * @author hanan.gitliz@gmail.com
 * @since Jan 19, 2015
 */
public class IllegalQuantityException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalQuantityException() {
		super("Quantity must be positive!");
	}
	
	public IllegalQuantityException(String message) {
		super(message);
	}
}
