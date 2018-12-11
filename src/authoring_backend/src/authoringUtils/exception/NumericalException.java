package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class NumericalException extends Exception {
    public NumericalException(String message) {
        super(message);
    }

    public NumericalException(Throwable e) {
        super(e);
    }

    public NumericalException(String message, Throwable e) {
        super(message, e);
    }
}
