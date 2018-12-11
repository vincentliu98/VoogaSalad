package gameObjects.entity;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidGameObjectInstanceException;
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

public interface EntityClass extends GameObjectClass {

    boolean isMovable();

    void setMovable(boolean move);

    EntityInstance createInstance(Point point)
            throws InvalidGameObjectInstanceException, GameObjectTypeException, InvalidIdException;

    /**
     * This method adds the image path to the GameObject Class and to all instances of the class.
     *
     * @param path file path of the image
     */
    void addImagePath(String path);

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
     * This method gets the image selector.
     *
     * @return image selector
     */
    BlockGraph getImageSelector();

    void setImageSelector(BlockGraph graph);


    @Override
    default GameObjectType getType() {
        return GameObjectType.ENTITY;
    }

    int getHeight();

    void setHeight(int newHeight);

    int getWidth();

    void setWidth(int newWidth);

    void equipContext(
            EntityInstanceFactory entityInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeEntityClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllEntityInstancesFunc,
            Function<Integer, Boolean> deleteEntityInstanceFunc
    );
}
