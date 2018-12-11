package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class InvalidGameObjectClassException extends GameObjectClassException {
    public InvalidGameObjectClassException(String message) {
        super(message);
    }

    public InvalidGameObjectClassException(Throwable e) {
        super(e);
    }

    public InvalidGameObjectClassException(String message, Throwable e) {
        super(message, e);
    }
}
