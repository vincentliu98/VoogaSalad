package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class GameObjectClassException extends Exception {
    public GameObjectClassException(String message) {
        super(message);
    }

    public GameObjectClassException(Throwable e) {
        super(e);
    }

    public GameObjectClassException(String message, Throwable e) {
        super(message, e);
    }
}
