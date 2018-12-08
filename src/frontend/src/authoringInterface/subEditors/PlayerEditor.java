package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;

public class PlayerEditor extends AbstractGameObjectEditor<PlayerClass, PlayerInstance> {

    PlayerEditor(GameObjectsCRUDInterface manager) {
        super(manager);
    }

    @Override
    protected void readGameObjectInstance() {

    }

    @Override
    protected void readGameObjectClass() {

    }
}
