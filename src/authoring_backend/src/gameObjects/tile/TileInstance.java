package gameObjects.tile;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;

import java.util.List;

public interface TileInstance extends GameObjectInstance {

    /**
     * This method adds the image path to the GameObject Class and to all instances of the class.
     *
     * @param imagePath file path of the image
     */
    void addImagePath(String imagePath);

    /**
     * This method gets the image path list of the GameObject Class.
     *
     * @return a list of the file paths of the images
     */
    List<String> getImagePathList();

    /**
     * This method removes the image path from the Entity Class and from all instances of the class.
     *
     * @param index index of the image file path in the list
     * @return true if the image file path successfully removed
     */
    boolean removeImagePath(int index);

    /**
     * This method sets the GroovyCode for choosing the image to display from the list of images.
     *
     * @param blockCode GroovyCode
     */
    void setImageSelector(String blockCode);

    /**
     * This method gets the image selector code.
     *
     * @return image selector code
     */
    String getImageSelectorCode();

    Point getCoord();

    void setCoord(Point coord);

    int getHeight();

    void setHeight(int newHeight);

    int getWidth();

    void setWidth(int newWidth);


    TileClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.TILE;
    }
}
