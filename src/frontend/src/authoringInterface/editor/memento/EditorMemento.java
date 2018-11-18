package authoringInterface.editor.memento;

import javafx.collections.ObservableMap;

public class EditorMemento {
    private final ObservableMap editorState;

    // construct a memento
    public EditorMemento(ObservableMap state) {
        editorState = state;
    }

    // retrieve current state info
    public ObservableMap getSavedState() {
        return editorState;
    }
}