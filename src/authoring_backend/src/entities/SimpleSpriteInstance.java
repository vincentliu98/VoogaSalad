package entities;

import groovy.api.BlockGraph;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;

import java.util.Map;
import java.util.function.Consumer;

public class SimpleSpriteInstance implements SpriteInstance {
    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleIntegerProperty tileId;
    private ObservableMap<String, String> propertiesMap;
    private Consumer<EntityInstance> returnInstanceIdFunc;

    SimpleSpriteInstance(String className, int tileId, ObservableMap<String, String> properties, Consumer<EntityInstance> returnInstanceIdFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.set(className);
        this.tileId = new ReadOnlyIntegerWrapper();
        this.tileId.setValue(tileId);
        this.propertiesMap = properties;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
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

    public Consumer<EntityInstance> getReturnInstanceIdFunc() {
        return returnInstanceIdFunc;
    }

    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
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

}
