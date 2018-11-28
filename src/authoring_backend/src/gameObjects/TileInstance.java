package gameObjects;

import grids.Point;

public interface TileInstance extends GameObjectInstance {
    Point getCoord();

    default GameObjectType getType() {
        return GameObjectType.TILE;
    }
}
