package authoringUtils.exception;

public class GameObjectTypeException extends Exception {
    public GameObjectTypeException(String className, String defaultClass) {
        super(className + "is not a" + defaultClass);
    }

    public GameObjectTypeException(Throwable e) {
        super(e);
    }

    public GameObjectTypeException(String message, Throwable e) {
        super(message, e);
    }
}
