package authoringInterface.subEditors;

import gameObjects.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * This is the editor for an "Entity" object that is opened when the user clicks on an existing entity or tries to add an entity to the game authoring library.
 *
 * @author Haotian Wang
 */
public class EntityEditor extends AbstractGameObjectEditor<EntityClass, EntityInstance> {
    private Text imageText;
    private Button chooseImage;
    private ImageView preview;
    private String imagePath;

    public EntityEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        inputText.setText("Your entity name:");
        imageText = new Text("Choose an image for your entity");
        chooseImage = new Button("Choose sprite");
        preview = new ImageView();
        // TODO: Select multiple images
        chooseImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                imagePath = file.toURI().toString();
                Image sprite = new Image(imagePath);
                preview.setImage(sprite);
                preview.setFitHeight(50);
                preview.setFitWidth(50);
            }
        });
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
                        gameObjectManager.createEntityClass(nameField.getText().trim());
                        EntityClass entityClass = gameObjectManager.getEntityClass(nameField.getText().trim());
                        TreeItem<String> newItem = new TreeItem<>(entityClass.getClassName().getValue());
                        entityClass.addImagePath(imagePath);
                        ImageView icon = new ImageView(preview.getImage());
                        icon.setFitWidth(50);
                        icon.setFitHeight(50);
                        newItem.setGraphic(icon);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        if (nodeEdited instanceof ImageView) {
                            ((ImageView) nodeEdited).setImage(preview.getImage());
                        } else if (nodeEdited instanceof Text) {
                            ((Text) nodeEdited).setText(nameField.getText());
                        }
                        // TODO make changes to the GameObjectInstance as well. Waiting for changes at JC's side.
                        break;
                    case EDIT_TREEITEM:
                        if (imagePath != null) {
                            gameObjectClass.addImagePath(imagePath);
                        }
                        gameObjectManager.changeGameObjectClassName(gameObjectClass.getClassName().getValue(), nameField.getText());
                        if (preview.getImage() != null) {
                            ImageView icon1 = new ImageView(preview.getImage());
                            icon1.setFitWidth(50);
                            icon1.setFitHeight(50);
                            treeItem.setGraphic(icon1);
                        }
                        treeItem.setValue(nameField.getText());
                        break;
                }
            }
        });
        setupLayout();
        rootPane.getChildren().addAll(imageText, chooseImage, preview);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
        // TODO: REmove this disgusting shite
        preview.setImage(new Image(gameObjectManager.getEntityClass(gameObjectInstance.getClassName().getValue()).getImagePathList().get(0)));
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        // TODO: REmove this disgusting shite
        try {
            preview.setImage(new Image(gameObjectManager.getEntityClass(gameObjectClass.getClassName().getValue()).getImagePathList().get(0)));
        } catch (IndexOutOfBoundsException e) {
            return;
        }
    }

    private void setupLayout() {
        imageText.setLayoutX(36);
        imageText.setLayoutY(176);
        chooseImage.setLayoutX(261);
        chooseImage.setLayoutY(158);
        preview.setLayoutX(37);
        preview.setLayoutY(206);
    }
}
