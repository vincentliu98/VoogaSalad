package gameObjects.player;

import authoringUtils.exception.InvalidOperationException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimplePlayerInstance implements PlayerInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;

    private SimpleStringProperty imagePath;
    private ObservableMap<String, String> propertiesMap;
    private Supplier<PlayerClass> getPlayerClassFunc;



    public SimplePlayerInstance(String className,
                                SimpleStringProperty imagePath,
                                ObservableMap<String, String> properties,
                                Supplier<PlayerClass> getPlayerClassFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.setValue(className);
        this.instanceName = new SimpleStringProperty(className);
        this.imagePath = imagePath;
        this.propertiesMap = properties;
        this.getPlayerClassFunc = getPlayerClassFunc;
        this.entitiesSet = FXCollections.observableSet();
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
    public ReadOnlyStringProperty getClassName() { return className; }

    @Override
    public void setClassName(String name) throws InvalidOperationException {
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
    public SimpleStringProperty getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String newImagePath) {
        imagePath.setValue(newImagePath);
    }

    @Override
    public PlayerClass getGameObjectClass() {
        return getPlayerClassFunc.get();
    }

}
