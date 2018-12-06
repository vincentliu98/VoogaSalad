package gameObjects;

import authoringUtils.exception.InvalidIdException;
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


    ThrowingConsumer<GameObjectClass, InvalidIdException> returnClassIdFunc();

    ThrowingConsumer<GameObjectInstance, InvalidIdException> returnInstanceIdFunc();


    Function<Integer, Boolean> verifyClassIdFunc();

    Function<Integer, Boolean> verifyTileInstanceIdFunc();
}
