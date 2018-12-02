package gameObjects.category;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Haotian Wang
 */
public interface CategoryClass extends GameObjectClass {

    CategoryInstance createInstance();

    SimpleStringProperty getImagePath();

    void setImagePath(String newImagePath);

    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
