package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.sidebar.subEditors.EntityEditor;
import authoringInterface.sidebar.treeItemEntries.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.*;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 *
 * @author Haotian Wang
 */
public class NewSideView implements SubView<StackPane>, SideViewInterface {
    private StackPane sidePane;
    private Map<String, EditTreeItem> objectMap;
    private static final String ROOT_NAME = "User Objects";

    public NewSideView() {
        sidePane = new StackPane();
        objectMap = new HashMap<>();
        TreeItem<String> rootNode = new TreeItem<>(ROOT_NAME);
        objectMap.put(ROOT_NAME, new Category(ROOT_NAME));
        rootNode.setExpanded(true);
        List<EditTreeItem> defaultList = new ArrayList<>(Arrays.asList(
                new Entity(0, "0"),
                new Entity(1, "X"),
                new Tile(0, "Default Grid")
        ));
        for (EditTreeItem item : defaultList) {
            objectMap.put(item.getName(), item);
            TreeItem<String> objectLeaf = new TreeItem<>(item.getName());
            boolean found = false;
            for (TreeItem<String> categoryNode : rootNode.getChildren()) {
                if (TreeItemType.valueOf(categoryNode.getValue()) == item.getType()) {
                    categoryNode.getChildren().add(objectLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> categoryNode = new TreeItem<>(item.getType().toString());
                rootNode.getChildren().add(categoryNode);
                categoryNode.getChildren().add(objectLeaf);
            }
        }
        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory(e -> new CustomTreeCellImpl());
        sidePane.getChildren().add(treeView);
    }

    /**
     * This method gets the underlying entities corresponding to the String entries in the tree view in the side bar.
     *
     * @param name: The name of the object to be queried.
     * @return The EditTreeItem object having the name.
     */
    public EditTreeItem getObject(String name) {
        return objectMap.get(name);
    }

    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    @Override
    public StackPane getView() {
        return sidePane;
    }

    /**
     * This inner class organizes the cell factory call back methods into a nicer format.
     *
     * @author Haotian Wang
     */
    private final class CustomTreeCellImpl extends TreeCell<String> {
        private TextField textField;
        private ContextMenu addMenu = new ContextMenu();

        private CustomTreeCellImpl() {
            MenuItem addMenuItem = new MenuItem("Add an entry");
            addMenuItem.setOnAction(e -> {
                switch (getItem()) {
                    case "ENTITY":
                        Stage stage = new Stage();
                        EntityEditor editor = new EntityEditor();
                        break;
                    case "SOUND":
                        break;
                    case "TILE":
                        break;
                }
            });
            addMenu.getItems().add(addMenuItem);
            setOnDragDetected(e -> startFullDrag());
        }


        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (
                            !getTreeItem().isLeaf()&&getTreeItem().getParent()!= null
                    ){
                        setContextMenu(addMenu);
                    }
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    objectMap.put(textField.getText(), objectMap.get(getItem()));
                    objectMap.remove(getItem());
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });

        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }
}
