package gameObjects.sound;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

/**
 * @author Haotian Wang
 */
public interface SoundInstance extends GameObjectInstance {

    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
