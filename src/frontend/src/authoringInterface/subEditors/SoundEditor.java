package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.sound.SoundClass;
import gameObjects.sound.SoundInstance;

/**
 * This class provides an editor for sound files used in the editor.
 *
 * @author Haotian Wang
 */
public class SoundEditor extends AbstractGameObjectEditor<SoundClass, SoundInstance> {
    public SoundEditor(GameObjectsCRUDInterface manager) {
        super(manager);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param gameObject
     */
    @Override
    public void readGameObjectInstance(SoundInstance gameObject) {

    }

    /**
     * Read the GameObjectClass represented by this editor.
     *
     * @param gameObjectClass : The GameObjectClass interface that is being read.
     */
    @Override
    public void readGameObjectClass(SoundClass gameObjectClass) {

    }
}
