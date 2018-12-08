package gameObjects.player;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;

public interface PlayerClass extends GameObjectClass {

    PlayerInstance createInstance() throws GameObjectTypeException, InvalidIdException;

    boolean addGameObjectInstances(GameObjectInstance gameObjectInstance);

    boolean removeGameObjectInstances(GameObjectInstance gameObjectInstance);

    void removeAllGameObjectInstances();

    SimpleStringProperty getImagePath();

    void setImagePath(String newImagePath);

    @Override
    default GameObjectType getType() {
        return GameObjectType.PLAYER;
    }
}
