package gameObjects.tile;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidPointsException;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TileInstanceFactory {
    private int numRows;
    private int numCols;
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private Consumer<GameObjectInstance> addInstanceToMapFunc;


    public TileInstanceFactory(
            int gridHeight,
            int gridWidth,
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            Consumer<GameObjectInstance> addInstanceToMapFunc) {

        numRows = gridHeight;
        numCols = gridWidth;
        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public TileInstance createInstance(TileClass tilePrototype, Point topLeftCoord)
            throws GameObjectTypeException {
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
