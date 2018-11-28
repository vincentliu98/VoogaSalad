package gameObjects.entity;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleIntegerProperty;

public interface EntityInstance extends GameObjectInstance {
    SimpleIntegerProperty getTileID();

    default GameObjectType getType() {
        return GameObjectType.ENTITY;
    }
}
