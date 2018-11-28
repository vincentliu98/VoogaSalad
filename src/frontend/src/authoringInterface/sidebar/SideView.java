package authoringInterface.sidebar;

import api.SubView;
import gameObjects.GameObjectsCRUDInterface;
import gameplay.Entity;
import gameplay.Tile;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.util.*;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 *
 * @author Haotian Wang
 */
public class SideView implements SubView<StackPane>, SideViewInterface {
    private StackPane sidePane;
    private GameObjectsCRUDInterface gameObjectsManager;
    private static final String ROOT_NAME = "User Objects";

    public SideView(GameObjectsCRUDInterface manager) {
        gameObjectsManager = manager;
        sidePane = new StackPane();
        objectMap = new HashMap<>();
        TreeItem<String> rootNode = new TreeItem<>(ROOT_NAME);
        objectMap.put(ROOT_NAME, new Category(ROOT_NAME));
        rootNode.setExpanded(true);
        List<EditTreeItem> defaultList = new ArrayList<>(Arrays.asList(
                new Entity(0, "O"),
                new Entity(1, "X"),
                new Tile(0, "Default Grid"),
                new Sound(0, "Sound file")
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
        treeView.setCellFactory(e -> new CustomTreeCellImpl(objectMap));
        sidePane.getChildren().add(treeView);
        treeView.getStyleClass().add("myTree");
        sidePane.getStyleClass().add("mySide");
    }

    /**
     * This method gets the underlying gameObjects corresponding to the String entries in the tree view in the side bar.
     *
     * @param name: The name of the object to be queried.
     * @return The EditTreeItem object having the name.
     */
    @Override
    public EditTreeItem getObject(String name) {
        return objectMap.get(name);
    }

    /**
     * @return The internal object map.
     */
    @Override
    public Map<String, EditTreeItem> getObjectMap() {
        return objectMap;
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
}
