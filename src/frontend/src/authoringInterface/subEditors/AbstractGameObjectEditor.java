package authoringInterface.subEditors;

import api.SubView;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.crud.GameObjectsCRUDInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.nodeInstance.NodeInstanceController;
import utils.exception.NodeNotFoundException;

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
    Button cancel;
    TreeItem<String> treeItem;
    GameObjectsCRUDInterface gameObjectManager;
    NodeInstanceController nodeInstanceController;
    EditingMode editingMode;
    Node nodeEdited;
    T gameObjectClass;
    V gameObjectInstance;
    GridPane layout;

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
            System.out.println(e.toString());
            ((Stage) rootPane.getScene().getWindow()).close();
        });
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
        nameField.setLayoutY(35);
        confirm.setLayoutX(296);
        confirm.setLayoutY(950);
        cancel.setLayoutX(391);
        cancel.setLayoutY(950);
        layout.setVgap(50);
        layout.setHgap(50);
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
     * @param treeItem: An existing TreeItem.
     * @param gameObjectClass: The GameObjectClass associated with the TreeItem to be edited by the user.
     */
    public void editTreeItem(TreeItem<String> treeItem, T gameObjectClass) {
        this.treeItem = treeItem;
        editingMode = EditingMode.EDIT_TREEITEM;
        this.gameObjectClass = gameObjectClass;
        readGameObjectClass();
    }

    /**
     * Register the object map.
     *
     * @param treeItem: An existing TreeItem.
     */
    public void addTreeItem(TreeItem<String> treeItem) {
        this.treeItem = treeItem;
        editingMode = EditingMode.ADD_TREEITEM;
    }

    /**
     * Register the node to Object map.
     *
     * @param node: The node that is to be altered.
     * @param controller: The NodeInstanceController that controls the relationship between a Node and a GameObjectInstance.
     */
    public void editNode(Node node, NodeInstanceController controller) {
        this.nodeEdited = node;
        editingMode = EditingMode.EDIT_NODE;
        nodeInstanceController = controller;
        try {
            this.gameObjectInstance = controller.getGameObjectInstance(node);
        } catch (NodeNotFoundException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        readGameObjectInstance();
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     */
    protected abstract void readGameObjectInstance();

    /**
     * Read the GameObjectClass represented by this editor.
     */
    protected abstract void readGameObjectClass();
}
