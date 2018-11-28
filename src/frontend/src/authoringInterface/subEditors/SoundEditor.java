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
}
