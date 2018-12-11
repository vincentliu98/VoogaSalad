package authoringInterface.editor.memento;

import java.util.ArrayList;

/**
 * An EditorCaretaker (Memento pattern) class that stores all the state of the program
 * An instance is created whenever the user saves the file, which will be added to EditorCaretaker
 *
 * @author jl729
 */

public class EditorCaretaker {
    private ArrayList<EditorMemento> mementos = new ArrayList<>();

    // add memento to the list
    public void addMemento(EditorMemento m) {
        mementos.add(m);
    }

    // get the state from a certain point
    public EditorMemento getMemento(Integer index) {
        if (0 <= index && index < mementos.size()) {
            return mementos.get(index);
        } else return mementos.get(mementos.size() - 1);
    }
}
