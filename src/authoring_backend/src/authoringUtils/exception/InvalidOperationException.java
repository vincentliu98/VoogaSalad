package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class InvalidOperationException extends Exception {
    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(Throwable e) {
        super(e);
    }

    public InvalidOperationException(String message, Throwable e) {
        super(message, e);
    }
}
