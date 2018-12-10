package gameObjects.category;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;

/**
 * Category Class holds the headers in the View Editor and is a placeholder class for the headers.
 * @author Haotian Wang
 */
public interface CategoryClass extends GameObjectClass {

    /**
     * The method should not be used.
     * @return the category instance that is created
     * @throws GameObjectTypeException
     * @throws InvalidIdException
     */
    CategoryInstance createInstance() throws GameObjectTypeException, InvalidIdException;

    /**
     * The method returns the path of the image stored in the Category Class.
     * @return the image path
     */
    String getImagePath();

    /**
     *
     * @param newImagePath the path of the image to be stored
     */
    void setImagePath(String newImagePath);

    /**
     * @return the GameObject Type
     */
    @Override
    default GameObjectType getType() {
        return GameObjectType.CATEGORY;
    }
}
