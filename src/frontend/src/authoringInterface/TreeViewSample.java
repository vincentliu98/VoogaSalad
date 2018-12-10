//package authoringInterface;
//
//import authoringInterface.sidebar.ListObjectManager;
//import authoringInterface.spritechoosingwindow.EntityWindow;
//import javafx.application.Application;
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
///**
// * Example code to implement side bar. Functions include adding a cell and
// * storing the cell in the ListObjectManager.
// *
// * Note that major categories of TreeItem can only be set during initialization of ListObjectManager, e.g. "GameObjectClass"
// *
// * @author jl729
// */
//
//
//public class TreeViewSample extends Application {
//    //        private final Node rootIcon =
////                new ImageView(new Image(getClass().getResourceAsStream("root.png")));
////        private final Image depIcon =
////                new Image(getClass().getResourceAsStream("type.png"));
//    private TreeItem<String> rootNode = new TreeItem<>("User Settings");
//    private ListObjectManager objects = new ListObjectManager();
//
//    public static void main(String[] args) {
//        Application.launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) {
//        rootNode.setExpanded(true);
//        for (ListObject employee : objects) {
//            // create tree items to accommodate objects
//            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
//            boolean found = false;
//            // loop through sub items e.g. "GameObjectClass" and "Grid"
//            for (TreeItem<String> depNode : rootNode.getChildren()) {
//                if (depNode.getValue().contentEquals(employee.getType())) {
//                    depNode.getChildren().add(empLeaf);
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                TreeItem<String> depNode = new TreeItem(employee.getType());
//                rootNode.getChildren().add(depNode);
//                depNode.getChildren().add(empLeaf);
//            }
//        }
//
//        stage.setTitle("Tree View Sample");
//        VBox box = new VBox();
//        final Scene scene = new Scene(box, 400, 300);
//        scene.setFill(Color.LIGHTGRAY);
//
//        TreeView<String> treeView = new TreeView<>(rootNode);
//        treeView.setEditable(true);
//        // produce cells using TextFieldTreeCellImpl
//        treeView.setCellFactory(e -> new TextFieldTreeCellImpl(objects));
//
//        box.getChildren().add(treeView);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    private final class TextFieldTreeCellImpl extends TreeCell<String> {
//
//        private TextField textField;
//        private ContextMenu addMenu = new ContextMenu();
//        private ListObject myObj;
//
//        public TextFieldTreeCellImpl(ListObjectManager objects) {
//            MenuItem addMenuItem = new MenuItem("Add ListObject");
//            addMenu.getItems().add(addMenuItem);
//            addMenuItem.setOnAction(e -> {
//                var popUp = new EntityWindow(new Stage());
//                var name = "New ListObject";
//                TreeItem newObject = new TreeItem<>(name);
//                var id = getTreeItem().getChildren().size();
//                var type = getTreeItem().getValue();
//                myObj = new ListObject(name, type, id);
//                objects.add(myObj);
//                System.out.println(objects);
//                getTreeItem().getChildren().add(newObject);
//            });
//        }
//
//        // select all texts when first start
//        @Override
//        public void startEdit() {
//            super.startEdit();
//
//            if (textField == null) {
//                createTextField();
//            }
//            setText(null);
//            setGraphic(textField);
//            textField.selectAll();
//        }
//
//        // recover the text if canceling the edit
//        @Override
//        public void cancelEdit() {
//            super.cancelEdit();
//
//            setText(getItem());
//            setGraphic(getTreeItem().getGraphic());
//        }
//
//        // update the texts in the item
//        @Override
//        public void updateItem(String item, boolean createPhaseGraph) {
//            super.updateItem(item, createPhaseGraph);
//
//            if (createPhaseGraph) {
//                setText(null);
//                setGraphic(null);
//            } else {
//                if (isEditing()) {
//                    if (textField != null) {
//                        textField.setText(getString());
//                    }
//                    setText(null);
//                    setGraphic(textField);
//                } else {
//                    setText(getString());
//                    setGraphic(getTreeItem().getGraphic());
//                    if (
//                            !getTreeItem().isLeaf() && getTreeItem().getParent() != null
//                    ) {
//                        setContextMenu(addMenu);
//                    }
//                }
//            }
//        }
//
//        private void createTextField() {
//            textField = new TextField(getString());
//            textField.setOnKeyReleased(t -> {
//                if (t.getCode() == KeyCode.ENTER) {
//                    commitEdit(textField.getText());
//                } else if (t.getCode() == KeyCode.ESCAPE) {
//                    cancelEdit();
//                }
//            });
//
//        }
//
//        private String getString() {
//            return getItem() == null ? "" : getItem();
//        }
//    }
//
//    // a class for all the objects such as entity, grid, and sound
//    public static class ListObject {
//
//        private final SimpleStringProperty name;
//        private final SimpleStringProperty type;
//        private final SimpleIntegerProperty id;
//
//        public ListObject(String name, String type, Integer id) {
//            this.name = new SimpleStringProperty(name);
//            this.type = new SimpleStringProperty(type);
//            this.id = new SimpleIntegerProperty(id);
//        }
//
//        public String getName() {
//            return name.get();
//        }
//
//        @Override
//        public String toString() {
//            return "ListObject{" +
//                    "name=" + name +
//                    ", type=" + type +
//                    ", id=" + id +
//                    '}';
//        }
//
//        public String getType() {
//            return type.get();
//        }
//
//        public Integer getId() {
//            return id.get();
//        }
//    }
//}
