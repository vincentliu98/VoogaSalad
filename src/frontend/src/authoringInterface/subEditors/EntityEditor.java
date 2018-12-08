package authoringInterface.subEditors;

import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectInstanceNotFoundException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import javafx.collections.*;
import grids.PointImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.ErrorWindow;
import utils.exception.GridIndexOutOfBoundsException;
import utils.exception.PreviewUnavailableException;
import utils.exception.UnremovableNodeException;
import utils.imageManipulation.ImageManager;
import utils.imageManipulation.JavaFxOperation;

import java.io.File;
import java.util.HashSet;
import java.util.Set;


/**
 * This is the editor for an "Entity" object that is opened when the user clicks on an existing entity or tries to add an entity to the game authoring library.
 *
 * @author Haotian Wang
 * @author Amy
 */
public class EntityEditor extends AbstractGameObjectEditor<EntityClass, EntityInstance> {
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;
    private static final double REMOVE_OPACITY = 0.5;
    private static final int DEFAULT_HEIGHT = 1;
    private static final int DEFAULT_WIDTH = 1;
    private Label imageText;
    private Button chooseImage;
    private HBox imagePanel;
    private TextField widthInput;
    private TextField heightInput;
    private Label propLabel = new Label("Properties");
    private Button addProperties = new Button("Add");
    private GridPane listProp;
    private FlowPane listview;
    private ObservableSet<PropertyBox> propBoxes;
    private ObservableList<String> imagePaths;
    private Set<ImageView> toRemove;
    private Set<String> toRemovePath;
    private GridPane size;
    private TextField rowInput;
    private TextField colInput;

