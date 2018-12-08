package authoringInterface.subEditors;

import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectInstanceNotFoundException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ErrorWindow;
import utils.exception.PreviewUnavailableException;
import utils.imageManipulation.JavaFxOperation;
import utils.imageManipulation.ImageManager;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Editor to change the Tile settings. Need to work on it. Low priority
 *
 * @author Haotian Wang, jl729
 */

public class TileEditor extends AbstractGameObjectEditor<TileClass, TileInstance> {
    private TextField widthText = new TextField();
    private TextField heightText = new TextField();
    private GridPane geometry = new GridPane();
    private HBox imagePanel = new HBox();
    private Label imageLabel = new Label("Add an image to your tile class");
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;
    private Button chooseButton = new Button("Choose image");
    private ObservableList<String> imagePaths;
    private Set<String> toRemovePath;
    private Set<ImageView> toRemoveImageView;
    private static final double REMOVE_OPACITY = 0.5;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;

    TileEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        toRemovePath = new HashSet<>();
        toRemoveImageView = new HashSet<>();
        Label widthLabel = new Label("Width");
        Label heightLabel = new Label("Height");
        imagePaths = FXCollections.observableArrayList();
        nameLabel.setText("Your tile name");
        widthText.setPromptText("Width");
        widthText.setText(String.valueOf(DEFAULT_WIDTH));
        heightText.setPromptText("Height");
        heightText.setText(String.valueOf(DEFAULT_HEIGHT));
        nameField.setPromptText("Tile name");
        geometry.setHgap(20);
        geometry.addRow(0, widthLabel, widthText);
        geometry.addRow(1, heightLabel, heightText);
        chooseButton.setStyle("-fx-text-fill: white;"
                            + "-fx-background-color: #343a40;");
        chooseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                String imagePath = file.toURI().toString();
                imagePaths.add(imagePath);
            }
        });

        imagePaths.addListener((ListChangeListener<String>) change -> {
            imagePaths.forEach(string -> {
                ImageView preview = new ImageView(string);
                preview.setFitHeight(ICON_HEIGHT);
                preview.setFitWidth(ICON_WIDTH);
                imagePanel.getChildren().add(preview);
                preview.setOnMouseClicked(e -> {
                    if (!toRemoveImageView.remove(preview)) {
                        toRemoveImageView.add(preview);
                        toRemovePath.add(string);
                        preview.setOpacity(REMOVE_OPACITY);
                    } else {
                        toRemoveImageView.remove(preview);
                        toRemovePath.remove(string);
                        preview.setOpacity(1);
                    }
                });
            });
        });

        rootPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                imagePaths.removeAll(toRemovePath);
            }
        });

        confirm.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                new ErrorWindow("Empty name", "You must give your tile a non-empty name").showAndWait();
            } else if (widthText.getText().trim().isEmpty()) {
                new ErrorWindow("Empty width", "You must specify a width for this tile").showAndWait();
            } else if (heightText.getText().trim().isEmpty()) {
                new ErrorWindow("Empty height", "You must specify a height for this tile").showAndWait();
            } else {
                int width = DEFAULT_WIDTH;
                int height = DEFAULT_HEIGHT;
                try {
                    width = Integer.parseInt(widthText.getText());
                } catch (NumberFormatException e1) {
                    new ErrorWindow("Incorrect width", "The input width is in an unsupported format").showAndWait();
                    return;
                }
                try {
                    height = Integer.parseInt(heightText.getText());
                } catch (NumberFormatException e1) {
                    new ErrorWindow("Incorrect height", "The input height is in an unsupported format").showAndWait();
                    return;
                }
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
                    case ADD_TREEITEM:
                        try {
                            gameObjectManager.createTileClass(nameField.getText().trim());
                        } catch (DuplicateGameObjectClassException e1) {
                            // TODO
                            e1.printStackTrace();
                        }
                        TileClass tileClass = null;
                        try {
                            tileClass = gameObjectManager.getTileClass(nameField.getText().trim());
                        } catch (GameObjectClassNotFoundException e1) {
                            // TODO
                            e1.printStackTrace();
                        }
                        TreeItem<String> newItem = new TreeItem<>(tileClass.getClassName().getValue());
                        tileClass.getImagePathList().addAll(imagePaths);
                        ImageView icon = null;
                        try {
                            icon = new ImageView(ImageManager.getPreview(tileClass));
                        } catch (PreviewUnavailableException e1) {
                            // TODO: proper error handling
                            e1.printStackTrace();
                        }
                        JavaFxOperation.setWidthAndHeight(icon, ICON_WIDTH, ICON_HEIGHT);
                        newItem.setGraphic(icon);
                        tileClass.setHeight(height);
                        tileClass.setWidth(width);
                        treeItem.getChildren().add(newItem);
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        try { ImageManager.removeInstanceImage(gameObjectInstance); } catch (GameObjectInstanceNotFoundException e1) {}
                        gameObjectInstance.setInstanceName(nameField.getText());
                        gameObjectInstance.getImagePathList().clear();
                        gameObjectInstance.getImagePathList().addAll(imagePaths);
                        gameObjectInstance.setWidth(width);
                        gameObjectInstance.setHeight(height);
                        try {
                            ((ImageView) nodeEdited).setImage(ImageManager.getPreview(gameObjectInstance));
                        } catch (PreviewUnavailableException e1) {
                            // TODO: proper error handling
                            e1.printStackTrace();
                        }
                        Tooltip.install(nodeEdited, new Tooltip(String.format("Width: %s\nHeight: %s\nSingle Click to toggle Deletion\nDouble Click or Right Click to edit\nInstance ID: %s\nClass Name: %s", width, height, gameObjectInstance.getInstanceId().getValue(), gameObjectInstance.getClassName().getValue())));
                        break;
                    case EDIT_TREEITEM:
                        try { ImageManager.removeClassImage(gameObjectClass); } catch (GameObjectClassNotFoundException e1) {}
                        gameObjectClass.getImagePathList().clear();
                        gameObjectClass.getImagePathList().addAll(imagePaths);
                        gameObjectClass.setWidth(width);
                        gameObjectClass.setHeight(height);
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
                        JavaFxOperation.setWidthAndHeight(icon2, ICON_WIDTH, ICON_HEIGHT);
                        treeItem.setValue(nameField.getText());
                        treeItem.setGraphic(icon2);
                        break;
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
        imagePaths.addAll(gameObjectInstance.getImagePathList());
        widthText.setText(String.valueOf(gameObjectInstance.getWidth().getValue()));
        heightText.setText(String.valueOf(gameObjectInstance.getHeight().getValue()));
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        imagePaths.addAll(gameObjectClass.getImagePathList());
        widthText.setText(String.valueOf(gameObjectClass.getWidth().getValue()));
        heightText.setText(String.valueOf(gameObjectClass.getHeight().getValue()));
    }

    private void setupLayout() {
        layout.addRow(0, geometry);
        layout.addRow(1, imageLabel, chooseButton);
        layout.addRow(2, imagePanel);
    }
}
