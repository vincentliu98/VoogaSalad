package gameObjects;

import javafx.beans.property.SimpleBooleanProperty;

public interface SpriteClass extends GameObjectClass {

    SimpleBooleanProperty isMovable();

    void setMovable(boolean move);

    GameObjectInstance createInstance(int tileId);
}
