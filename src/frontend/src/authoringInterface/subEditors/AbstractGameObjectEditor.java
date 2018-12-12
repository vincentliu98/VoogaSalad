package authoringInterface.subEditors;

import api.SubView;
import authoringInterface.subEditors.exception.IllegalGameObjectNamingException;
import authoringInterface.subEditors.exception.IllegalGeometryException;
import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.ErrorWindow;
import utils.exception.GridIndexOutOfBoundsException;
import utils.exception.NodeNotFoundException;
import utils.exception.PreviewUnavailableException;
import utils.exception.UnremovableNodeException;
import utils.nodeInstance.NodeInstanceController;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * This abstract class provides a boiler plate for different editors because they are pretty similar.
 *
 * @author Haotian Wang
 */
public abstract class AbstractGameObjectEditor<T extends GameObjectClass, V extends GameObjectInstance> implements SubView<AnchorPane> {
    AnchorPane rootPane;
    Label nameLabel;
    TextField nameField;
    Button confirm;
    TreeItem<String> treeItem;
    GameObjectsCRUDInterface gameObjectManager;
    NodeInstanceController nodeInstanceController;
    EditingMode editingMode;
    Node nodeEdited;
    T gameObjectClass;
    V gameObjectInstance;
    GridPane layout;
    Label propLabel = new Label("Properties");
    Button addProperties = new Button("Add");
    GridPane listProp;
    FlowPane listview;
    ObservableSet<PropertyBox> propBoxes;
    private Button cancel;

    AbstractGameObjectEditor(GameObjectsCRUDInterface manager) {
        editingMode = EditingMode.NONE;
        layout = new GridPane();
        gameObjectManager = manager;
        rootPane = new AnchorPane();
        rootPane.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #868c87;");
        nameLabel = new Label();
        nameField = new TextField();
        confirm = new Button("Apply");
        cancel = new Button("Cancel");
        confirm.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        cancel.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        cancel.setOnAction(e -> {
            closeEditor();
        });
        listProp = new GridPane();
        listview = new FlowPane();
        listview.setVgap(10);
        listview.setHgap(10);
        propBoxes = FXCollections.observableSet();
        propBoxes.addListener((SetChangeListener<? super PropertyBox>) e -> {
            if (e.wasAdded()) listview.getChildren().add(e.getElementAdded());
            else listview.getChildren().remove(e.getElementRemoved());
        });

        listProp.getChildren().add(listview);
        nameField.setPromptText("Your entity name");

        addProperties.setStyle("-fx-text-fill: white;"
                + "-fx-background-color: #343a40;");
        addProperties.setOnAction(e -> new PropertyInputDialog().showAndWait().ifPresent(prop -> {
            boolean added = false;
            for (var box : propBoxes) {
                if (box.getKey().equals(prop.getKey())) {
                    box.setValue(prop.getValue());
                    added = true;
                }
            }
            if (!added) propBoxes.add(new PropertyBox(prop.getKey(), prop.getValue(), propBoxes::remove));
        }));
        rootPane.getChildren().addAll(nameLabel, nameField, layout, confirm, cancel);
        setupBasicLayout();
    }

