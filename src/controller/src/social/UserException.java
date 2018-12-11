package social;

public class UserException extends RuntimeException {

    /**
     * Exceptions related to User login
     */
    public UserException(String message, Object... values) {
        super(String.format(message, values));
    }
}
