package gameObjects.tile;

import authoringUtils.exception.*;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.*;
import grids.Point;
import javafx.collections.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

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
            throw new GameObjectTypeException("tilePrototype is not of Tile Class");
        }
        ObservableList imagePathListCopy = FXCollections.observableArrayList();
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        imagePathListCopy.addAll(tilePrototype.getImagePathList());
        propertiesMapCopy.putAll(tilePrototype.getPropertiesMap());
        Supplier<TileClass> getTileClassFunc = () -> tilePrototype;
        TileInstance tileInstance = new SimpleTileInstance(tilePrototype.getClassName().getValue(), topLeftCoord, imagePathListCopy, propertiesMapCopy, getTileClassFunc);
        requestInstanceIdFunc.accept(tileInstance);
        addInstanceToMapFunc.accept(tileInstance);
        return tileInstance;

    }

    // TODO
    public void setDimensions(int gridHeight, int gridWidth) {
        numRows = gridHeight;
        numCols = gridWidth;
    }



}
