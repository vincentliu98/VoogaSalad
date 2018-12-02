package gameObjects.player;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public class SimplePlayerInstance implements PlayerInstance {
    private ReadOnlyStringWrapper className;
    private SimpleStringProperty instanceName;
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleStringProperty imagePath;
    private ObservableMap<String, String> propertiesMap;
    private ObservableSet<Integer> entitiesSet;
//    Supplier<PlayerClass> getPlayerClassFunc;


    public SimplePlayerInstance(
            String className) {
        this.className = new ReadOnlyStringWrapper(className);
        this.instanceName = new SimpleStringProperty();
        this.imagePath = new SimpleStringProperty();
        this.propertiesMap = FXCollections.observableHashMap();
        this.entitiesSet = FXCollections.observableSet();
//        this.getPlayerClassFunc = getPlayerClassFunc;
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

    public Set<Integer> getEntityIDs() { return new TreeSet<>(entitiesSet); }

    @Override
    public boolean addEntity(int entityId) {
        if (!entitiesSet.contains(entityId)){
            entitiesSet.add(entityId);
            return true;
        }
        else return false;
    }

    @Override
    public boolean removeEntity(int entityId) {
        return entitiesSet.remove(entityId);
    }

    @Override
    public void removeAllEntities() {
        entitiesSet.clear();
    }

//    @Override
//    public PlayerClass getGameObjectClass() {
//        return getPlayerClassFunc.get();
//    }

}
