package authoringUtils.exception;

public class GameObjectInstanceNotFoundException extends GameObjectInstanceException {
    public GameObjectInstanceNotFoundException(String message) {
        super(message);
    }

    public GameObjectInstanceNotFoundException(Throwable e) {
        super(e);
    }

    public GameObjectInstanceNotFoundException(String message, Throwable e) {
        super(message, e);
    }
}
