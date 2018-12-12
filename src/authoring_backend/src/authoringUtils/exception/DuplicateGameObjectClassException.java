package authoringUtils.exception;

/**
 * @author Jason Zhou
 * @author Yunhao Qing
 */
public class DuplicateGameObjectClassException extends GameObjectClassException {
    public DuplicateGameObjectClassException() {
        super("Another class with the same name exists");
    }

    public DuplicateGameObjectClassException(String msg) { super(msg); }

    public DuplicateGameObjectClassException(Throwable e) {
        super(e);
    }

    public DuplicateGameObjectClassException(String message, Throwable e) {
        super(message, e);
    }
}
