package gameObjects.player;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Yunhao Qing
 */

public interface PlayerClass extends GameObjectClass {

    PlayerInstance createInstance() throws GameObjectTypeException, InvalidIdException;

    boolean addGameObjectInstances(GameObjectInstance gameObjectInstance);

    boolean isOwnedByPlayer(GameObjectInstance gameObjectInstance);

    boolean removeGameObjectInstances(GameObjectInstance gameObjectInstance);

    void removeAllGameObjectInstances();

    Set<Integer> getAllGameObjectInstanceIDs();

    String getImagePath();

    void setImagePath(String newImagePath);

    void equipContext(
            PlayerInstanceFactory playerInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changePlayerClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllPlayerInstancesFunc,
            Function<Integer, Boolean> deletePlayerInstanceFunc
    );

    @Override
    default GameObjectType getType() {
        return GameObjectType.PLAYER;
    }
}
