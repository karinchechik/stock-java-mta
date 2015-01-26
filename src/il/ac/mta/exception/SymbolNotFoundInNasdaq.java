package il.ac.mta.exception;

public class SymbolNotFoundInNasdaq extends Exception {

	private static final long serialVersionUID = 1L;

	public SymbolNotFoundInNasdaq(String symbol) {
		super("Symbol " + symbol + " does not exist in Nasdaq!");
	}
}
