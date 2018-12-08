package gameObjects.player;

import authoringUtils.exception.InvalidOperationException;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

public interface PlayerInstance extends GameObjectInstance {

    SimpleStringProperty getImagePath();

    void setImagePath(String newImagePath);

    PlayerClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.PLAYER;
    }

}
