package gameObjects.category;

import authoringUtils.exception.*;
import gameObjects.gameObject.*;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Haotian Wang
 */
public interface CategoryClass extends GameObjectClass {

    CategoryInstance createInstance() throws GameObjectTypeException, InvalidIdException;

    SimpleStringProperty getImagePath();

    void setImagePath(String newImagePath);

    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
