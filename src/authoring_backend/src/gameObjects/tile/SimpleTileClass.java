package gameObjects.tile;

import authoringUtils.exception.*;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.*;
import grids.Point;
import javafx.beans.property.*;
import javafx.collections.*;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleTileClass implements TileClass {
    private int numRow, numCol;

    private static final String CONST_CLASSNAME = "className";
    private static final String CONST_ID = "id";
    private static final String CONST_ENTITYCONTAINABLE = "entityContainable";
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;

    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleBooleanProperty entityContainable;
    private SimpleIntegerProperty width, height;
    private ObservableList<String> imagePathList;
    private ObservableMap<String, String> propertiesMap;
    private String imageSelector;

    private TileInstanceFactory myFactory;
    private ThrowingBiConsumer<String, String, InvalidOperationException> changeTileClassNameFunc;
    private Function<String, Collection<GameObjectInstance>> getAllTileInstancesFunc;
    private Function<Integer, Boolean> deleteTileInstanceFunc;

    public SimpleTileClass(String name) {
        className = new ReadOnlyStringWrapper(this, CONST_CLASSNAME, name);
        classId = new ReadOnlyIntegerWrapper(this, CONST_ID);
        entityContainable = new SimpleBooleanProperty(this, CONST_ENTITYCONTAINABLE);
        imagePathList = FXCollections.observableArrayList();
        propertiesMap = FXCollections.observableHashMap();
        imageSelector = "";
        width = new SimpleIntegerProperty(DEFAULT_WIDTH);
        height = new SimpleIntegerProperty(DEFAULT_HEIGHT);
    }

    public SimpleTileClass(
            String className,
            TileInstanceFactory tileInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeTileClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllTileInstancesFunc,
            Function<Integer, Boolean> deleteTileInstanceFunc) {
        this(className);
        this.myFactory = tileInstanceFactory;
        this.changeTileClassNameFunc = changeTileClassNameFunc;
        this.getAllTileInstancesFunc = getAllTileInstancesFunc;
        this.deleteTileInstanceFunc = deleteTileInstanceFunc;
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
    public void changeClassName(String newClassName)
            throws InvalidOperationException {
        changeTileClassNameFunc.accept(className.getValue(), newClassName);
    }

    @Override
    public void setClassName(String newClassName) {
        className.setValue(newClassName);
    }

    @Override
    public ObservableMap<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        if (propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, defaultValue);
        Collection<TileInstance> tileInstances = getAllInstances();
        for (TileInstance t : tileInstances) {
            t.addProperty(propertyName, defaultValue);
        }
        return true;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.remove(propertyName);
        Collection<TileInstance> tileInstances = getAllInstances();
        for (TileInstance t : tileInstances) {
            t.removeProperty(propertyName);
        }
        return true;
    }


    @Override
    public ObservableList<String> getImagePathList() {
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
    public TileInstance createInstance(Point topLeftCoord)
            throws GameObjectTypeException, InvalidIdException {
        return myFactory.createInstance(this, topLeftCoord);

    }

    @Override
    public void setHeight(int height) {
        this.height.set(height);
    }

    @Override
    public void setWidth(int width) {
        this.width.set(width);
    }

    public boolean deleteInstance(int tileInstanceId) {
        return deleteTileInstanceFunc.apply(tileInstanceId);
    }

    @Override
    public Collection<TileInstance> getAllInstances() {
        ObservableSet<TileInstance> s = FXCollections.observableSet();
        Collection<GameObjectInstance> instances = getAllTileInstancesFunc.apply(getClassName().getValue());
        for (GameObjectInstance i : instances) {
            if (i.getType() == GameObjectType.TILE) {
                s.add((TileInstance) i);
            }
        }
        return s;
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
