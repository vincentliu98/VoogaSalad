package entities;

import grids.Point;
import groovy.api.BlockGraph;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleTileClass implements TileClass {

    private String CONST_CLASSNAME = "className";
    private String CONST_ID = "id";
    private String CONST_SPRITECONTAINABLE = "spriteContainable";

    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleBooleanProperty spriteContainable;
    private ObservableList<String> imagePathList;
    private ObservableMap<String, BlockGraph> propertiesMap;
    private BlockGraph imageSelector;

    private Function<Set<Point>, Boolean> verifyPointsFunc;

    private Function<String, Set<EntityInstance>> getTileInstancesFunc;
    private Consumer<EntityInstance> setInstanceIdFunc;
    private Consumer<EntityInstance> returnInstanceIdFunc;
    private Consumer<TileInstance> addTileInstanceToMapFunc;

    private SimpleTileClass() {
        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME);
        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
        spriteContainable = new SimpleBooleanProperty(this, CONST_SPRITECONTAINABLE);
        imagePathList = FXCollections.observableArrayList();
        propertiesMap = FXCollections.observableHashMap();
    }

    SimpleTileClass(Function<Set<Point>, Boolean> verifyPointsFunc,
                    Consumer<EntityInstance> setInstanceIdFunc,
                    Consumer<EntityInstance> returnInstanceIdFunc,
                    Consumer<TileInstance> addTileInstanceToMapFunc,
                    Function<String, Set<EntityInstance>> getTileInstancesFunc) {
        this();
        this.verifyPointsFunc = verifyPointsFunc;
        this.setInstanceIdFunc = setInstanceIdFunc;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
        this.addTileInstanceToMapFunc = addTileInstanceToMapFunc;
        this.getTileInstancesFunc = getTileInstancesFunc;
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
    public boolean addProperty(String propertyName, BlockGraph defaultValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            propertiesMap.put(propertyName, defaultValue);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName) {
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
    public void setImageSelector(BlockGraph blockCode) {
        imageSelector = blockCode;
    }

    @Override
    public BlockGraph getImageSelectorCode() {
        return imageSelector;
    }

    @Override
    public Set<EntityInstance> getInstances() {
        return null;
    }

    @Override
    public EntityInstance createInstance(Set<Point> points) {
        if (!verifyPointsFunc.apply(points)) {
            throw new InvalidPointsException();
        }
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        propertiesMapCopy.putAll(propertiesMap);
        TileInstance tileInstance = new SimpleTileInstance(className.getName(), points, propertiesMapCopy, returnInstanceIdFunc);
        setInstanceIdFunc.accept(tileInstance);
        addTileInstanceToMapFunc.accept(tileInstance);
        return tileInstance;

    }

    @Override
    public SimpleBooleanProperty isSpriteContainable() {
        return spriteContainable;
    }

    @Override
    public void setSpriteContainable(boolean contains) {
        spriteContainable.setValue(contains);
    }
}
