package authoringUtils.exception;

public class GameObjectTypeException extends Exception {
    public GameObjectTypeException(String message) {
        super(message);
    }

    public GameObjectTypeException(Throwable e) {
        super(e);
    }

    public GameObjectTypeException(String message, Throwable e) {
        super(message, e);
    }
}
