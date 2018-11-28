package gameObjects.tile;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;

public interface TileInstance extends GameObjectInstance {
    Point getCoord();

    default GameObjectType getType() {
        return GameObjectType.TILE;
    }
}
