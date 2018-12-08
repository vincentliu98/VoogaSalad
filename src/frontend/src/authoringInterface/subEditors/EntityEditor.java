package authoringInterface.subEditors;

import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectInstanceNotFoundException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import utils.ErrorWindow;
import utils.exception.PreviewUnavailableException;
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
    private Dialog<Pair<String, String>> dialog = new Dialog<>();
    private GridPane grid = new GridPane();
    private GridPane listProp;
    private FlowPane listview;
    private ObservableMap<String, String> list;
    private TextField name;
    private TextField value;
    private Button delete;
    private ComboBox<String> comboBox;
    private Label playerLabel;
    private ObservableList<String> imagePaths;
    private Set<ImageView> toRemove;
    private Set<String> toRemovePath;
    private GridPane size;
    private GridPane position;

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


        comboBox = new ComboBox<>();
        comboBox.setPromptText("NONE");
        playerLabel = new Label("Choose Player");
        imageText = new Label("Add an image to your entity");
        chooseImage = new Button("Choose image");
        chooseImage.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        imagePanel = new HBox(10);
        listProp = new GridPane();
        listview = new FlowPane();
        list = FXCollections.observableHashMap();
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
        setupProp();
        addProperties.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        addProperties.setOnAction(e -> {
            dialog.showAndWait().ifPresent(prop -> manageList(prop.getKey(), prop.getValue()));
        });

        confirm.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        confirm.setOnAction(e -> {
            if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
                new ErrorWindow("Empty name", "You must give your entity a non-empty name").showAndWait();
            } else if (widthInput.getText().trim().isEmpty()) {
                new ErrorWindow("Empty width", "You must specify a width for this entity").showAndWait();
            } else if (heightInput.getText().trim().isEmpty()) {
                new ErrorWindow("Empty height", "You must specify a height for this entity").showAndWait();
            } else {
                int width = DEFAULT_WIDTH;
                int height = DEFAULT_HEIGHT;
                try {
                    width = Integer.parseInt(widthInput.getText());
                } catch (NumberFormatException e1) {
                    new ErrorWindow("Incorrect width", "The input width is in an unsupported format").showAndWait();
                    return;
                }
                try {
                    height = Integer.parseInt(heightInput.getText());
                } catch (NumberFormatException e1) {
                    new ErrorWindow("Incorrect height", "The input height is in an unsupported format").showAndWait();
                    return;
                }
                ((Stage) rootPane.getScene().getWindow()).close();
                switch (editingMode) {
                    case ADD_TREEITEM:
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
                        entityClass.getPropertiesMap().putAll(list);
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
                        break;
                    case NONE:
                        return;
                    case EDIT_NODE:
                        try {
                            ImageManager.removeInstanceImage(gameObjectInstance);
                        } catch (GameObjectInstanceNotFoundException ignored) {
                        }
                        gameObjectInstance.setInstanceName(nameField.getText());
                        gameObjectInstance.getImagePathList().clear();
                        gameObjectInstance.getImagePathList().addAll(imagePaths);
                        gameObjectInstance.getPropertiesMap().clear();
                        gameObjectInstance.getPropertiesMap().putAll(list);
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
                        try {
                            ImageManager.removeClassImage(gameObjectClass);
                        } catch (GameObjectClassNotFoundException ignored) {
                        }
                        gameObjectClass.getImagePathList().clear();
                        gameObjectClass.getImagePathList().addAll(imagePaths);
                        gameObjectClass.getPropertiesMap().clear();
                        gameObjectClass.getPropertiesMap().putAll(list);
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
                        break;
                }
            }
        });
        setupLayout();
        rootPane.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
                toRemovePath.forEach(path -> imagePaths.remove(path));
            }
        });
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
        list.putAll(gameObjectInstance.getPropertiesMap());
        widthInput.setText(String.valueOf(gameObjectInstance.getWidth().getValue()));
        heightInput.setText(String.valueOf(gameObjectInstance.getHeight().getValue()));
        Label xLabel = new Label("x");
        Label yLabel = new Label("y");
        TextField xInput = new TextField(String.valueOf(gameObjectInstance.getCoord().getX()));
        TextField yInput = new TextField(String.valueOf(gameObjectInstance.getCoord().getY()));
        position = new GridPane();
        position.addRow(0, xLabel, xInput);
        position.addRow(1, yLabel, yInput);
        position.setHgap(20);
        layout.addRow(6, position);
    }

    /**
     * Read the GameObjectClass represented by this editor.
     */
    @Override
    public void readGameObjectClass() {
        nameField.setText(gameObjectClass.getClassName().getValue());
        imagePaths.addAll(gameObjectClass.getImagePathList());
        list.putAll(gameObjectClass.getPropertiesMap());
        widthInput.setText(String.valueOf(gameObjectClass.getWidth().getValue()));
        heightInput.setText(String.valueOf(gameObjectClass.getHeight().getValue()));
    }

    private void setupLayout() {
        layout.addRow(0, size);
        layout.addRow(1, playerLabel, comboBox);
        layout.addRow(2, imageText, chooseImage);
        layout.addRow(3, imagePanel);
        layout.addRow(4, propLabel, addProperties);
        layout.addRow(5, listProp);
    }

    private void setupProp() {
        dialog.setTitle("Property of Entity");
        dialog.setHeaderText("This action will add new property of the entity.");
        ButtonType propConfirm = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(propConfirm, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(setPropkeys());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == propConfirm) {
                return new Pair<>(name.getText(), value.getText());
            }
            return null;
        });
    }

    private GridPane setPropkeys() {
        grid.setHgap(10);
        grid.setVgap(5);
        grid.setPadding(new Insets(20, 150, 10, 10));
        name = new TextField();
        value = new TextField();
        name.setPromptText("i.e) hp");
        value.setPromptText("i.e) 5");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(name, 1, 0);
        grid.add(new Label("Value:"), 0, 1);
        grid.add(value, 1, 1);

        return grid;
    }

    private void manageList(String key, String value) {
        HBox prop = new HBox();
        prop.setSpacing(20);
        listview.setVgap(10);
        listview.setHgap(10);
        delete = new Button("X");
        delete.setStyle("-fx-font-size: 8px;"
                + "-fx-text-fill: white;"
                + "-fx-background-color: black;");
        Text keyText = new Text(key);
        Text valueText = new Text(value);
        prop.setStyle("-fx-padding: 5;"
                +"-fx-border-style: dashed");
        if (list.keySet().contains(key)) {
            new ErrorWindow("Error", "This is already existed name of property.").showAndWait();
        } else {
            prop.getChildren().addAll(delete, keyText, valueText);
            listview.getChildren().add(prop);
            list.put(key, value);
        }
        delete.setOnMouseClicked(e -> {
            listview.getChildren().remove(prop);
            list.remove(key, value);
        });
    }
}