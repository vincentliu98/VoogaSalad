package exceptions;

public class ServerException extends ExtendedException {

    /**
     * Exceptions related to User login
     */
    public ServerException (String message, String warning, Object ... values) {
        super(message, warning, values);
    }}
