package gameObjects.category;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

/**
 * CategoryInstance is a dummy interface to ensure consistency in the structure of the CRUD interface.
 *
 * @author Haotian Wang
 */
public interface CategoryInstance extends GameObjectInstance {

    /**
     * @return
     */
    String getImagePath();

    /**
     * @param newImagePath
     */
    void setImagePath(String newImagePath);

    /**
     * @return
     */
    CategoryClass getGameObjectClass();

    /**
     * @return
     */
    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
