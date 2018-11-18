package entities;

import grids.Point;
import groovy.api.BlockGraph;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class SimpleTileInstance implements TileInstance {
    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper instanceId;
    private Set<Point> points;
    private ObservableMap<String, BlockGraph> propertiesMap;
    private Consumer<EntityInstance> returnInstanceIdFunc;


    SimpleTileInstance(String className, Set<Point> points, ObservableMap<String, BlockGraph> properties, Consumer<EntityInstance> returnInstanceIdFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.set(className);
        this.points = new HashSet<>();
        this.points.addAll(points);
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
