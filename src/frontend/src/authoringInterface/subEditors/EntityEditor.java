package authoringInterface.subEditors;

import authoringInterface.sidebar.treeItemEntries.Entity;
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
public class EntityEditor extends AbstractGameObjectEditor<Entity> {
    private Text imageText;
    private Button chooseImage;
    private ImageView preview;
    private Entity entity;

    public EntityEditor() {
        super();
        entity = new Entity();
        inputText.setText("Your entity name:");
        imageText = new Text("Choose an image for your entity");
        chooseImage = new Button("Choose sprite");
        preview = new ImageView();
        chooseImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                Image sprite = new Image(file.toURI().toString());
                entity.setSprite(sprite);
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
                entity.setName(nameField.getText().trim());
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
                    case ADD_TREEITEM:
                        int id = treeItem.getChildren().size();
                        TreeItem<String> newItem = new TreeItem<>(entity.getName());
                        ImageView preview = new ImageView(entity.getSprite());
                        preview.setFitWidth(50);
                        preview.setFitHeight(50);
                        newItem.setGraphic(preview);
                        entity.setId(id);
                        objectMap.put(newItem.getValue(), entity);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        if (nodeEdited instanceof ImageView) {
                            ((ImageView) nodeEdited).setImage(entity.getSprite());
                        } else if (nodeEdited instanceof Text) {
                            ((Text) nodeEdited).setText(nameField.getText());
                        }
                        break;
                    case EDIT_TREEITEM:
                        break;
                }
            }
        });
        setupLayout();
        rootPane.getChildren().addAll(imageText, chooseImage, preview);
    }

    private void setupLayout() {
        imageText.setLayoutX(36);
        imageText.setLayoutY(176);
        chooseImage.setLayoutX(261);
        chooseImage.setLayoutY(158);
        preview.setLayoutX(37);
        preview.setLayoutY(206);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(Entity userObject) {
        entity = userObject;
        nameField.setText(entity.getName());
        preview.setImage(entity.getSprite());
    }

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    @Override
    public Entity getObject() {
        return entity;
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public AnchorPane getView() {
        return rootPane;
    }
}
