package authoringInterface.editor.memento;

import javafx.collections.ObservableMap;

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
