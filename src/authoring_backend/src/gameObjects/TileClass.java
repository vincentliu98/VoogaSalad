package gameObjects;

import grids.Point;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.Set;

public interface TileClass extends GameObjectClass {

    SimpleBooleanProperty isSpriteContainable();

    void setSpriteContainable(boolean contains);

    GameObjectInstance createInstance(Set<Point> points);
}
