package gameObjects.player;

import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;

import java.util.function.Consumer;

public class PlayerInstanceFactory {
    String CLASSNAME = "Player";

    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;

    public PlayerInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public PlayerInstance createInstance(String playerName)
            throws InvalidIdException {
        PlayerInstance playerInstance = new SimplePlayerInstance(CLASSNAME);
        playerInstance.setInstanceName(playerName);
        requestInstanceIdFunc.accept(playerInstance);
        addInstanceToMapFunc.accept(playerInstance);
        return playerInstance;
    }
}
