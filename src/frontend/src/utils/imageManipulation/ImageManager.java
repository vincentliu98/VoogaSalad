package utils.imageManipulation;

import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Transform;
import utils.exception.GameObjectClassNotFoundException;
import utils.exception.GameObjectInstanceNotFoundException;
import utils.exception.PreviewUnavailableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This ImageManager will provide an Image that is a "preview" of a GameObjectClass. This Image is a JavaFx Image regardless of whether the actual thing being presented is a String.
 * This class also has functionalities that stack the multiple Images of a GameObjectClass if it has one.
 *
 * @author Haotian Wang
 */
public class ImageManager {
    private static final double IMAGE_WIDTH = 100;
    private static final double IMAGE_HEIGHT = 100;
    private static final double X_OFFSET = 10;
    private static final double Y_OFFSET = 10;
    private static Map<GameObjectClass, Image> classImageMap;
    private static Map<GameObjectInstance, Image> instanceImageMap;
    static {
        classImageMap = new HashMap<>();
        instanceImageMap = new HashMap<>();
    }

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
            case TILE:
            case ENTITY:
                ObservableList<String> imagePaths;
                if (gameObjectClass instanceof EntityClass) {
                    imagePaths = ((EntityClass) gameObjectClass).getImagePathList();
                } else {
                    imagePaths = ((TileClass) gameObjectClass).getImagePathList();
                }
                return getImage(imagePaths, gameObjectClass.getClassName());
            case CATEGORY:
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
     * Refactored code to remove duplicates.
     *
     * @param imagePaths: A List of String that contains file paths to images.
     * @param textToDisplay: The input String if image file is not available.
     * @return An Image for this GameObject Instance or Class.
     */
    private static Image getImage(ObservableList<String> imagePaths, ReadOnlyStringProperty textToDisplay) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            return composeImageFromString(textToDisplay.getValue());
        } else if (imagePaths.size() == 1) {
            return new Image(imagePaths.get(0));
        } else {
            List<Image> images = new ArrayList<>();
            imagePaths.forEach(uri -> images.add(new Image(uri)));
            return stackImageFromMultipleImages(images);
        }
    }

    /**
     * This method composes an Image from a list of Images.
     *
     * @param images: A list of Images to be stacked on top of each other.
     * @return A resultant Image representing the stack of images.
     */
    private static Image stackImageFromMultipleImages(List<Image> images) {
        double width = (images.size()-1) * X_OFFSET + IMAGE_WIDTH;
        double height = (images.size()-1) * Y_OFFSET + IMAGE_HEIGHT;
        Canvas canvas = new Canvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
        for (int i = 0, size = images.size(); i < size; i++) {
            double x = i * X_OFFSET;
            double y = i * Y_OFFSET;
            gc.fillRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
            gc.drawImage(images.get(i), x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
        }
        SnapshotParameters sp =  new SnapshotParameters();
        sp.setTransform(Transform.scale(IMAGE_WIDTH, IMAGE_HEIGHT));
        sp.setFill(Color.TRANSPARENT);
        return canvas.snapshot(sp, null);
    }

    /**
     * THis method composes an Image from an input String.
     *
     * @param value: The input String that will be converted to an Image.
     * @return An Image that has the input String as content.
     */
    private static Image composeImageFromString(String value) {
        SnapshotParameters sp =  new SnapshotParameters();
        sp.setTransform(Transform.scale(IMAGE_WIDTH, IMAGE_HEIGHT));
        sp.setFill(Color.TRANSPARENT);
        return new Text(value).snapshot(sp, null);
    }

    /**
     * This method gets an Image that represents a preview of a GameObjectInstance.
     *
     * @param gameObjectInstance: A GameObjectInstance whose preview will be returned.
     * @return An Image object.
     */
    public static Image getPreview(GameObjectInstance gameObjectInstance) throws PreviewUnavailableException {
        GameObjectType type = gameObjectInstance.getType();
        switch (type) {
            case UNSPECIFIED:
                throw new PreviewUnavailableException(String.format("The instance %s does not support getPreview operation.", gameObjectInstance.getInstanceName().getValue()));
            case TILE:
            case ENTITY:
                ObservableList<String> imagePaths;
                if (gameObjectInstance instanceof EntityInstance) {
                    imagePaths = ((EntityInstance) gameObjectInstance).getImagePathList();
                } else {
                    imagePaths = ((TileInstance) gameObjectInstance).getImagePathList();
                }
                return getImage(imagePaths, gameObjectInstance.getInstanceName());
            case CATEGORY:
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
     * Remove a (GameObjectInstance, Image) pair from the internal repository of this class.
     *
     * @param gameObjectInstance: A GameObjectInstance whose images will be removed.
     * @throws GameObjectInstanceNotFoundException
     */
    public static void removeInstanceImage(GameObjectInstance gameObjectInstance) throws GameObjectInstanceNotFoundException {
        if (!instanceImageMap.containsKey(gameObjectInstance)) {
            throw new GameObjectInstanceNotFoundException(String.format("The Image preview for the GameObjectInstance %s is not found", gameObjectInstance.getInstanceName().getValue()));
        }
        instanceImageMap.remove(gameObjectInstance);
    }

    /**
     * Remove a (GameObjectClass, Image) pair from the internal repository of this class.
     *
     * @param gameObjectClass: A GameObjectInstance who together with its image will be removed.
     * @throws GameObjectClassNotFoundException
     */
    public static void removeClassImage(GameObjectClass gameObjectClass) throws GameObjectClassNotFoundException {
        if (!classImageMap.containsKey(gameObjectClass)) {
            throw new GameObjectClassNotFoundException(String.format("The Image preview for the GameObjectClass %s is not found", gameObjectClass.getClassName().getValue()));
        }
        classImageMap.remove(gameObjectClass);
    }
}
