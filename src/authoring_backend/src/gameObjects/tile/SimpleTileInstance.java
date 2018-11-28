package gameObjects.tile;

import gameObjects.gameObject.GameObjectInstance;
import grids.Point;
import javafx.beans.property.*;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;

public class SimpleTileInstance implements TileInstance {
    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper instanceId;
    private SimpleObjectProperty<Point> coord;
    private ObservableMap<String, String> propertiesMap;
    private Consumer<GameObjectInstance> returnInstanceIdFunc;


    SimpleTileInstance(String className, Point coord, ObservableMap<String, String> properties, Consumer<GameObjectInstance> returnInstanceIdFunc) {
        this.className = new ReadOnlyStringWrapper();
        this.className.set(className);
        this.coord = new SimpleObjectProperty<>(coord);
        this.propertiesMap = properties;
        this.returnInstanceIdFunc = returnInstanceIdFunc;
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

    public Consumer<GameObjectInstance> getReturnInstanceIdFunc() {
        return returnInstanceIdFunc;
    }

    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        return false;
    }

    @Override
    public boolean removeProperty(String propertyName) {
        return false;
    }

    @Override
    public Point getCoord() { return coord.get(); }
}
