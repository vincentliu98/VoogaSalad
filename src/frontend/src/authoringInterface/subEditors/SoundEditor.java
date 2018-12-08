package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.sound.SoundClass;
import gameObjects.sound.SoundInstance;
import javafx.collections.ObservableList;

/**
 * This class provides an editor for sound files used in the editor.
 *
 * @author Haotian Wang
 */
public class SoundEditor extends AbstractGameObjectEditor<SoundClass, SoundInstance> {
    private ObservableList<String> mediaFilePaths;

    SoundEditor(GameObjectsCRUDInterface manager) {
        super(manager);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {

    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {

    }
}
