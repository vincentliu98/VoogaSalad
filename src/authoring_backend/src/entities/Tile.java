<<<<<<< HEAD:src/authoring_backend/entities/Tile.java
package authoring_backend.entities;
=======
package entities;
>>>>>>> master:src/authoring_backend/src/entities/Tile.java

import java.util.Iterator;
import java.util.Set;

public interface Tile extends Entity {
    Set getSprites();

    Iterator<Sprite> sprites();

    boolean isSpriteContainable();

    void  setSpriteContainable();
}
