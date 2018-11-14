package entities;

import java.util.Iterator;
import java.util.Set;

public interface TileClass extends EntityClass {
    Set getSprites();

    Iterator<SpriteClass> sprites();

    boolean isSpriteContainable();

    void  setSpriteContainable();
}
