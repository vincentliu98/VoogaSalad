package gameObjects.tile;

import gameObjects.gameObject.GameObjectInstance;
import grids.Point;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleTileInstance implements TileInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleObjectProperty<Point> coord;
    private ObservableList<String> imagePathList;
    private String imageSelector;
    private ObservableMap<String, String> propertiesMap;
    private Supplier<TileClass> getTileClassFunc;


    SimpleTileInstance(
            String className,
            Point topLeftCoord,
            ObservableList<String> imagePathList,
            ObservableMap<String, String> properties,
            Supplier<TileClass> getTileClassFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.setValue(className);
        this.instanceName = new SimpleStringProperty();
        this.coord = new SimpleObjectProperty<>(topLeftCoord);
        this.imagePathList = imagePathList;
        this.propertiesMap = properties;
        this.getTileClassFunc = getTileClassFunc;
        instanceId = new ReadOnlyIntegerWrapper();
    }


    @Override
    public ReadOnlyIntegerProperty getInstanceId() {
        return instanceId.getReadOnlyProperty();
    }

    @Override
    public void setInstanceId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(instanceId);
    }

    @Override
    public ReadOnlyStringProperty getClassName() {
        return className.getReadOnlyProperty();
    }

    public void setClassName(String name) {
        className.setValue(name);
    }

//    public Consumer<GameObjectInstance> getReturnInstanceIdFunc() {
//        return returnInstanceIdFunc;
//    }

    @Override
    public SimpleStringProperty getInstanceName() {
        return instanceName;
    }

    @Override
    public void setInstanceName(String newInstanceName) {
        instanceName.setValue(newInstanceName);
    }

    @Override
    public void addProperty(String propertyName, String defaultValue) {
        propertiesMap.put(propertyName, defaultValue);
    }

    @Override
    public void removeProperty(String propertyName) {
        propertiesMap.remove(propertyName);
    }

    @Override
    public boolean changePropertyValue(String propertyName, String newValue) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, newValue);
        return true;
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
    public Point getCoord() { return coord.get(); }
}