    EntityEditor(GameObjectsCRUDInterface manager) {
        super(manager);
        toRemove = new HashSet<>();
        toRemovePath = new HashSet<>();
        nameLabel.setText("Your entity name:");

        size = new GridPane();
        size.setHgap(20);
        Label widthLabel = new Label("Width of entity");
        Label heightLabel = new Label("Height of entity");
        widthInput = new TextField(String.valueOf(DEFAULT_WIDTH));
        widthInput.setPromptText("width of entity");
        heightInput = new TextField(String.valueOf(DEFAULT_HEIGHT));
        heightInput.setPromptText("height of entity");
        size.addRow(0, widthLabel, widthInput);
        size.addRow(1, heightLabel, heightInput);

        imageText = new Label("Add an image to your entity");
        chooseImage = new Button("Choose image");
        chooseImage.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        imagePanel = new HBox(10);
        listProp = new GridPane();
        listview = new FlowPane();
        listview.setVgap(10);
        listview.setHgap(10);
        propBoxes = FXCollections.observableSet();
        propBoxes.addListener((SetChangeListener<? super PropertyBox>) e -> {
            if(e.wasAdded()) listview.getChildren().add(e.getElementAdded());
            else listview.getChildren().remove(e.getElementRemoved());
        });

        listProp.getChildren().add(listview);
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

        addProperties.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        addProperties.setOnAction(e -> {
            new PropertyInputDialog().showAndWait().ifPresent(prop -> {
                boolean added = false;
                for(var box : propBoxes) {
                    if(box.getKey().equals(prop.getKey())) {
                        box.setValue(prop.getValue());
                        added = true;
                    }
                }
                if(!added) propBoxes.add(new PropertyBox(prop.getKey(), prop.getValue(), propBoxes::remove));
            });
        });
        setupLayout();
        rootPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
                toRemovePath.forEach(path -> imagePaths.remove(path));
            }
        });
    }

    /**
     * This method sets up the confirm logic for adding new TreeItems.
     */
    @Override
    protected void confirmAddTreeItem() {
        int width;
        try {
            width = outputPositiveInteger(widthInput);
        } catch (IllegalGeometryException e) {
            new ErrorWindow("Illegal Width", e.toString()).showAndWait();
            return;
        }
        int height;
        try {
            height = outputPositiveInteger(heightInput);
        } catch (IllegalGeometryException e) {
            new ErrorWindow("Illegal Height", e.toString()).showAndWait();
            return;
        }
        try {
            gameObjectManager.createEntityClass(nameField.getText().trim());
        } catch (DuplicateGameObjectClassException e1) {
            // TODO
            e1.printStackTrace();
        }
        EntityClass entityClass = null;
        try {
            entityClass = gameObjectManager.getEntityClass(nameField.getText().trim());
        } catch (GameObjectClassNotFoundException e1) {
            // TODO
            e1.printStackTrace();
        }
        assert entityClass != null;
        TreeItem<String> newItem = new TreeItem<>(entityClass.getClassName().getValue());
        entityClass.getImagePathList().addAll(imagePaths);
        for(var box : propBoxes) {
            entityClass.getPropertiesMap().put(box.getKey(), box.getValue());
        }
        entityClass.setHeight(height);
        entityClass.setWidth(width);
        ImageView icon = null;
        try {
            icon = new ImageView(ImageManager.getPreview(entityClass));
        } catch (PreviewUnavailableException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
        assert icon != null;
        JavaFxOperation.setWidthAndHeight(icon, ICON_WIDTH, ICON_HEIGHT);
        newItem.setGraphic(icon);
        treeItem.getChildren().add(newItem);
    }

    /**
     * This method sets up the confirm logic of editing existing TreeItem.
     */
    @Override
    protected void confirmEditTreeItem() {
        int width;
        try {
            width = outputPositiveInteger(widthInput);
        } catch (IllegalGeometryException e) {
            new ErrorWindow("Illegal Width", e.toString()).showAndWait();
            return;
        }
        int height;
        try {
            height = outputPositiveInteger(heightInput);
        } catch (IllegalGeometryException e) {
            new ErrorWindow("Illegal Height", e.toString()).showAndWait();
            return;
        }
        try {
            ImageManager.removeClassImage(gameObjectClass);
        } catch (GameObjectClassNotFoundException ignored) {}
        gameObjectClass.getImagePathList().clear();
        gameObjectClass.getImagePathList().addAll(imagePaths);
        gameObjectClass.getPropertiesMap().clear();
        for(var box : propBoxes) {
            gameObjectClass.getPropertiesMap().put(box.getKey(), box.getValue());
        }
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
        int width;
        try {
            width = outputPositiveInteger(widthInput);
        } catch (IllegalGeometryException e) {
            new ErrorWindow("Illegal Width", e.toString()).showAndWait();
            return;
        }
        int height;
        try {
            height = outputPositiveInteger(heightInput);
        } catch (IllegalGeometryException e) {
            new ErrorWindow("Illegal Height", e.toString()).showAndWait();
            return;
        }
        try { ImageManager.removeInstanceImage(gameObjectInstance); } catch (GameObjectInstanceNotFoundException ignored) {}
        gameObjectInstance.setInstanceName(nameField.getText());
        gameObjectInstance.getImagePathList().clear();
        gameObjectInstance.getImagePathList().addAll(imagePaths);
        gameObjectInstance.getPropertiesMap().clear();
        for(var box : propBoxes) {
            gameObjectInstance.getPropertiesMap().put(box.getKey(), box.getValue());
        }
        gameObjectInstance.setWidth(width);
        gameObjectInstance.setHeight(height);
        try {
            ((ImageView) nodeEdited).setImage(ImageManager.getPreview(gameObjectInstance));
        } catch (PreviewUnavailableException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
        Tooltip.install(nodeEdited, new Tooltip(String.format("Width: %s\nHeight: %s\nSingle Click to toggle Deletion\nDouble Click or Right Click to edit\nInstance ID: %s\nClass Name: %s", width, height, gameObjectInstance.getInstanceId().getValue(), gameObjectInstance.getClassName().getValue())));
        StackPane target;
        int row = Integer.parseInt(rowInput.getText());
        int col = Integer.parseInt(colInput.getText());
        try {
            target = JavaFxOperation.getNodeFromGridPaneByIndices(((GridPane) JavaFxOperation.getGrandParent(nodeEdited)), row, col);
        } catch (GridIndexOutOfBoundsException e1) {
            new ErrorWindow("GridIndexOutOfBounds error", e1.toString()).showAndWait();
            return;
        }
        try {
            JavaFxOperation.removeFromParent(nodeEdited);
        } catch (UnremovableNodeException e1) {
            // TODO: proper error handling
            e1.printStackTrace();
        }
        assert target != null;
        target.getChildren().add(nodeEdited);
        gameObjectInstance.setCoord(new PointImpl(col, row));
    }

    /**
     * Present the ImageViews contained in the imagePanel according to the ObservableList of ImagePaths.
     */
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

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    @Override
    public void readGameObjectInstance() {
        nameField.setText(gameObjectInstance.getInstanceName().getValue());
        imagePaths.addAll(gameObjectInstance.getImagePathList());
        gameObjectInstance.getPropertiesMap().forEach((k, v) -> propBoxes.add(new PropertyBox(k, v, propBoxes::remove)));
        widthInput.setText(String.valueOf(gameObjectInstance.getWidth().getValue()));
        heightInput.setText(String.valueOf(gameObjectInstance.getHeight().getValue()));
        Label xLabel = new Label("x, col index");
        Label yLabel = new Label("y, row index");
        colInput = new TextField(String.valueOf(gameObjectInstance.getCoord().getX()));
        rowInput = new TextField(String.valueOf(gameObjectInstance.getCoord().getY()));
        GridPane position = new GridPane();
        position.addRow(0, xLabel, colInput);
        position.addRow(1, yLabel, rowInput);
        position.setHgap(20);
        layout.addRow(5, position);
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        imagePaths.addAll(gameObjectClass.getImagePathList());
        gameObjectClass.getPropertiesMap().forEach((k, v) -> propBoxes.add(new PropertyBox(k, v, propBoxes::remove)));
        widthInput.setText(String.valueOf(gameObjectClass.getWidth().getValue()));
        heightInput.setText(String.valueOf(gameObjectClass.getHeight().getValue()));
    }

    private void setupLayout() {
        layout.addRow(0, size);
        layout.addRow(1, imageText, chooseImage);
        layout.addRow(2, imagePanel);
        layout.addRow(3, propLabel, addProperties);
        layout.addRow(4, listProp);
    }
}