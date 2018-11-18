package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import authoringInterface.sidebar.treeItemEntries.Entity;
import authoringInterface.sidebar.treeItemEntries.TreeItemType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;

/**
 * This abstract class provides a boiler plate for different editors because they are pretty similar.
 *
 * @author Haotian Wang
 */
public abstract class AbstractObjectEditor<T extends EditTreeItem> implements ObjectEditor<T>, SubView<AnchorPane> {
    protected AnchorPane rootPane;
    protected Text inputText;
    protected TextField nameField;
    protected Button confirm;
    protected Button cancel;
    protected TreeItem<String> treeItem;
    protected Map<String, EditTreeItem> objectMap;
    protected Map<Node, EditTreeItem> nodeToObjectMap;
    protected EditingMode editingMode;
    protected Node nodeEdited;

    public AbstractObjectEditor() {
        editingMode = EditingMode.NONE;
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
     * @param map: The map from String name to Entity.
     */
    @Override
    public void editTreeItem(TreeItem<String> treeItem, Map<String, EditTreeItem> map) {
        this.treeItem = treeItem;
        this.objectMap = map;
        editingMode = EditingMode.EDIT_TREEITEM;
    }

    /**
     * Register the object map.
     *
     * @param treeItem: An existing TreeItem.
     * @param map: The map from String name to Entity.
     */
    @Override
    public void addTreeItem(TreeItem<String> treeItem, Map<String, EditTreeItem> map) {
        this.treeItem = treeItem;
        this.objectMap = map;
        editingMode = EditingMode.ADD_TREEITEM;
    }

    /**
     * Register the node to Object map.
     *
     * @param node: The node that is to be altered.
     * @param map: The node to user object map.
     */
    @Override
    public void editNode(Node node, Map<Node, EditTreeItem> map) {
        this.nodeToObjectMap = map;
        this.nodeEdited = node;
        editingMode = EditingMode.EDIT_NODE;
    }
}
