package gameObjects.category;

import gameObjects.gameObject.*;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Haotian Wang
 */
public interface CategoryInstance extends GameObjectInstance {

    SimpleStringProperty getImagePath();

    void setImagePath(String newImagePath);

    CategoryClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
