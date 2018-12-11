package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class TurnNotFoundException extends Exception {
    public TurnNotFoundException(String message) {
        super(message);
    }

    public TurnNotFoundException(Throwable e) {
        super(e);
    }

    public TurnNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
