package entities;

import java.util.Iterator;
import java.util.Set;

public interface TileClass extends EntityClass {

    boolean isSpriteContainable();

    void setSpriteContainable(boolean contains);
}
