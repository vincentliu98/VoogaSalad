package gameObjects.entity;

import gameObjects.gameObject.GameObjectInstance;
import grids.Point;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleEntityInstance implements EntityInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleIntegerProperty tileId;
    private ObservableList<String> imagePathList;
    private String imageSelector;
    private ObservableMap<String, String> propertiesMap;
    private Supplier<EntityClass> getEntityClassFunc;

    SimpleEntityInstance(
            String className,
            int tileId,
            ObservableList<String> imagePathList,
            ObservableMap<String, String> properties,
            Supplier<EntityClass> getEntityClassFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.setValue(className);
        this.instanceName = new SimpleStringProperty();
        this.tileId = new SimpleIntegerProperty();
        this.tileId.setValue(tileId);
        this.imagePathList = imagePathList;
        this.propertiesMap = properties;
        this.getEntityClassFunc = getEntityClassFunc;
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
    public EntityClass getGameObjectClass() {
        return getEntityClassFunc.get();
    }

    @Override
    public SimpleIntegerProperty getTileID() { return tileId; }
}
