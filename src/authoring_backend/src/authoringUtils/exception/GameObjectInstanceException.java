package authoringUtils.exception;

/**
 * @author Jason Zhou
 */
public class GameObjectInstanceException extends Exception {
    public GameObjectInstanceException(String message) {
        super(message);
    }

    public GameObjectInstanceException(Throwable e) {
        super(e);
    }

    public GameObjectInstanceException(String message, Throwable e) {
        super(message, e);
    }
}
