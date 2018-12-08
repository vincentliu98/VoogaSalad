package utils.exception;

/**
 * This exception is thrown when the user attempts to access some child Nodes of a GridPane that is out side its bounds
 *
 * @author Haotian Wang
 */
public class GridIndexOutOfBoundsException extends Exception {
    public GridIndexOutOfBoundsException(String message) {
        super(message);
    }

    public GridIndexOutOfBoundsException(Throwable e) {
        super(e);
    }

    public GridIndexOutOfBoundsException(String message, Throwable e) {
        super(message, e);
    }
}
