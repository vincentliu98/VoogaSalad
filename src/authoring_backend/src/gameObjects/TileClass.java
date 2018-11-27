package entities;

import grids.Point;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Iterator;
import java.util.Set;

public interface TileClass extends EntityClass {

    SimpleBooleanProperty isSpriteContainable();

    void setSpriteContainable(boolean contains);

    EntityInstance createInstance(Set<Point> points);
}
