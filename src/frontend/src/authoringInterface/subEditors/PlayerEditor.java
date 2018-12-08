package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;

public class PlayerEditor extends AbstractGameObjectEditor<PlayerClass, PlayerInstance> {

    PlayerEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        nameLabel.setText("Your Player Name");
        nameField.setPromptText("Player 0");
    }

    @Override
    protected void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
    }

    @Override
    protected void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
    }
}
