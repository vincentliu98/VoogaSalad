package authoringInterface.subEditors;

import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ErrorWindow;
import utils.exception.NodeNotFoundException;
import utils.exception.PreviewUnavailableException;
import utils.imageManipulation.Coordinates;
import utils.imageManipulation.ImageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the editor for an "Entity" object that is opened when the user clicks on an existing entity or tries to add an entity to the game authoring library.
 *
 * @author Haotian Wang
 */
public class EntityEditor extends AbstractGameObjectEditor<EntityClass, EntityInstance> {
    private Label imageText;
    private Button chooseImage;
    private HBox imagePanel;
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;

    public EntityEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        nameLabel.setText("Your entity name:");
        imageText = new Label("Add an image to your entity");
        chooseImage = new Button("Choose image");
        imagePanel = new HBox(10);
        nameField.setPromptText("Your entity name");
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
            if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
                new ErrorWindow("Empty name", "You must give your entity a non-empty name").showAndWait();
            } else {
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
                    case ADD_TREEITEM:
                        gameObjectManager.createEntityClass(nameField.getText().trim());
                        EntityClass entityClass = gameObjectManager.getEntityClass(nameField.getText().trim());
                        TreeItem<String> newItem = new TreeItem<>(entityClass.getClassName().getValue());
                        entityClass.getImagePathList().addAll(imagePaths);
                        ImageView icon = null;
                        try {
                            icon = new ImageView(ImageManager.getPreview(entityClass));
                        } catch (PreviewUnavailableException e1) {
                            // TODO: proper error handling
                            e1.printStackTrace();
                        }
                        Coordinates.setWidthAndHeight(icon, ICON_WIDTH, ICON_HEIGHT);
                        newItem.setGraphic(icon);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        gameObjectInstance.setInstanceName(nameField.getText());
                        gameObjectInstance.getImagePathList().clear();
                        gameObjectInstance.getImagePathList().addAll(imagePaths);
                        try {
                            ((ImageView) nodeEdited).setImage(ImageManager.getPreview(gameObjectInstance));
                        } catch (PreviewUnavailableException e1) {
                            // TODO: proper error handling
                            e1.printStackTrace();
                        }
                        break;
                    case EDIT_TREEITEM:
                        gameObjectClass.getImagePathList().clear();
                        gameObjectClass.getImagePathList().addAll(imagePaths);
                        gameObjectManager.changeGameObjectClassName(gameObjectClass.getClassName().getValue(), nameField.getText());
                        ImageView icon2 = null;
                        try {
                            icon2 = new ImageView(ImageManager.getPreview(gameObjectClass));
                        } catch (PreviewUnavailableException e1) {
                            // TODO: proper error handling
                            e1.printStackTrace();
                        }
                        Coordinates.setWidthAndHeight(icon2, ICON_WIDTH, ICON_HEIGHT);
                        treeItem.setValue(nameField.getText());
                        treeItem.setGraphic(icon2);
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
        nameField.setText(gameObjectInstance.getInstanceName().getValue());
        imagePaths.addAll(gameObjectInstance.getImagePathList());
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        imagePaths.addAll(gameObjectClass.getImagePathList());
    }

    private void setupLayout() {
        imageText.setLayoutX(36);
        imageText.setLayoutY(176);
        chooseImage.setLayoutX(261);
        chooseImage.setLayoutY(176);
        imagePanel.setLayoutX(36);
        imagePanel.setLayoutY(206);
    }
}
