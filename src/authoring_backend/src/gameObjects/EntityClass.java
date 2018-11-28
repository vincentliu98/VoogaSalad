package gameObjects;

import javafx.beans.property.SimpleBooleanProperty;

public interface EntityClass extends GameObjectClass {

    SimpleBooleanProperty isMovable();

    void setMovable(boolean move);

    GameObjectInstance createInstance(int tileId);

    default GameObjectType getType() {
        return GameObjectType.ENTITY;
    }
}
