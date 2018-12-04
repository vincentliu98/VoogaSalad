package authoringUtils.exception;

public class DuplicateGameObjectClassException extends GameObjectClassException {
    public DuplicateGameObjectClassException(String message) {
        super(message);
    }

    public DuplicateGameObjectClassException(Throwable e) {
        super(e);
    }

    public DuplicateGameObjectClassException(String message, Throwable e) {
        super(message, e);
    }
}
