package gameObjects.category;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;

/**
 * @author Haotian Wang
 */
public interface CategoryClass extends GameObjectClass {
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
