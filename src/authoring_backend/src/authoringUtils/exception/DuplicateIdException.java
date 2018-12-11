package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class DuplicateIdException extends IdException {
    public DuplicateIdException(String message) {
        super(message);
    }

    public DuplicateIdException(Throwable e) {
        super(e);
    }

    public DuplicateIdException(String message, Throwable e) {
        super(message, e);
    }
}
