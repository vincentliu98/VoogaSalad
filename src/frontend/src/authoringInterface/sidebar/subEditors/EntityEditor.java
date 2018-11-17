package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import authoringInterface.sidebar.treeItemEntries.Entity;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * This is the editor for an "Entity" object that is opened when the user clicks on an existing entity or tries to add an entity to the game authoring library.
 *
 * @author Haotian Wang
 */
public class EntityEditor extends AbstractObjectEditor implements ObjectEditor<Entity>, SubView<AnchorPane> {
    private Text imageText;
    private Button chooseImage;
    private VBox imageBox;
    private Entity entity;

    public EntityEditor() {
        super();
        entity = new Entity();
        inputText.setText("Your entity name:");
        imageText = new Text("Choose an image for your entity");
        chooseImage = new Button("Choose sprite");
        chooseImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                Image sprite = new Image(file.toURI().toString());
                entity.setSprite(sprite);
                imageBox.getChildren().add(new ImageView(sprite));
            }
        });
        imageBox = new VBox();
        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Name");
                alert.setContentText("You must give your entity a non-empty name");
                alert.showAndWait();
            } else {
                entity.setName(nameField.getText().trim());
                isApplied = true;
                ((Stage) rootPane.getScene().getWindow()).close();
            }
        });
        setupLayout();
        rootPane.getChildren().addAll(imageText, chooseImage, imageBox);
    }

    private void setupLayout() {
        imageText.setLayoutX(36);
        imageText.setLayoutY(176);
        chooseImage.setLayoutX(261);
        chooseImage.setLayoutY(158);
        imageBox.setPrefSize(164, 171);
        imageBox.setLayoutX(37);
        imageBox.setLayoutY(206);
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
        imageBox.getChildren().set(0, new ImageView(entity.getSprite()));
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
     * Return a boolean indicating whether the changes are successfully applied.
     *
     * @return
     */
    @Override
    public boolean applied() {
        return isApplied;
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
