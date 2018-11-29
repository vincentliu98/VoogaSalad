package authoringInterface.editor.memento;

import javafx.collections.ObservableMap;

/**
 * An Editor class (Memento pattern) that keeps track with the global data.
 * It is initialized in the front end's EditorMenuBarView
 *
 * @author jl729
 */

//Originator
public class Editor {
    ObservableMap globalData;

    // main body
    public void setState(ObservableMap contents) {
        this.globalData = contents;
    }

    // generate new memento to be added to caretaker
    public EditorMemento save() {
        return new EditorMemento(globalData);
    }

    // restore the state to the one from the parameter
    public void restoreToState(EditorMemento memento) {
        globalData = memento.getSavedState();
    }

}
