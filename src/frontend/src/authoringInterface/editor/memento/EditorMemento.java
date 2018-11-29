package authoringInterface.editor.memento;

import javafx.collections.ObservableMap;

/**
 * An EditorMemento (Memento pattern) class that keeps track of a particular state
 * An instance is created whenever the user saves the file, which will be added to EditorCaretaker
 *
 * @author jl729
 */

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