package utils.exception;

/**
 * This exception gets thrown when the NodeInstanceController tries to get a corresponding Node that is not there.
 *
 * @author Haotian Wang
 */
public class NodeNotFoundException extends Exception {
    public NodeNotFoundException(String message) {
        super(message);
    }

    public NodeNotFoundException(Throwable e) {
        super(e);
    }

    public NodeNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
