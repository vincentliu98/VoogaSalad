package gameObjects.player;

import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

/**
 * @author Yunhao Qing
 */


public interface PlayerInstance extends GameObjectInstance {
    String getImagePath();

    void setImagePath(String newImagePath);

    PlayerClass getGameObjectClass();

    @Override
    default GameObjectType getType() {
        return GameObjectType.PLAYER;
    }

}
