package gameObjects;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Jason Zhou
 */
public interface IdManager {

    Consumer<GameObjectClass> requestClassIdFunc();

    Consumer<GameObjectInstance> requestInstanceIdFunc();

    Function<Integer, Boolean> verifyClassIdFunc();

    Function<Integer, Boolean> verifyTileInstanceIdFunc();
}
