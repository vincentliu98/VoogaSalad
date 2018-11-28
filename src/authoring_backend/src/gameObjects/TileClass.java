package gameObjects;

import grids.Point;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public interface TileClass extends GameObjectClass {
    SimpleIntegerProperty getWidth();
    SimpleIntegerProperty getHeight();

    SimpleBooleanProperty isEntityContainable();

    void setEntityContainable(boolean contains);

    GameObjectInstance createInstance(Point coord);

    default GameObjectType getType() {
        return GameObjectType.TILE;
    }
}
