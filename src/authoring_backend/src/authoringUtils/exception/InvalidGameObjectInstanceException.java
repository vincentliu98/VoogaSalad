package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class InvalidGameObjectInstanceException extends Exception {
    public InvalidGameObjectInstanceException(String message) {
        super(message);
    }

    public InvalidGameObjectInstanceException(Throwable e) {
        super(e);
    }

    public InvalidGameObjectInstanceException(String message, Throwable e) {
        super(message, e);
    }
}
