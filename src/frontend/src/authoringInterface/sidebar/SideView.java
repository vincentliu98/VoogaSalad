package authoringInterface.sidebar;

import api.SubView;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.SimpleEntityClass;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import gameObjects.tile.SimpleTileClass;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import utils.NodeInstanceController;

import java.util.*;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 *
 * @author Haotian Wang
 */
public class SideView implements SubView<StackPane> {
    private StackPane sidePane;
    private GameObjectsCRUDInterface gameObjectsManager;
    private static final String ROOT_NAME = "Game Objects";

    public SideView(GameObjectsCRUDInterface manager) {
        gameObjectsManager = manager;
        sidePane = new StackPane();
        TreeItem<String> rootNode = new TreeItem<>(ROOT_NAME);
        gameObjectsManager.createCategoryClass(ROOT_NAME);
        rootNode.setExpanded(true);
        List<GameObjectClass> defaultList = new ArrayList<>(Arrays.asList(
                new SimpleEntityClass("O"),
                new SimpleEntityClass("X"),
                new SimpleTileClass("Default Grid")
                // TODO: new SimpleSoundClass("Sound file")
        ));
        for (GameObjectClass item : defaultList) {
            switch (item.getType()) {
                case CATEGORY:
                    gameObjectsManager.createCategoryClass(item.getClassName().getValue());
                    break;
                case ENTITY:
                    gameObjectsManager.createEntityClass(item.getClassName().getValue());
                    break;
                case TILE:
                    gameObjectsManager.createTileClass(item.getClassName().getValue());
                    break;
            }
            TreeItem<String> objectLeaf = new TreeItem<>(item.getClassName().getValue());
            boolean found = false;
            for (TreeItem<String> categoryNode : rootNode.getChildren()) {
                if (categoryNode.getValue() == item.getType().toString()) {
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
        treeView.setCellFactory(e -> new CustomTreeCellImpl(gameObjectsManager));
        sidePane.getChildren().add(treeView);
        treeView.getStyleClass().add("myTree");
        sidePane.getStyleClass().add("mySide");
        sidePane.setOnDragOver(e -> System.out.println("yes"));
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
