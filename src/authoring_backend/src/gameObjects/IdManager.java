package gameObjects;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Jason Zhou
 */
public interface IdManager {

    Consumer<GameObjectClass> requestClassIdFunc();

    Consumer<GameObjectInstance> requestTileInstanceIdFunc();

    Consumer<GameObjectInstance> requestEntityInstanceIdFunc();

    Consumer<GameObjectClass> returnClassIdFunc();

    Consumer<GameObjectInstance> returnTileInstanceIdFunc();

    Consumer<GameObjectInstance> returnEntityInstanceIdFunc();

    Function<Integer, Boolean> verifyClassIdFunc();

    Function<Integer, Boolean> verifyTileInstanceIdFunc();

    Function<Integer, Boolean> verifyEntityInstanceIdFunc();
}
