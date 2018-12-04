package gameObjects.player;

import authoringUtils.exception.InvalidOperationException;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.Set;

public interface PlayerInstance extends GameObjectInstance {

    @Override
    default void setClassName(String name)
            throws InvalidOperationException {
        throw new InvalidOperationException("Player Class name is not settable");
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
