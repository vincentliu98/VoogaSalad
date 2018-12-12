package gameObjects.tile;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidPointsException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class TileInstanceFactory {
    private int numRows;
    private int numCols;
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;


    public TileInstanceFactory(
            int gridHeight,
            int gridWidth,
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc) {

        numRows = gridHeight;
        numCols = gridWidth;
        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public TileInstance createInstance(TileClass tilePrototype, Point topLeftCoord)
            throws GameObjectTypeException, InvalidIdException {
        // TODO locality
        if (topLeftCoord.outOfBounds(numRows, numCols)) {
            throw new InvalidPointsException();
        }
        if (tilePrototype.getType() != GameObjectType.TILE) {
            throw new GameObjectTypeException(GameObjectType.TILE);
        }
        var imagePathListCopy = new ArrayList<>(tilePrototype.getImagePathList());
        var propertiesMapCopy = new HashMap<>(tilePrototype.getPropertiesMap());
        TileInstance tileInstance = new SimpleTileInstance(tilePrototype.getClassName(), topLeftCoord, imagePathListCopy, propertiesMapCopy, tilePrototype);
        requestInstanceIdFunc.accept(tileInstance);
        addInstanceToMapFunc.accept(tileInstance);
        tileInstance.setHeight(tilePrototype.getHeight());
        tileInstance.setWidth(tilePrototype.getWidth());
        return tileInstance;

    }

    // TODO
    public void setDimensions(int gridHeight, int gridWidth) {
        numRows = gridHeight;
        numCols = gridWidth;
    }


}
