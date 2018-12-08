package authoringInterface.subEditors;

import authoringInterface.subEditors.exception.IllegalGameObjectNamingException;
import authoringInterface.subEditors.exception.IllegalGeometryException;
import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectInstanceNotFoundException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import grids.PointImpl;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.exception.GridIndexOutOfBoundsException;
import utils.exception.PreviewUnavailableException;
import utils.exception.UnremovableNodeException;
import utils.imageManipulation.ImageManager;
import utils.imageManipulation.JavaFxOperation;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Editor to change the Tile settings. Need to work on it. Low priority
 *
 * @author Haotian Wang, jl729
 */

@SuppressWarnings("Duplicates")
public class TileEditor extends AbstractGameObjectEditor<TileClass, TileInstance> {
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;
    private static final double REMOVE_OPACITY = 0.5;
    private static final double FULL_OPACITY = 1;
    private static final int DEFAULT_WIDTH = 1;
    private static final int DEFAULT_HEIGHT = 1;
    private static final double IMAGE_PANEL_GAP = 10;
    private TextField widthText = new TextField();
    private TextField heightText = new TextField();
    private GridPane geometry = new GridPane();
    private HBox imagePanel = new HBox(IMAGE_PANEL_GAP);
    private Label imageLabel = new Label("Add an image to your tile class");
    private Button chooseButton = new Button("Choose image");
    private ObservableList<String> imagePaths;
    private Set<String> toRemovePath;
    private Set<ImageView> toRemoveImageView;
    private TextField xInput;
    private TextField yInput;

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
            imagePanel.getChildren().clear();
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
                        preview.setOpacity(FULL_OPACITY);
                    }
                });
            });
        });

        rootPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
                imagePaths.removeAll(toRemovePath);
            }
        });
        setupLayout();
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {
        readCommonTileCharacteristic(gameObjectInstance.getClassName(), gameObjectInstance.getImagePathList(), gameObjectInstance.getWidth(), gameObjectInstance.getHeight());
        Label xLabel = new Label("x");
        Label yLabel = new Label("y");
        xInput = new TextField(String.valueOf(gameObjectInstance.getCoord().getX()));
        yInput = new TextField(String.valueOf(gameObjectInstance.getCoord().getY()));
        GridPane position = new GridPane();
        position.addRow(0, xLabel, xInput);
        position.addRow(1, yLabel, yInput);
        position.setHgap(20);
        layout.addRow(5, position);
        readInstanceProperties();
    }

    private void readCommonTileCharacteristic(ReadOnlyStringProperty className, ObservableList<String> imagePathList, SimpleIntegerProperty width, SimpleIntegerProperty height) {
        nameField.setText(className.getValue());
        imagePaths.addAll(imagePathList);
        widthText.setText(String.valueOf(width.getValue()));
        heightText.setText(String.valueOf(height.getValue()));
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        readCommonTileCharacteristic(gameObjectClass.getClassName(), gameObjectClass.getImagePathList(), gameObjectClass.getWidth(), gameObjectClass.getHeight());
        readClassProperties();
    }

    /**
     * This method sets up the confirm logic of adding new TreeItem.
     *
     * @throws IllegalGeometryException
     * @throws PreviewUnavailableException
     * @throws GameObjectClassNotFoundException
     * @throws IllegalGameObjectNamingException
     */
    @Override
    protected void confirmAddTreeItem() throws IllegalGeometryException, PreviewUnavailableException, GameObjectClassNotFoundException, IllegalGameObjectNamingException {
        setClassName();
        int width = outputPositiveInteger(widthText);
        int height = outputPositiveInteger(heightText);
        TileClass tileClass = gameObjectManager.getTileClass(nameField.getText().trim());
        TreeItem<String> newItem = new TreeItem<>(tileClass.getClassName().getValue());
        tileClass.getImagePathList().addAll(imagePaths);
        ImageView icon = new ImageView(ImageManager.getPreview(tileClass));
        JavaFxOperation.setWidthAndHeight(icon, ICON_WIDTH, ICON_HEIGHT);
        newItem.setGraphic(icon);
        tileClass.setHeight(height);
        tileClass.setWidth(width);
        treeItem.getChildren().add(newItem);
        writeClassProperties();
    }

    /**
     * This method sets up the confirm logic of editing existing TreeItem.
     *
     * @throws IllegalGeometryException
     * @throws InvalidOperationException
     * @throws PreviewUnavailableException
     * @throws GameObjectClassNotFoundException
     * @throws IllegalGameObjectNamingException
     */
    @Override
    protected void confirmEditTreeItem() throws IllegalGeometryException, InvalidOperationException, PreviewUnavailableException, GameObjectClassNotFoundException, IllegalGameObjectNamingException {
        setClassName();
        int width = outputPositiveInteger(widthText);
        int height = outputPositiveInteger(heightText);
        ImageManager.removeClassImage(gameObjectClass);
        gameObjectClass.getImagePathList().clear();
        gameObjectClass.getImagePathList().addAll(imagePaths);
        gameObjectClass.setWidth(width);
        gameObjectClass.setHeight(height);
        gameObjectManager.changeGameObjectClassName(gameObjectClass.getClassName().getValue(), nameField.getText());
        ImageView icon2 = new ImageView(ImageManager.getPreview(gameObjectClass));
        JavaFxOperation.setWidthAndHeight(icon2, ICON_WIDTH, ICON_HEIGHT);
        treeItem.setValue(nameField.getText());
        treeItem.setGraphic(icon2);
        writeClassProperties();
    }

    /**
     * This method sets up the confirm logic of editing existing Node.
     *
     * @throws IllegalGeometryException
     * @throws UnremovableNodeException
     * @throws GridIndexOutOfBoundsException
     * @throws PreviewUnavailableException
     * @throws IllegalGameObjectNamingException
     */
    @Override
    protected void confirmEditNode() throws IllegalGeometryException, UnremovableNodeException, GridIndexOutOfBoundsException, PreviewUnavailableException, IllegalGameObjectNamingException {
        setInstanceName();
        int width = outputPositiveInteger(widthText);
        int height = outputPositiveInteger(heightText);
        try {
            ImageManager.removeInstanceImage(gameObjectInstance);
        } catch (GameObjectInstanceNotFoundException ignored) {}
        gameObjectInstance.getImagePathList().clear();
        gameObjectInstance.getImagePathList().addAll(imagePaths);
        gameObjectInstance.setWidth(width);
        gameObjectInstance.setHeight(height);
        ((ImageView) nodeEdited).setImage(ImageManager.getPreview(gameObjectInstance));
        Tooltip.install(nodeEdited, new Tooltip(String.format("Width: %s\nHeight: %s\nSingle Click to toggle Deletion\nDouble Click or Right Click to edit\nInstance ID: %s\nClass Name: %s", width, height, gameObjectInstance.getInstanceId().getValue(), gameObjectInstance.getClassName().getValue())));
        int row = outputPositiveInteger(yInput);
        int col = outputPositiveInteger(xInput);
        StackPane target = JavaFxOperation.getNodeFromGridPaneByIndices(((GridPane) JavaFxOperation.getGrandParent(nodeEdited)), row, col);
        JavaFxOperation.removeFromParent(nodeEdited);
        assert target != null;
        target.getChildren().add(nodeEdited);
        gameObjectInstance.setCoord(new PointImpl(col, row));
        writeInstanceProperties();
    }

    private void setupLayout() {
        layout.addRow(0, geometry);
        layout.addRow(1, imageLabel, chooseButton);
        layout.addRow(2, imagePanel);
        layout.addRow(3, propLabel, addProperties);
        layout.addRow(4, listProp);
    }
}
