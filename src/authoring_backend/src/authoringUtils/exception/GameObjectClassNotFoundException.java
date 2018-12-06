package authoringUtils.exception;

public class GameObjectClassNotFoundException extends GameObjectClassException {
    public GameObjectClassNotFoundException(String message) {
        super(message + "Class Not Found");
    }

    public GameObjectClassNotFoundException(Throwable e) {
        super(e);
    }

    public GameObjectClassNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
