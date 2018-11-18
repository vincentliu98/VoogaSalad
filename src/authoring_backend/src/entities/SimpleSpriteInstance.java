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
    private ObservableMap<String, BlockGraph> propertiesMap;
    private Consumer<EntityInstance> returnInstanceIdFunc;

    SimpleSpriteInstance(String className, int tileId, ObservableMap<String, BlockGraph> properties, Consumer<EntityInstance> returnInstanceIdFunc) {
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
}
