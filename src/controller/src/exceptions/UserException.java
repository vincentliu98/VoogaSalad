package exceptions;

public class UserException extends ExtendedException {

    /**
     * Exceptions related to User login
     */
    public UserException (String message, String warning, Object ... values) {
        super(message, warning, values);
    }}
