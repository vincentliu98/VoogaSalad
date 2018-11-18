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

    SimpleSpriteInstance(String className, int tileId, Map<String, BlockGraph> properties) {
        this.className = new ReadOnlyStringWrapper();
        this.className.set(className);
        this.tileId = new ReadOnlyIntegerWrapper();
        this.tileId.setValue(tileId);


    }


    @Override
    public ReadOnlyIntegerProperty getInstanceId() {
        return instanceId.getReadOnlyProperty();
    }

    @Override
    public void setInstanceId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(instanceId);
    }
}
