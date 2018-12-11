package authoringUtils.exception;

/**
 * @author Jason Zhou
 * @author Yunhao Qing
 */
public class InvalidIdException extends IdException {
    public InvalidIdException() {
        super("GameObject Instance has an invalid Id");
    }

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(Throwable e) {
        super(e);
    }

    public InvalidIdException(String message, Throwable e) {
        super(message, e);
    }
}
