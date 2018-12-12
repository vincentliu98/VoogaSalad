package exceptions;

public class LoginException extends ExtendedException {
    /**
     * Exceptions related to User login
     */
    public LoginException (String message, String warning, Object ... values) {
        super(message, warning, values);
    }}
