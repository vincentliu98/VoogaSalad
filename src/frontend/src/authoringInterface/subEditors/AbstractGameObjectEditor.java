package authoringInterface.subEditors;

import api.SubView;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.GameObjectsCRUDInterface;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

/**
 * This abstract class provides a boiler plate for different editors because they are pretty similar.
 *
 * @author Haotian Wang
 */
public abstract class AbstractGameObjectEditor<T extends GameObjectClass, V extends GameObjectInstance> implements SubView<AnchorPane> {
    protected AnchorPane rootPane;
    protected Text inputText;
    protected TextField nameField;
    protected Button confirm;
    protected Button cancel;
    protected TreeItem<String> treeItem;
    protected GameObjectsCRUDInterface gameObjectManager;
    protected EditingMode editingMode;
    protected Node nodeEdited;

    public AbstractGameObjectEditor(GameObjectsCRUDInterface manager) {
        editingMode = EditingMode.NONE;
        gameObjectManager = manager;
        rootPane = new AnchorPane();
        inputText = new Text();
        nameField = new TextField();
        confirm = new Button("Apply");
        cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            ((Stage) rootPane.getScene().getWindow()).close();
        });
        rootPane.getChildren().addAll(inputText, nameField, confirm, cancel);
        setupBasicLayout();
    }

    /**
     * This sets up the basic layout for the Abstract Editor.
     */
    private void setupBasicLayout() {
        AnchorPane.setLeftAnchor(inputText, 50.0);
        AnchorPane.setTopAnchor(inputText, 50.0);
        inputText.setLayoutX(14);
        inputText.setLayoutY(27);
        nameField.setLayoutX(208);
        nameField.setLayoutY(37);
        confirm.setLayoutX(296);
        confirm.setLayoutY(436);
        cancel.setLayoutX(391);
        cancel.setLayoutY(436);
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
        readGameObjectClass(gameObjectClass);
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
     * @param gameObjectInstance: The GameObjectInstance that is associated with the Node on the rootPane.
     */
    public void editNode(Node node, V gameObjectInstance) {
        this.nodeEdited = node;
        editingMode = EditingMode.EDIT_NODE;
        readGameObjectInstance(gameObjectInstance);
    }

    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param gameObject
     */
    public abstract void readGameObjectInstance(V gameObject);
//
//    /**
//     * Return the object after edits in this ObjectEditor.
//     *
//     * @return A specific user object.
//     */
//    public abstract V getObject();
//
    /**
     * Read the GameObjectClass represented by this editor.
     *
     * @param gameObjectClass: The GameObjectClass interface that is being read.
     */
    public abstract void readGameObjectClass(T gameObjectClass);
//
//    /**
//     * @return The GameObjectClass stored in the internal memory right now.
//     */
//    public abstract T getGameObjectClass();
}
