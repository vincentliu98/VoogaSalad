package gameObjects.player;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleIntegerProperty;

public interface PlayerInstance extends GameObjectInstance{
    SimpleIntegerProperty getPlayerID();

    @Override
    default GameObjectType getType() {
        return GameObjectType.PLAYER;
    }

}
