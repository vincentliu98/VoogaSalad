package gameObjects;

import javafx.beans.property.SimpleBooleanProperty;

public interface SpriteClass extends EntityClass {

    SimpleBooleanProperty isMovable();

    void setMovable(boolean move);

    EntityInstance createInstance(int tileId);
}
