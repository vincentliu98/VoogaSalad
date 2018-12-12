package gameObjects.player;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * @author Yunhao Qing
 */


public class PlayerInstanceFactory {
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;

    public PlayerInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public PlayerInstance createInstance(PlayerClass playerPrototype)
            throws GameObjectTypeException, InvalidIdException {
        // TODO locality
        if (playerPrototype.getType() != GameObjectType.PLAYER) {
            throw new GameObjectTypeException(GameObjectType.PLAYER);
        }
        var imagePathCopy = playerPrototype.getImagePath();
        var propertiesMapCopy = new HashMap<>(playerPrototype.getPropertiesMap());
        PlayerInstance playerInstance = new SimplePlayerInstance(playerPrototype.getClassName(), imagePathCopy, propertiesMapCopy, playerPrototype);
        requestInstanceIdFunc.accept(playerInstance);
        addInstanceToMapFunc.accept(playerInstance);
        return playerInstance;

    }
}
