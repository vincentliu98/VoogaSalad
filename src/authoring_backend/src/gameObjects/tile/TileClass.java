package gameObjects.tile;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;
import groovy.api.BlockGraph;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public interface TileClass extends GameObjectClass {

    int getWidth();

    void setWidth(int width);

    int getHeight();

    void setHeight(int height);

    boolean isEntityContainable();

    void setEntityContainable(boolean contains);

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

    BlockGraph getImageSelector();

    void setImageSelector(BlockGraph graph);

    TileInstance createInstance(Point topLeftCoord) throws GameObjectTypeException, InvalidIdException;

    @Override
    default GameObjectType getType() {
        return GameObjectType.TILE;
    }

    void equipContext(
            TileInstanceFactory tileInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeTileClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllTileInstancesFunc,
            Function<Integer, Boolean> deleteTileInstanceFunc
    );
}
