package authoringUtils.exception;

public class InvalidIdException extends IdException {
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
