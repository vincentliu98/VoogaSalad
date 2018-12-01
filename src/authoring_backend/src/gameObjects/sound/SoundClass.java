package gameObjects.sound;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;

/**
 * @author  Haotian Wang
 */
public interface SoundClass extends GameObjectClass {

    SoundInstance createInstance();

    String getMediaFilePath();

    void setMediaFilePath(String mediaFilePath);


    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
