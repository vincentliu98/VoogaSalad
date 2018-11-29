package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.ErrorWindow;

/**
 * Editor to change the Tile settings. Need to work on it. Low priority
 *
 * @author jl729, Haotian Wang
 */

public class TileEditor extends AbstractGameObjectEditor<TileClass, TileInstance> {
    private TextField widthText = new TextField();
    private TextField heightText = new TextField();
    private GridPane geometry = new GridPane();
    private Label widthLabel = new Label();
    private Label heightLabel = new Label();
    private ObservableList<String> imagePaths = FXCollections.observableArrayList();
    private double width;
    private double height;

    public TileEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        nameLabel.setText("Your tile name");
        widthText.setPromptText("Width");
        heightText.setPromptText("Height");
        nameField.setPromptText("Tile name");
        widthLabel.setText("Width");
        heightLabel.setText("Height");
        geometry.setHgap(30);
        geometry.addRow(0, widthLabel, widthText);
        geometry.addRow(1, heightLabel, heightText);

        rootPane.getChildren().addAll(
                geometry);

        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                new ErrorWindow("Empty name", "You must give your tile a non-empty name").showAndWait();
            } else if (widthText.getText().trim().isEmpty()) {
                new ErrorWindow("Empty width", "You must specify a width for this tile class").showAndWait();
            } else if (heightText.getText().trim().isEmpty()) {
                new ErrorWindow("Empty height", "You must specify a height for this tile class").showAndWait();
            } else {
                try {
                    width = Double.parseDouble(widthText.getText());
                } catch (NumberFormatException e1) {
                    new ErrorWindow("Incorrect width", "The input width is in an unsupported format");
                    return;
                }
                try {
                    height = Double.parseDouble(heightText.getText());
                } catch (NumberFormatException e1) {
                    new ErrorWindow("Incorrect height", "The input height is in an unsupported format");
                    return;
                }
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
//                    case ADD_TREEITEM:
//                        gameObjectManager.createTileClass(nameField.getText().trim());
//                        TileClass TileClass = gameObjectManager.getTileClass(nameField.getText().trim());
//                        TreeItem<String> newItem = new TreeItem<>(TileClass.getClassName().getValue());
//                        TileClass.getImagePathList().addAll(imagePaths);
//                        ImageView icon = new ImageView(imagePaths.get(0));
//                        icon.setFitWidth(50);
//                        icon.setFitHeight(50);
//                        newItem.setGraphic(icon);
//                        treeItem.getChildren().add(newItem);
//                        break;
//                    case NONE:
//                        return;
//                    case EDIT_NODE:
//                        if (nodeEdited instanceof ImageView) {
//                            ((ImageView) nodeEdited).setImage(new Image(imagePaths.get(0)));
//                        } else if (nodeEdited instanceof Text) {
//                            ((Text) nodeEdited).setText(nameField.getText());
//                        }
//                        // TODO make changes to the GameObjectInstance as well. Waiting for changes at JC's side.
//                        break;
//                    case EDIT_TREEITEM:
//                        gameObjectClass.getImagePathList().clear();
//                        gameObjectClass.getImagePathList().addAll(imagePaths);
//                        gameObjectManager.changeGameObjectClassName(gameObjectClass.getClassName().getValue(), nameField.getText());
//                        if (!imagePaths.isEmpty()) {
//                            ImageView icon1 = new ImageView(imagePaths.get(0));
//                            icon1.setFitWidth(50);
//                            icon1.setFitHeight(50);
//                            treeItem.setGraphic(icon1);
//                        }
//                        treeItem.setValue(nameField.getText());
//                        break;
                }
            }
        });
        setupLayout();
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
        // TODO: REmove this disgusting shite
        imagePaths.addAll(gameObjectManager.getTileClass(gameObjectInstance.getClassName().getValue()).getImagePathList());
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        // TODO: REmove this disgusting shite
        imagePaths.addAll(gameObjectClass.getImagePathList());
    }

    private void setupLayout() {
        geometry.setLayoutX(50);
        geometry.setLayoutY(100);
    }
}
