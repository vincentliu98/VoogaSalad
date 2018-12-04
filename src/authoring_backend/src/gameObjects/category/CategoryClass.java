package gameObjects.category;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;
import authoringUtils.exception.GameObjectTypeException;

/**
 * @author Haotian Wang
 */
public interface CategoryClass extends GameObjectClass {

    CategoryInstance createInstance() throws GameObjectTypeException;

    SimpleStringProperty getImagePath();

    void setImagePath(String newImagePath);

    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
