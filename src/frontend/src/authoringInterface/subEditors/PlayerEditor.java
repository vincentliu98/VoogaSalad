package authoringInterface.subEditors;

import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;
import javafx.scene.control.TreeItem;
import utils.ErrorWindow;

public class PlayerEditor extends AbstractGameObjectEditor<PlayerClass, PlayerInstance> {

    PlayerEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        nameLabel.setText("Your Player Name");
        nameField.setPromptText("Player 0");

        confirm.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                new ErrorWindow("Empty name", "You must give your player a non-empty name").showAndWait();
            } else {
                switch (editingMode) {
                    case ADD_TREEITEM:
                        try {
                            gameObjectManager.createPlayerClass(nameField.getText().trim());
                        } catch (DuplicateGameObjectClassException e1) {
                            // TODO
                            e1.printStackTrace();
                        }
                        //TODO: if backend ready, use below
                        PlayerClass playerClass = null;
//                        try {
//                            playerClass = gameObjectManager.getPlayerClass(nameField.getText().trim());
//                        } catch (GameObjectClassNotFoundException e1) {
//                            // TODO
//                            e1.printStackTrace();
//                        }
//                        assert PlayerClass != null;
//                        TreeItem<String> newItem = new TreeItem<>(PlayerClass.getClassName().getValue());
                }
            }
        });
    }

    @Override
    protected void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
    }

    @Override
    protected void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
    }

    /**
     * This method sets up the confirm logic of adding new TreeItem.
     */
    @Override
    protected void confirmAddTreeItem() {

    }

    /**
     * This method sets up the confirm logic of editing existing TreeItem.
     */
    @Override
    protected void confirmEditTreeItem() {

    }

    /**
     * This method sets up the confirm logic of editing existing Node.
     */
    @Override
    protected void confirmEditNode() {

    }
}
