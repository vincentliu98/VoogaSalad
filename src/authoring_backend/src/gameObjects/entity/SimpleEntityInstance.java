package gameObjects.entity;

import grids.Point;
import javafx.beans.property.*;
import javafx.collections.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleEntityInstance implements EntityInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleIntegerProperty tileId;
    private SimpleObjectProperty<Point> coord;
    private SimpleIntegerProperty height;
    private SimpleIntegerProperty width;
    private ObservableList<String> imagePathList;
    private String imageSelector;
    private ObservableMap<String, String> propertiesMap;
    private Supplier<EntityClass> getEntityClassFunc;

    SimpleEntityInstance(
            String className,
            ObservableList<String> imagePathList,
            ObservableMap<String, String> properties,
            Supplier<EntityClass> getEntityClassFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.setValue(className);
        this.instanceName = new SimpleStringProperty(className);
        this.tileId = new SimpleIntegerProperty();
        this.imagePathList = imagePathList;
        this.propertiesMap = properties;
        this.getEntityClassFunc = getEntityClassFunc;
        instanceId = new ReadOnlyIntegerWrapper();
        this.width = new SimpleIntegerProperty();
        this.height = new SimpleIntegerProperty();
        this.coord = new SimpleObjectProperty<>();
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

    @Override
    public SimpleStringProperty getInstanceName() {
        return instanceName;
    }

    @Override
    public void setInstanceName(String newInstanceName) {
        instanceName.setValue(newInstanceName);
    }

    @Override
    public ObservableMap<String, String> getPropertiesMap() { return propertiesMap; }

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
    public EntityClass getGameObjectClass() {
        return getEntityClassFunc.get();
    }


    @Override
    public SimpleIntegerProperty getHeight() {
        return height;
    }

    @Override
    public SimpleIntegerProperty getWidth() {
        return width;
    }

    @Override
    public void setHeight(int newHeight) {
        height.setValue(newHeight);
    }

    @Override
    public void setWidth(int newWidth) {
        height.setValue(newWidth);
    }


    @Override
    public Point getCoord() { return coord.get(); }

    @Override
    public void setCoord(Point coord) {
        this.coord.set(coord);
    }

    @Override
    public void setTileId(int newTileId) {
        tileId.setValue(newTileId);
    }

    @Override
    public SimpleIntegerProperty getTileID() { return tileId; }
}
