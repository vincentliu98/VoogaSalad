package gameObjects.player;

import gameObjects.gameObject.GameObjectInstance;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class PlayerInstanceFactory {
    String CLASSNAME = "Player";

    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private Consumer<GameObjectInstance> addInstanceToMapFunc;

    public PlayerInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            Consumer<GameObjectInstance> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public PlayerInstance createInstance() {

        PlayerInstance playerInstance = new SimplePlayerInstance(CLASSNAME);
        requestInstanceIdFunc.accept(playerInstance);
        addInstanceToMapFunc.accept(playerInstance);
        return playerInstance;

    }
}
