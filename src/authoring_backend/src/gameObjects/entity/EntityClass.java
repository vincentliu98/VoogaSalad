package gameObjects.entity;

import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleBooleanProperty;

public interface EntityClass extends GameObjectClass {

    SimpleBooleanProperty isMovable();

    void setMovable(boolean move);

    GameObjectInstance createInstance(int tileId);

    @Override
    default GameObjectType getType() {
        return GameObjectType.ENTITY;
    }
}
