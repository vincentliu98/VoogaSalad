package utils.exception;

/**
 * This exception is thrown when the XMLParser encountered an error parsing the XML.
 *
 * @author Haotian Wang
 */
public class XMLParsingException extends Exception {
    public XMLParsingException(String msg) {
        super(msg);
    }

    public XMLParsingException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public XMLParsingException(Throwable cause) {
        super(cause);
    }
}
