package authoringInterface.sidebar.subEditors;

import authoringInterface.sidebar.treeItemEntries.Sound;
import javafx.scene.layout.AnchorPane;

/**
 * This class provides an editor for sound files used in the editor.
 *
 * @author Haotian Wang
 */
public class SoundEditor extends AbstractGameObjectEditor<Sound> {
    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return null;
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(Sound userObject) {

    }

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    @Override
    public Sound getObject() {
        return null;
    }

}
