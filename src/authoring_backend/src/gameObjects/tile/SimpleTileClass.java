package gameObjects.tile;

import gameObjects.TriConsumer;
import gameObjects.TriFunction;
import gameObjects.exception.InvalidPointsException;
import gameObjects.gameObject.GameObjectInstance;
import grids.Point;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleTileClass implements TileClass {
    private int numRow, numCol;

    private String CONST_CLASSNAME = "className";
    private String CONST_ID = "id";
    private String CONST_ENTITYCONTAINABLE = "entityContainable";

    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleBooleanProperty entityContainable;
    private SimpleIntegerProperty width, height;
    private ObservableList<String> imagePathList;
    private ObservableMap<String, String> propertiesMap;
    private String imageSelector;

    private TriFunction<Point, Integer, Integer, Boolean> verifyPointsFunc;

    private Consumer<GameObjectInstance> setInstanceIdFunc;
    private Consumer<GameObjectInstance> returnInstanceIdFunc;
    private Function<String, Set<GameObjectInstance>> getTileInstancesFunc;
    private Consumer<TileInstance> addTileInstanceToMapFunc;
    private Function<Integer, Boolean> deleteTileInstanceFromMapFunc;
    private TriConsumer<String, String, String> addTilePropertyFunc;
    private BiConsumer<String, String> removeTilePropertyFunc;

    private SimpleTileClass(String name) {
        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME, name);
        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
        entityContainable = new SimpleBooleanProperty(this, CONST_ENTITYCONTAINABLE);
        imagePathList = FXCollections.observableArrayList();
        propertiesMap = FXCollections.observableHashMap();
        imageSelector = "";
        width = new SimpleIntegerProperty(1);
        height = new SimpleIntegerProperty(1);
    }

    SimpleTileClass(String name,
                    int numRow, int numCol,
                    TriFunction<Point, Integer, Integer, Boolean> verifyPointsFunc,
                    Consumer<GameObjectInstance> setInstanceIdFunc,
                    Consumer<GameObjectInstance> returnInstanceIdFunc,
                    Consumer<TileInstance> addTileInstanceToMapFunc,
                    Function<Integer, Boolean> deleteTileInstanceFromMapFunc,
                    Function<String, Set<GameObjectInstance>> getTileInstancesFunc,
                    TriConsumer<String, String, String> addTilePropertyFunc,
                    BiConsumer<String, String> removeTilePropertyFunc) {
        this(name);
        this.numRow = numRow;
        this.numCol = numCol;
        this.verifyPointsFunc = verifyPointsFunc;
        this.setInstanceIdFunc = setInstanceIdFunc;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
        this.addTileInstanceToMapFunc = addTileInstanceToMapFunc;
        this.deleteTileInstanceFromMapFunc = deleteTileInstanceFromMapFunc;
        this.getTileInstancesFunc = getTileInstancesFunc;
        this.addTilePropertyFunc = addTilePropertyFunc;
        this.removeTilePropertyFunc = removeTilePropertyFunc;
    }

    @Override
    public ReadOnlyIntegerProperty getClassId() {
        return classId.getReadOnlyProperty();
    }

    @Override
    public void setClassId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(classId);
    }

    @Override
    public ReadOnlyStringProperty getClassName() {
        return className.getReadOnlyProperty();
    }

    @Override
    public void setClassName(Consumer<SimpleStringProperty> setFunc) {
        setFunc.accept(className);
    }

    @Override
    public ObservableMap getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            propertiesMap.put(propertyName, defaultValue);
            addTilePropertyFunc.accept(className.getValue(), propertyName, defaultValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        removeTilePropertyFunc.accept(className.getValue(), propertyName);
        return propertiesMap.remove(propertyName) != null;
    }


    @Override
    public ObservableList getImagePathList() {
        return imagePathList;
    }

    @Override
    public void addImagePath(String path) {
        imagePathList.add(path);
    }


    @Override
    public boolean removeImagePath(int index) {
        try {
            imagePathList.remove(index);
            return true;
        }
        catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @Override
    public void setImageSelector(String blockCode) {
        imageSelector = blockCode;
    }

    @Override
    public String getImageSelectorCode() {
        return imageSelector;
    }

    @Override
    public GameObjectInstance createInstance(Point coord) {
        if (verifyPointsFunc.apply(coord, numRow, numCol)) {
            throw new InvalidPointsException();
        }
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        propertiesMapCopy.putAll(propertiesMap);
        TileInstance tileInstance = new SimpleTileInstance(className.get(), coord, propertiesMapCopy, returnInstanceIdFunc);
        setInstanceIdFunc.accept(tileInstance);
        addTileInstanceToMapFunc.accept(tileInstance);
        return tileInstance;

    }

    public boolean deleteInstance(int tileInstanceId) {
        return deleteTileInstanceFromMapFunc.apply(tileInstanceId);
    }

    @Override
    public Set<GameObjectInstance> getInstances() {
        return getTileInstancesFunc.apply(getClassName().getValue());
    }

    @Override
    public SimpleIntegerProperty getWidth() { return width; }

    @Override
    public SimpleIntegerProperty getHeight() { return height; }


    @Override
    public SimpleBooleanProperty isEntityContainable() {
        return entityContainable;
    }

    @Override
    public void setEntityContainable(boolean contains) {
        entityContainable.setValue(contains);
    }
}
