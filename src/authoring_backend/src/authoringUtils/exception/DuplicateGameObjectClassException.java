package authoringUtils.exception;

public class DuplicateGameObjectClassException extends GameObjectClassException {
    public DuplicateGameObjectClassException() {
        super("Another class with the same name exists");
    }

    public DuplicateGameObjectClassException(Throwable e) {
        super(e);
    }

    public DuplicateGameObjectClassException(String message, Throwable e) {
        super(message, e);
    }
}
