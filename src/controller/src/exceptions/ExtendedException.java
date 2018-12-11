package exceptions;

public class ExtendedException extends RuntimeException {
    private String warningMessage;
    /**
     * Exceptions related to User login
     */
    public ExtendedException(String message, String warning, Object ... values) {
        super(String.format(message, values));
        warningMessage = warning;
    }

    public String getWarning(){
        return warningMessage;
    }
}
