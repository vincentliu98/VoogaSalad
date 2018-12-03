package utils.exception;

/**
 * This exception gets thrown when the NodeInstanceController tries to get a corresponding GameObjectInstance that is not there.
 *
 * @author Haotian Wang
 */
public class GameObjectInstanceNotFoundException extends Exception {
    public GameObjectInstanceNotFoundException(String message) {
        super(message);
    }

    public GameObjectInstanceNotFoundException(Throwable e) {
        super(e);
    }

    public GameObjectInstanceNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
