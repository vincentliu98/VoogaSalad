package utils.exception;

/**
 * This Exception is thrown when the developer attempts to use the convenient methods in Coordiates class to process some Nodes whose logic has not been defined yet.
 *
 * @author Haotian Wang
 */
public class UnhandledCoordinatesClassException extends Exception {
    public UnhandledCoordinatesClassException(String message) {
        super(message);
    }

    public UnhandledCoordinatesClassException(Throwable e) {
        super(e);
    }

    public UnhandledCoordinatesClassException(String message, Throwable e) {
        super(message, e);
    }
}
