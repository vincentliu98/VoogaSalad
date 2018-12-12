package exceptions;

public class RegistrationException extends ExtendedException {
    /**
     * Exceptions related to User login
     */
    public RegistrationException (String message, String warning, Object ... values) {
        super(message, warning, values);
    }
}