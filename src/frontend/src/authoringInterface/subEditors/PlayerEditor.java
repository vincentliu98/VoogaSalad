package authoringInterface.subEditors;

import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ErrorWindow;

import java.io.File;
import java.util.Set;

public class PlayerEditor extends AbstractGameObjectEditor<PlayerClass, PlayerInstance> {
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;
    private static final double REMOVE_OPACITY = 0.5;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_WIDTH = 1;
    private Label imageText;
    private Button chooseImage;
    private HBox imagePanel;
    private ObservableList<String> imagePaths;
    private Set<ImageView> toRemove;
    private Set<String> toRemovePath;

    PlayerEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        nameLabel.setText("Your Player Name");
        nameField.setPromptText("Player 0");

        imageText = new Label("Add an image to your entity");
        chooseImage = new Button("Choose image");
        chooseImage.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
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

        confirm.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                new ErrorWindow("Empty name", "You must give your player a non-empty name").showAndWait();
            } else {
                switch (editingMode) {
                    case ADD_TREEITEM:
                        try {
                            gameObjectManager.createPlayerClass(nameField.getText().trim());
                        } catch (DuplicateGameObjectClassException e1) {
                            // TODO
                            e1.printStackTrace();
                        }
                        //TODO: if backend ready, use below
                        PlayerClass playerClass = null;
//                        try {
//                            playerClass = gameObjectManager.getPlayerClass(nameField.getText().trim());
//                        } catch (GameObjectClassNotFoundException e1) {
//                            // TODO
//                            e1.printStackTrace();
//                        }
//                        assert PlayerClass != null;
//                        TreeItem<String> newItem = new TreeItem<>(PlayerClass.getClassName().getValue());
                }
            }
        });
        layout.addRow(0, imageText, chooseImage);
    }

    private void presentImages() {
        imagePanel.getChildren().clear();
        imagePaths.forEach(path -> {
            ImageView preview = new ImageView(path);
            preview.setFitWidth(ICON_WIDTH);
            preview.setFitHeight(ICON_HEIGHT);
            imagePanel.getChildren().add(preview);
            preview.setOnMouseClicked(e -> {
                if (!toRemove.remove(preview)) {
                    toRemove.add(preview);
                    toRemovePath.add(path);
                    preview.setOpacity(REMOVE_OPACITY);
                } else {
                    toRemovePath.remove(path);
                    preview.setOpacity(1);
                }
            });
        });
    }

    @Override
    protected void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getClassName().getValue());
//        imagePaths.addAll(gameObjectInstance.getImagePathList());
    }

    @Override
    protected void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
//        imagePaths.addAll(gameObjectClass.getImagePath());
    }

    /**
     * This method sets up the confirm logic of adding new TreeItem.
     */
    @Override
    protected void confirmAddTreeItem() {

    }

    /**
     * This method sets up the confirm logic of editing existing TreeItem.
     */
    @Override
    protected void confirmEditTreeItem() {

    }

    /**
     * This method sets up the confirm logic of editing existing Node.
     */
    @Override
    protected void confirmEditNode() {

    }
}
