package utils.exception;

/**
 * This Exception is thrown when a GameObjectClass is queried in some map but is not found as an entry.
 *
 * @author Haotian Wang
 */
public class GameObjectClassNotFoundException extends Exception {
    public GameObjectClassNotFoundException(String message) {
        super(message);
    }

    public GameObjectClassNotFoundException(Throwable e) {
        super(e);
    }

    public GameObjectClassNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
