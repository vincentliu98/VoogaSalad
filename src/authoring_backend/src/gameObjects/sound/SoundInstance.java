package gameObjects.sound;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Haotian Wang
 */
public interface SoundInstance extends GameObjectInstance {

    SimpleStringProperty getMediaFilePath();

    void setMediaFilePath(String newMediaFilePath);

    SoundClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
