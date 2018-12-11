package authoringInterface.subEditors.exception;

/**
 * This exception is thrown when the names for a GameObjectClass or GameObjectInstance is illegal.
 *
 * @author Haotian Wang
 */
public class IllegalGameObjectNamingException extends Exception {
    public IllegalGameObjectNamingException(String msg) {
        super(msg);
    }

    public IllegalGameObjectNamingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalGameObjectNamingException(Throwable cause) {
        super(cause);
    }
}
