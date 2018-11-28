package gameObjects.sound;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;

/**
 * @author  Haotian Wang
 */
public interface SoundClass extends GameObjectClass {
    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
