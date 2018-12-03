package utils.exception;

/**
 * This exception gets thrown when a preview for a GameObjectInstance or GameObjectClass is not available.
 *
 * @author Haotian Wang
 */
public class PreviewUnavailableException extends Exception {
    public PreviewUnavailableException(String message) {
        super(message);
    }

    public PreviewUnavailableException(Throwable e) {
        super(e);
    }

    public PreviewUnavailableException(String message, Throwable e) {
        super(message, e);
    }
}
