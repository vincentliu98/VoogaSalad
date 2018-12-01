package gameObjects.category;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

/**
 * @author Haotian Wang
 */
public interface CategoryInstance extends GameObjectInstance {

    CategoryClass getGameObjectClass();


    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