    /**
     * This sets up the basic layout for the Abstract Editor.
     */
    private void setupBasicLayout() {
        AnchorPane.setLeftAnchor(nameLabel, 50.0);
        AnchorPane.setTopAnchor(nameLabel, 50.0);
        nameLabel.setLayoutX(14);
        nameLabel.setLayoutY(30);
        nameField.setLayoutX(208);
        nameField.setLayoutY(45);
        confirm.setLayoutX(396);
        confirm.setLayoutY(560);
        cancel.setLayoutX(491);
        cancel.setLayoutY(560);
        layout.setVgap(30);
        layout.setHgap(30);
        AnchorPane.setTopAnchor(layout, 100.0);
        AnchorPane.setRightAnchor(layout, 0.0);
        AnchorPane.setLeftAnchor(layout, 50.0);
        AnchorPane.setBottomAnchor(layout, 100.0);
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

    /**
     * Register the editor with an existing TreeItem in order to update or edit existing entries.
     *
     * @param treeItem:        An existing TreeItem.
     * @param gameObjectClass: The GameObjectClass associated with the TreeItem to be edited by the user.
     */
    public void editTreeItem(TreeItem<String> treeItem, T gameObjectClass) {
        this.treeItem = treeItem;
        editingMode = EditingMode.EDIT_TREEITEM;
        this.gameObjectClass = gameObjectClass;
        readGameObjectClass();
        confirm.setOnAction(e -> {
            try {
                confirmEditTreeItem();
            } catch (IllegalGeometryException e1) {
                new ErrorWindow("Illegal Geometry", e1.toString()).showAndWait();
                return;
            } catch (InvalidOperationException e1) {
                new ErrorWindow("Invalid Operation", e1.toString()).showAndWait();
                return;
            } catch (PreviewUnavailableException e1) {
                new ErrorWindow("Preview Unavailable", e1.toString()).showAndWait();
                return;
            } catch (IllegalGameObjectNamingException e1) {
                new ErrorWindow("Illegal Naming", e1.toString()).showAndWait();
                return;
            } catch (DuplicateGameObjectClassException e1) {
                ErrorWindow.display("Duplicate Class Name", e1.toString());
                return;
            }
            closeEditor();
        });
    }

    /**
     * Register the object map.
     *
     * @param treeItem: An existing TreeItem.
     */
    public void addTreeItem(TreeItem<String> treeItem) {
        this.treeItem = treeItem;
        editingMode = EditingMode.ADD_TREEITEM;
        confirm.setOnAction(e -> {
            try {
                confirmAddTreeItem();
            } catch (IllegalGeometryException e1) {
                new ErrorWindow("Illegal Geometry", e1.toString()).showAndWait();
                return;
            } catch (PreviewUnavailableException e1) {
                new ErrorWindow("Preview Unavailable", e1.toString()).showAndWait();
                return;
            } catch (IllegalGameObjectNamingException e1) {
                new ErrorWindow("Illegal Naming", e1.toString()).showAndWait();
                return;
            } catch (DuplicateGameObjectClassException e1) {
                new ErrorWindow("Duplicate GameObjectClass", e1.toString()).showAndWait();
            }
            closeEditor();
        });
    }

    /**
     * Register the node to Object map.
     *
     * @param node:       The node that is to be altered.
     * @param controller: The NodeInstanceController that controls the relationship between a Node and a GameObjectInstance.
     */
    public void editNode(Node node, NodeInstanceController controller) {
        this.nodeEdited = node;
        editingMode = EditingMode.EDIT_NODE;
        nodeInstanceController = controller;
        try {
            this.gameObjectInstance = controller.getGameObjectInstance(node);
        } catch (NodeNotFoundException ignored) {
        }
        readGameObjectInstance();
        confirm.setOnAction(e -> {
            try {
                confirmEditNode();
            } catch (IllegalGeometryException e1) {
                new ErrorWindow("Illegal Geometry", e1.toString()).showAndWait();
                return;
            } catch (PreviewUnavailableException e1) {
                new ErrorWindow("Preview Unavailable", e1.toString()).showAndWait();
                return;
            } catch (UnremovableNodeException e1) {
                new ErrorWindow("Unremovable Node", e1.toString()).showAndWait();
                return;
            } catch (GridIndexOutOfBoundsException e1) {
                new ErrorWindow("Grid IndexOutOfBounds", e1.toString()).showAndWait();
                return;
            } catch (IllegalGameObjectNamingException e1) {
                new ErrorWindow("Illegal Naming", e1.toString()).showAndWait();
                return;
            }
            closeEditor();
        });
    }

    /**
     * This method reads in the properties of an existing GameObjectInstance.
     */
    void readInstanceProperties() {
        gameObjectInstance.getPropertiesMap().forEach((k, v) -> propBoxes.add(new PropertyBox(k, v, propBoxes::remove)));
    }

    /**
     * This method reads in the properties of an existing GameObjectClass.
     */
    void readClassProperties() {
        gameObjectClass.getPropertiesMap().forEach((k, v) -> propBoxes.add(new PropertyBox(k, v, propBoxes::remove)));
    }

    /**
     * This method writes to the properties of an existing GameObjectClass.
     */
    void writeClassProperties() {
        propBoxes.forEach(box -> gameObjectClass.getPropertiesMap().put(box.getKey(), box.getValue()));
    }

    /**
     * This method writes to the properties of an existing GameObjectInstance.
     */
    void writeInstanceProperties() {
        propBoxes.forEach(box -> gameObjectInstance.getPropertiesMap().put(box.getKey(), box.getValue()));
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    protected abstract void readGameObjectInstance();

    /**
     * Read the GameObjectClass represented by this editor.
     */
    protected abstract void readGameObjectClass();

    /**
     * This method sets up the confirm logic of adding new TreeItem.
     *
     * @throws IllegalGameObjectNamingException
     * @throws IllegalGeometryException
     * @throws PreviewUnavailableException
     * @throws DuplicateGameObjectClassException
     */
    protected abstract void confirmAddTreeItem() throws IllegalGameObjectNamingException, IllegalGeometryException, PreviewUnavailableException, DuplicateGameObjectClassException;

    /**
     * This method sets up the confirm logic of editing existing TreeItem.
     *
     * @throws IllegalGameObjectNamingException
     * @throws IllegalGeometryException
     * @throws InvalidOperationException
     * @throws PreviewUnavailableException
     */
    protected abstract void confirmEditTreeItem() throws IllegalGameObjectNamingException, IllegalGeometryException, InvalidOperationException, PreviewUnavailableException, DuplicateGameObjectClassException;

    /**
     * This method sets up the confirm logic of editing existing Node.
     *
     * @throws IllegalGameObjectNamingException
     * @throws IllegalGeometryException
     * @throws PreviewUnavailableException
     * @throws GridIndexOutOfBoundsException
     * @throws UnremovableNodeException
     */
    protected abstract void confirmEditNode() throws IllegalGameObjectNamingException, IllegalGeometryException, PreviewUnavailableException, GridIndexOutOfBoundsException, UnremovableNodeException;

    /**
     * This method closes the editor.
     */
    private void closeEditor() {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    /**
     * This method outputs a positive integer from a TextField input and throws appropriate errors.
     *
     * @param intInput: A TextField input.
     * @return An int representing the legal output.
     * @throws IllegalGeometryException
     */
    int outputPositiveInteger(TextField intInput) throws IllegalGeometryException {
        int ret;
        try {
            ret = Integer.parseInt(intInput.getText());
        } catch (NumberFormatException e) {
            throw new IllegalGeometryException(String.format("%s is not a positive integer", intInput.getText()), e);
        }
        if (ret == 0) {
            throw new IllegalGeometryException(String.format("%s is not a positive integer", intInput.getText()));
        }
        return ret;
    }

    /**
     * This method sets names for GameObjectClass.
     *
     * @return A String name for the GameObjectClass.
     * @throws IllegalGameObjectNamingException
     * @throws DuplicateGameObjectClassException
     */
    String getValidClassName() throws IllegalGameObjectNamingException, DuplicateGameObjectClassException {
        if (nameField.getText().trim().isEmpty()) {
            throw new IllegalGameObjectNamingException("GameObjectClass cannot have an empty name");
        }
        if(gameObjectClass == null || gameObjectClass.getClassName().equals(nameField.getText().trim())) return nameField.getText().trim();
        try {
            gameObjectManager.getGameObjectClass(nameField.getText().trim());
        } catch (GameObjectClassNotFoundException e) {
            return nameField.getText().trim();
        }
        throw new DuplicateGameObjectClassException(nameField.getText().trim() + " already exists, please rename your GameObjectClass");
    }

    /**
     * This method sets names for GameObjectInstance.
     *
     * @return A String name for the GameObjectInstance.
     * @throws IllegalGameObjectNamingException
     */
    String getValidInstanceName() throws IllegalGameObjectNamingException {
        if (nameField.getText().trim().isEmpty()) {
            throw new IllegalGameObjectNamingException("GameObjectInstance cannot have an empty name");
        }
        return nameField.getText().trim();
    }
}
