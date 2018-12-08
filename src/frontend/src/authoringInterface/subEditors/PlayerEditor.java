package authoringInterface.subEditors;

import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectInstanceNotFoundException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.exception.PreviewUnavailableException;
import utils.imageManipulation.ImageManager;
import utils.imageManipulation.JavaFxOperation;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class PlayerEditor extends AbstractGameObjectEditor<PlayerClass, PlayerInstance> {
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;
    private static final double REMOVE_OPACITY = 0.5;
    private static final double IMAGE_PANEL_GAP = 10;
    private Label imageText;
    private Button chooseImage;
    private HBox imagePanel;
    private Set<ImageView> toRemove;
    private Set<String> toRemovePath;
    private String imagePath;

    PlayerEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        toRemove = new HashSet<>();
        toRemovePath = new HashSet<>();
        nameLabel.setText("Your Player Name");
        nameField.setPromptText("Player 0");

        imageText = new Label("Add an image to your entity");
        chooseImage = new Button("Choose image");
        chooseImage.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        imagePanel = new HBox(10);
        chooseImage.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String imagefilePath = file.toURI().toString();
                imagePath = imagefilePath;
                presentImages();
            }
        });
        imagePanel = new HBox(IMAGE_PANEL_GAP);

        confirm.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        layout.addRow(0, imageText, chooseImage);
        layout.addRow(1, imagePanel);
    }

    private void presentImages() {
        imagePanel.getChildren().clear();
        ImageView preview = new ImageView(imagePath);
        preview.setFitWidth(ICON_WIDTH);
        preview.setFitHeight(ICON_HEIGHT);
        imagePanel.getChildren().add(preview);
        preview.setOnMouseClicked(e -> {
            if (!toRemove.remove(preview)) {
                toRemove.add(preview);
                toRemovePath.add(imagePath);
                preview.setOpacity(REMOVE_OPACITY);
            } else {
                toRemovePath.remove(imagePath);
                preview.setOpacity(1);
            }
        });
    }

    @Override
    protected void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
        imagePath = gameObjectInstance.getImagePath().getName();
    }

    @Override
    protected void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        imagePath = gameObjectClass.getImagePath().getName();
    }

    /**
     * This method sets up the confirm logic of adding new TreeItem.
     */
    @Override
    protected void confirmAddTreeItem() {
        try {
            gameObjectManager.createPlayerClass(nameField.getText().trim());
        } catch (DuplicateGameObjectClassException e1) {
            // TODO
            e1.printStackTrace();
        }
        PlayerClass playerClass = null;
        try {
            playerClass = gameObjectManager.getPlayerClass((nameField.getText().trim()));
        } catch (GameObjectClassNotFoundException e1) {
            // TODO
            e1.printStackTrace();
        }
        assert playerClass != null;
        TreeItem<String> newItem = new TreeItem<>(playerClass.getClassName().getValue());
        playerClass.setImagePath(imagePath);

        ImageView icon = null;
        try {
            icon = new ImageView(ImageManager.getPreview(playerClass));
        } catch (PreviewUnavailableException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
        assert icon != null;

        newItem.setGraphic(icon);
        treeItem.getChildren().add(newItem);
    }

    /**
     * This method sets up the confirm logic of editing existing TreeItem.
     */
    @Override
    protected void confirmEditTreeItem() {
        try {
            ImageManager.removeClassImage(gameObjectClass);
        } catch (GameObjectClassNotFoundException ignored) {}
        gameObjectClass.setImagePath(imagePath);

        try {
            gameObjectManager.changeGameObjectClassName(gameObjectClass.getClassName().getValue(), nameField.getText());
        } catch (InvalidOperationException e1) {
            // TODO
            e1.printStackTrace();
        }
        ImageView icon2 = null;
        try {
            icon2 = new ImageView(ImageManager.getPreview(gameObjectClass));
        } catch (PreviewUnavailableException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
        assert icon2 != null;
        JavaFxOperation.setWidthAndHeight(icon2, ICON_WIDTH, ICON_HEIGHT);
        treeItem.setValue(nameField.getText());
        treeItem.setGraphic(icon2);
    }

    /**
     * This method sets up the confirm logic of editing existing Node.
     */
    @Override
    protected void confirmEditNode() {
        try { ImageManager.removeInstanceImage(gameObjectInstance); } catch (GameObjectInstanceNotFoundException ignored) {}
        gameObjectInstance.setInstanceName(nameField.getText());
        gameObjectInstance.setImagePath(imagePath);
        try {
            ((ImageView) nodeEdited).setImage(ImageManager.getPreview(gameObjectInstance));
        } catch (PreviewUnavailableException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
    }
}
