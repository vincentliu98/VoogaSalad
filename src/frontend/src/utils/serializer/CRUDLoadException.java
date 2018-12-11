package utils.serializer;

/**
 * This exception is thrown when the XML Parser tries to parse an
 *
 * @author Haotian Wang
 */
public class CRUDLoadException extends Exception {
    public CRUDLoadException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CRUDLoadException(String msg) {
        super(msg);
    }

    public CRUDLoadException(Throwable cause) {
        super(cause);
    }
}
