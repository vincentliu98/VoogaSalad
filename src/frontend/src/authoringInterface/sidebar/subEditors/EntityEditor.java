package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import authoringInterface.sidebar.treeItemEntries.Entity;
import javafx.scene.Scene;
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
public class EntityEditor implements ObjectEditor<Entity>, SubView<AnchorPane> {
    private static final double WIDTH = 500;
    private static final double HEIGHT = 500;
    private AnchorPane rootPane;
    private Text inputText;
    private TextField nameField;
    private Text imageText;
    private Button chooseImage;
    private VBox imageBox;
    private Button confirm;
    private Button cancel;
    private Entity entity;

    public EntityEditor() {
        entity = new Entity();
        rootPane = new AnchorPane();
        inputText = new Text("Your entity name:");
        nameField = new TextField();
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
        confirm = new Button("Apply");
        confirm.setOnAction(e -> {
            entity.setName(nameField.getText());
            ((Stage) rootPane.getScene().getWindow()).close();
        });
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            ((Stage) rootPane.getScene().getWindow()).close();
        });
        setupLayout();
        rootPane.getChildren().addAll(inputText, nameField, imageText, chooseImage, imageBox);
    }

    private void setupLayout() {
        rootPane.setPrefSize(WIDTH, HEIGHT);
        AnchorPane.setLeftAnchor(inputText, 50.0);
        AnchorPane.setTopAnchor(inputText, 50.0);
        inputText.setLayoutX(14);
        inputText.setLayoutY(27);
        nameField.setLayoutX(208);
        nameField.setLayoutY(37);
        imageText.setLayoutX(36);
        imageText.setLayoutY(176);
        chooseImage.setLayoutX(261);
        chooseImage.setLayoutY(158);
        imageBox.setPrefSize(164, 171);
        imageBox.setLayoutX(37);
        imageBox.setLayoutY(206);
        confirm.setLayoutX(296);
        confirm.setLayoutY(436);
        cancel.setLayoutX(391);
        cancel.setLayoutY(436);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    @Override
    public void readObject(Entity userObject) {

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
