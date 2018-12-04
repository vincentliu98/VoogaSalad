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


    Consumer<GameObjectClass> returnClassIdFunc();

    Consumer<GameObjectInstance> returnInstanceIdFunc();


    Function<Integer, Boolean> verifyClassIdFunc();

    Function<Integer, Boolean> verifyTileInstanceIdFunc();
}
