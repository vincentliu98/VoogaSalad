package gameObjects.player;

import gameObjects.exception.InvalidOperationException;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Set;

public interface PlayerInstance extends GameObjectInstance {

    @Override
    default void setClassName(String name) {
        throw new InvalidOperationException();
    }

    Set<Integer> getEntityIDs();

    boolean addEntity(int entityId);

    boolean removeEntity(int entityId);

    void removeAllEntities();

//    PlayerClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.PLAYER;
    }

}
