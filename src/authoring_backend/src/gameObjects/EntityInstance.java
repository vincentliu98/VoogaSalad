package gameObjects;

import javafx.beans.property.SimpleIntegerProperty;

public interface EntityInstance extends GameObjectInstance {
    SimpleIntegerProperty getTileID();

    default GameObjectType getType() {
        return GameObjectType.ENTITY;
    }
}
