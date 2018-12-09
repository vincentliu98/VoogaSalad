package authoringInterface.subEditors.exception;

/**
 * This exception class is thrown when the EditorFactory is called to make an Editor, but an editor for that GameObjectType is not implemented yet.
 *
 * @author Haotian Wang
 */
public class MissingEditorForTypeException extends Exception {
    public MissingEditorForTypeException(String message) {
        super(message);
    }

    public MissingEditorForTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
