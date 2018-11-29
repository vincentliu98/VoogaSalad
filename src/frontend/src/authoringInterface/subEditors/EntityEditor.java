package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the editor for an "Entity" object that is opened when the user clicks on an existing entity or tries to add an entity to the game authoring library.
 *
 * @author Haotian Wang
 */
public class EntityEditor extends AbstractGameObjectEditor<EntityClass, EntityInstance> {
    private Text imageText;
    private Button chooseImage;
    private HBox imagePanel;
    private ObservableList<String> imagePaths;

    public EntityEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        inputText.setText("Your entity name:");
        imageText = new Text("Add an image to your entity");
        chooseImage = new Button("Choose image");
        imagePanel = new HBox(10);
        imagePaths = FXCollections.observableArrayList();
        imagePaths.addListener((ListChangeListener<String>) c -> presentImages());
        chooseImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String imagePath = file.toURI().toString();
                imagePaths.add(imagePath);
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
                        entityClass.getImagePathList().addAll(imagePaths);
                        ImageView icon = new ImageView(imagePaths.get(0));
                        icon.setFitWidth(50);
                        icon.setFitHeight(50);
                        newItem.setGraphic(icon);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        if (nodeEdited instanceof ImageView) {
                            ((ImageView) nodeEdited).setImage(new Image(imagePaths.get(0)));
                        } else if (nodeEdited instanceof Text) {
                            ((Text) nodeEdited).setText(nameField.getText());
                        }
                        // TODO make changes to the GameObjectInstance as well. Waiting for changes at JC's side.
                        break;
                    case EDIT_TREEITEM:
                        gameObjectClass.getImagePathList().clear();
                        gameObjectClass.getImagePathList().addAll(imagePaths);
                        gameObjectManager.changeGameObjectClassName(gameObjectClass.getClassName().getValue(), nameField.getText());
                        if (!imagePaths.isEmpty()) {
                            ImageView icon1 = new ImageView(imagePaths.get(0));
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
        rootPane.getChildren().addAll(imageText, chooseImage, imagePanel);
    }

    /**
     * Present the ImageViews contained in the imagePanel according to the ObservableList of ImagePaths.
     */
    private void presentImages() {
        List<Node> images = new ArrayList<>();
        imagePaths.forEach(path -> {
            ImageView preview = new ImageView(path);
            preview.setFitWidth(50);
            preview.setFitHeight(50);
            images.add(preview);
        });
        imagePanel.getChildren().clear();
        imagePanel.getChildren().addAll(images);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
        // TODO: REmove this disgusting shite
        imagePaths.addAll(gameObjectManager.getEntityClass(gameObjectInstance.getClassName().getValue()).getImagePathList());
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
        imageText.setLayoutX(36);
        imageText.setLayoutY(176);
        chooseImage.setLayoutX(261);
        chooseImage.setLayoutY(158);
        imagePanel.setLayoutX(37);
        imagePanel.setLayoutY(206);
    }
}
