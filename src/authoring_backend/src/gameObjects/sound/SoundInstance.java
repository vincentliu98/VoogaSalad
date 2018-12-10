package gameObjects.sound;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Haotian Wang
 */
public interface SoundInstance extends GameObjectInstance {

    String getMediaFilePath();

    void setMediaFilePath(String newMediaFilePath);

    double getDuration();

    void setDuration(double newDuration);

    SoundClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.SOUND;
    }
}
