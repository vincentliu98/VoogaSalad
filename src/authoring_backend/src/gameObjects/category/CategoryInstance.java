package gameObjects.category;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

/**
 * @author Haotian Wang
 */
public interface CategoryInstance extends GameObjectInstance {
    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
