package utils.exception;

/**
 * This exception is thrown when one is trying to call JavaFxOperation.removeNodeFromParent and for some reason it is not possible to remove the child Node.
 *
 * @author Haotian Wang
 */
public class UnremovableNodeException extends Exception {
    public UnremovableNodeException(String message) {
        super(message);
    }

    public UnremovableNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnremovableNodeException(Throwable cause) {
        super(cause);
    }
}
