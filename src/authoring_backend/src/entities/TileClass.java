package entities;

import grids.Point;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Iterator;
import java.util.Set;

public interface TileClass extends EntityClass {


    void setDefaultHeightWidth(int defaultHeight, int defaultWidth);

    SimpleIntegerProperty getDefaultHeight();

    SimpleIntegerProperty getDefaultWidth();

    SimpleBooleanProperty isSpriteContainable();

    void setSpriteContainable(boolean contains);

    EntityInstance createInstance(Set<Point> points);
}
