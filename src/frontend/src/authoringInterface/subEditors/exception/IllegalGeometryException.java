package authoringInterface.subEditors.exception;

/**
 * This exception is thrown when the user attempts to assign illegal information about the geometry of a GameObject.
 *
 * @author Haotian Wang
 */
public class IllegalGeometryException extends Exception {
    public IllegalGeometryException(String msg) {
        super(msg);
    }

    public IllegalGeometryException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public IllegalGeometryException(Throwable cause) {
        super(cause);
    }
}
