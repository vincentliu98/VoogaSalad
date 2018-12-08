package gameObjects.sound;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;
/**
 * @author  Haotian Wang
 */
public interface SoundClass extends GameObjectClass {

    SoundInstance createInstance() throws GameObjectTypeException, InvalidIdException;

    SimpleStringProperty getMediaFilePath();

    void setMediaFilePath(String mediaFilePath);


    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
