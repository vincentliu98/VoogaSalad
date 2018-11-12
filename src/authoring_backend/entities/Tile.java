package authoring_backend.entities;

import java.util.Iterator;
import java.util.Set;

public interface Tile extends Entity {
    Set getSprites();

    Iterator<Sprite> sprites();

    boolean isSpriteContainable();

    void  setSpriteContainable();
}
