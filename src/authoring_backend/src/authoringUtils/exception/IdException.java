package authoringUtils.exception;

public class IdException extends Exception {
    public IdException(String message) {
        super(message);
    }

    public IdException(Throwable e) {
        super(e);
    }

    public IdException(String message, Throwable e) {
        super(message, e);
    }
}
