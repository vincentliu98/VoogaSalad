package gameObjects;

import javafx.beans.property.SimpleIntegerProperty;

public interface EntityInstance extends GameObjectInstance {
    SimpleIntegerProperty getTileID();
}
