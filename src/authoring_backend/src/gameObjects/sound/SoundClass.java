package gameObjects.sound;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;
import authoringUtils.exception.GameObjectTypeException;

/**
 * @author  Haotian Wang
 */
public interface SoundClass extends GameObjectClass {

    SoundInstance createInstance() throws GameObjectTypeException;

    SimpleStringProperty getMediaFilePath();

    void setMediaFilePath(String mediaFilePath);


    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
