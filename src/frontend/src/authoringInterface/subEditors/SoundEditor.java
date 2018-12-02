package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.sound.SoundClass;
import gameObjects.sound.SoundInstance;
import utils.NodeInstanceController;

/**
 * This class provides an editor for sound files used in the editor.
 *
 * @author Haotian Wang
 */
public class SoundEditor extends AbstractGameObjectEditor<SoundClass, SoundInstance> {
    public SoundEditor(GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        super(manager, controller);
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
