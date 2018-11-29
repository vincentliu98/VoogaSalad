package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * Editor to change the Tile settings. Need to work on it. Low priority
 *
 * @author jl729
 */

public class TileEditor extends AbstractGameObjectEditor<TileClass, TileInstance> {
    private TextField widthText = new TextField();
    private TextField heightText = new TextField();
    private TextField nameText = new TextField();

    public TileEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        widthText.setPromptText("Width");
        heightText.setPromptText("Height");
        nameText.setPromptText("Tile Name");
        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Name");
                alert.setContentText("You must give your entity a non-empty name");
                alert.showAndWait();
            } else {
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
                    case ADD_TREEITEM:
                        TreeItem<String> newItem = new TreeItem<>();
//                        entityClass.addImagePath(imagePath);
//                        ImageView icon = new ImageView(preview.getImage());
//                        icon.setFitWidth(50);
//                        icon.setFitHeight(50);
//                        newItem.setGraphic(icon);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        break;
                    case EDIT_TREEITEM:
                        break;
                }
            }
        });
        setupLayout();
        rootPane.getChildren().addAll();
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {

    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {

    }

    private void setupLayout() {

    }
}
