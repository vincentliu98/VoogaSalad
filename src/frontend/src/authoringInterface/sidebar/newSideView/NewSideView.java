package authoringInterface.sidebar.newSideView;

import api.SubView;
import authoringInterface.sidebar.*;
import authoringInterface.sidebar.old.SideView;
import authoringInterface.spritechoosingwindow.EntityWindow;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 *
 * @author Haotian Wang
 */
public class NewSideView implements SubView<StackPane> {
    private StackPane sidePane;

    public NewSideView() {
        sidePane = new StackPane();
        TreeItem<EditTreeItem> rootNode = new TreeItem<>(new Category("User Objects"));
        rootNode.setExpanded(true);
        List<EditTreeItem> defaultList = new ArrayList<>(Arrays.asList(
                new Entity(0, "0"),
                new Entity(1, "X"),
                new Tile(0, "Default Grid")
        ));
        for (EditTreeItem item : defaultList) {
            TreeItem<EditTreeItem> objectLeaf = new TreeItem<>(item);
            boolean found = false;
            for (TreeItem<EditTreeItem> categoryNode : rootNode.getChildren()) {
                if (TreeItemType.valueOf(categoryNode.getValue().getName()) == item.getType()) {
                    categoryNode.getChildren().add(objectLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<EditTreeItem> categoryNode = new TreeItem(new Category(item.getType().toString()));
                rootNode.getChildren().add(categoryNode);
                categoryNode.getChildren().add(objectLeaf);
            }
        }
        TreeView<EditTreeItem> treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory(e -> new CustomTreeCellImpl());
        sidePane.getChildren().add(treeView);
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
    private final class CustomTreeCellImpl extends TreeCell<EditTreeItem> {
        private TextField textField;
        private ContextMenu addMenu = null;

        private CustomTreeCellImpl() {
            if (getTreeItem().getValue().getType() == TreeItemType.CATEGORY) {
                addMenu = new ContextMenu();
                MenuItem addMenuItem = new MenuItem("Add an entry");
                addMenuItem.setOnAction(e -> {
                    switch (getItem().getName()) {
                        case "ENTITY":
                            break;
                        case "SOUND":
                            break;
                        case "TILE":
                            break;
                    }
                });
            }
            setOnDragDetected(e -> {
                startFullDrag();
            });
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

            setText(getItem().getName());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(EditTreeItem item, boolean empty) {
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
                    getItem().setName(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().getName();
        }
    }
}
