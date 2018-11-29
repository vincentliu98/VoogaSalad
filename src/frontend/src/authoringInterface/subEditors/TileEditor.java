package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

        rootPane.getChildren().addAll(
                new Label("name"), nameText,
                new Label("Width"), widthText,
                new Label("Height"), heightText);

        confirm.setOnAction(e -> {

        });
        setupLayout();
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
        widthText.setLayoutX(36);
        widthText.setLayoutY(176);
        heightText.setLayoutX(261);
        heightText.setLayoutY(158);
        nameText.setLayoutX(37);
        nameText.setLayoutY(206);
    }
}
