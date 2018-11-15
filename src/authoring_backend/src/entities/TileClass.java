package entities;

import javafx.beans.property.SimpleBooleanProperty;

import java.util.Iterator;
import java.util.Set;

public interface TileClass extends EntityClass {

    SimpleBooleanProperty isSpriteContainable();

    void setSpriteContainable(boolean contains);
}
