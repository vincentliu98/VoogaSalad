package utils.imageManipulation;

import gameObjects.entity.EntityClass;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import utils.exception.PreviewUnavailableException;

import java.util.ArrayList;
import java.util.List;

/**
 * This ImageManager will provide an Image that is a "preview" of a GameObjectClass. This Image is a JavaFx Image regardless of whether the actual thing being presented is a String.
 * This class also has functionalities that stack the multiple Images of a GameObjectClass if it has one.
 *
 * @author Haotian Wang
 */
public class ImageManager {
    private static final double IMAGE_WIDTH = 100;
    private static final double IMAGE_HEIGHT = 100;

    /**
     * This method gets a preview Image for the input GameObjectClass.
     *
     * @param gameObjectClass: The input GameObjectClass.
     * @return An Image object.
     */
    public static Image getPreview(GameObjectClass gameObjectClass) throws PreviewUnavailableException {
        GameObjectType type = gameObjectClass.getType();
        switch (type) {
            case UNSPECIFIED:
                throw new PreviewUnavailableException(String.format("The class %s does not support getPreview operation.", gameObjectClass.getClassName().getValue()));
            case ENTITY:
                ObservableList<String> imagePaths = ((EntityClass) gameObjectClass).getImagePathList();
                if (imagePaths == null || imagePaths.isEmpty()) {
                    return composeImageFromString(gameObjectClass.getClassName().getValue());
                } else if (imagePaths.size() == 1) {
                    return new Image(imagePaths.get(0));
                } else {
                    List<Image> images = new ArrayList<>();
                    imagePaths.forEach(uri -> images.add(new Image(uri)));
                    return stackImageFromMultipleImages(images);
                }
                break;
            case CATEGORY:
                // TODO
                break;
            case TILE:
                // TODO
                break;
            case SOUND:
                // TODO
                break;
            case PLAYER:
                // TODO
                break;
        }
        return null;
    }

    /**
     * This method composes an Image from a list of Images.
     *
     * @param images: A list of Images to be stacked on top of each other.
     * @return A resultant Image representing the stack of images.
     */
    private static Image stackImageFromMultipleImages(List<Image> images) {
    }

    /**
     * THis method composes an Image from an input String.
     *
     * @param value: The input String that will be converted to an Image.
     * @return An Image that has the input String as content.
     */
    private static Image composeImageFromString(String value) {
        return new Text(value).snapshot(null, null);
    }

    /**
     * This method gets an Image that represents a preview of a GameObjectInstance.
     *
     * @param gameObjectInstance: A GameObjectInstance whose preview will be returned.
     * @return An Image object.
     */
    public static Image getPreview(GameObjectInstance gameObjectInstance) {

    }
}
