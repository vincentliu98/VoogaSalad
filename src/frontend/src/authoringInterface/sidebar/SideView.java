package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.spritechoosingwindow.EntityWindow;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.*;

import java.util.Stack;

/**
 * A sidebar that contains many TreeItems. Functions include adding a cell and
 * storing the cell in the ListObjectManager. All the user's settings will go here,
 * and it supports drag and drop functionality
 *
 * Note that major categories of TreeItem can only be set during initialization of ListObjectManager, e.g. "EntityClass"
 *
 * @author jl729
 */

// TODO: 11/12/18 Make the string in the TreeCell represent the object itself

public class SideView implements SubView<StackPane> {
    private final ObjectManager objectManager;
    //    private final TreeView<String> rootTreeView;
//    private final TreeItem<String> rootTreeItem;
//    private final TreeItem<String> entityTreeItem;
//    private final TreeItem<String> soundTreeItem;
//    private final TreeItem<String> tileSetsTreeItem;
    private StackPane sideView;
    private TreeItem<String> rootNode = new TreeItem<>("User Settings");
    private ListObjectManager objects;
//
//    public SideView(Stage primaryStage) {
//        sideView = new StackPane();
//        rootTreeItem = new TreeItem<>("User Settings");
//        rootTreeItem.setExpanded(true);
//        entityTreeItem = new EntitySubTreeView(primaryStage).getRootItem();
//        soundTreeItem = new SoundSubTreeView(primaryStage).getRootItem();
//        tileSetsTreeItem = new TileSetsSubTreeView(primaryStage).getRootItem();
//        rootTreeItem.getChildren().addAll(entityTreeItem, soundTreeItem, tileSetsTreeItem);
//        rootTreeView = new TreeView<>(rootTreeItem);
//        rootTreeView.setOnMouseClicked(event -> new EntityWindow(primaryStage));
//
//        rootTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handle(newValue));
//
//        sideView.getChildren().addAll(rootTreeView);
//    }
//
//    private void handle(Object newValue) {
//        System.out.println(newValue);
//    }

    public SideView(Stage primaryStage) {
        sideView = new StackPane();
        objectManager = new ObjectManager();
        objects = new ListObjectManager();

        rootNode.setExpanded(true);
        for (ListObject listObject : objects) {
            // create tree items to accommodate objects
            TreeItem<String> empLeaf = new TreeItem<>(listObject.getName());
            boolean found = false;
            // loop through sub items e.g. "EntityClass" and "Grid"
            for (TreeItem<String> depNode : rootNode.getChildren()) {
                if (depNode.getValue().contentEquals(listObject.getType())) {
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }
            if (!found) {
                TreeItem<String> depNode = new TreeItem(listObject.getType());
                rootNode.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }
        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        // produce cells using TextFieldTreeCellImpl
        treeView.setCellFactory(e -> new TextFieldTreeCellImpl(this, primaryStage, objectManager));

        sideView.getChildren().add(treeView);
    }

    public void addCell(Entity entity, TreeItem<String> item){
            TreeItem newObject = new TreeItem<>(entity.getName());
            newObject.setGraphic(new ImageView(entity.getSprite()));
            // add to ListObjectManager
            var type = item.getValue();
            var myObj = new ListObject(entity.getName(), type, entity.getId());
            objects.add(myObj);

            System.out.println("Type: " + type + "Cell: " + myObj);
            item.getChildren().add(newObject);
    }

    private final class TextFieldTreeCellImpl extends TreeCell<String> {

        private TextField textField;
        private ContextMenu addMenu = new ContextMenu();

        public TextFieldTreeCellImpl(SideView mySideView, Stage primaryStage, ObjectManager objectManager) {
            MenuItem addMenuItem = new MenuItem("Add an Object");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction(e -> {
                // show the pop-up window
                var type = getTreeItem().getValue();
                var id = getTreeItem().getChildren().size();
                var myItem = getTreeItem();
                if (type.equals("Entity")){
                    new EntityWindow(primaryStage, objectManager, mySideView, id, myItem);
                }
                else if (type.equals("Tile")){
                    // Generate a TileWindow
//                    new EntityWindow(primaryStage, objectManager);
                }
                else if (type.equals("Sound")) {

                }
//                var name = "New ListObject";
//                TreeItem newObject = new TreeItem<>(name);
//                myObj = new ListObject(name, type, id);
//                objects.add(myObj);
//                getTreeItem().getChildren().add(newObject);
            });
            setOnDragDetected(e -> {
                startFullDrag();
            });
        }

        // select all texts when first start
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

        // recover the text if canceling the edit
        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        // update the texts in the item
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
                            !getTreeItem().isLeaf() && getTreeItem().getParent() != null
                    ) {
                        setContextMenu(addMenu);
                    }
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyReleased(t -> {
                if (t.getCode() == KeyCode.ENTER) {
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

    // a class for all the objects such as entity, grid, and sound
    public static class ListObject {

        private final SimpleStringProperty name;
        private final SimpleStringProperty type;
        private final SimpleIntegerProperty id;

        public ListObject(String name, String type, Integer id) {
            this.name = new SimpleStringProperty(name);
            this.type = new SimpleStringProperty(type);
            this.id = new SimpleIntegerProperty(id);
        }

        public String getName() {
            return name.get();
        }

        @Override
        public String toString() {
            return "ListObject{" +
                    "name=" + name +
                    ", type=" + type +
                    ", id=" + id +
                    '}';
        }

        public String getType() {
            return type.get();
        }

        public Integer getId() {
            return id.get();
        }
    }

    @Override
    public StackPane getView() {
        return sideView;
    }
}


