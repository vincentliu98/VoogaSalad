package authoringInterface.sidebar;

import api.SubView;
import authoringUtils.exception.DuplicateGameObjectClassException;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.SimpleEntityClass;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.player.SimplePlayerClass;
import gameObjects.sound.SimpleSoundClass;
import gameObjects.tile.SimpleTileClass;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import utils.nodeInstance.NodeInstanceController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 *
 * @author Haotian Wang
 */
public class SideView implements SubView<StackPane> {
    private StackPane sidePane;
    private GameObjectsCRUDInterface gameObjectsManager;
    private NodeInstanceController nodeInstanceController;
    private static final String ROOT_NAME = "Game Objects";

    public SideView(GameObjectsCRUDInterface manager, NodeInstanceController controller) {
        gameObjectsManager = manager;
        nodeInstanceController = controller;
        sidePane = new StackPane();
        TreeItem<String> rootNode = new TreeItem<>(ROOT_NAME);
        try {
            gameObjectsManager.createCategoryClass(ROOT_NAME);
        } catch (DuplicateGameObjectClassException e) {
            // TODO
            e.printStackTrace();
        }
        rootNode.setExpanded(true);
        try {
            gameObjectsManager.createCategoryClass("ENTITY");
            gameObjectsManager.createCategoryClass("TILE");
            gameObjectsManager.createCategoryClass("SOUND");
            gameObjectsManager.createCategoryClass("PLAYER");
        } catch (DuplicateGameObjectClassException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
        List<GameObjectClass> defaultList = new ArrayList<>(Arrays.asList(
                new SimpleEntityClass("O"),
                new SimpleEntityClass("X"),
                new SimpleTileClass("Default Grid"),
                new SimplePlayerClass("Default Player"),
                new SimpleSoundClass("Sound file")
        ));
        for (GameObjectClass item : defaultList) {
            try {
                gameObjectsManager.createGameObjectClass(item.getType(), item.getClassName().getValue());
            } catch (DuplicateGameObjectClassException e) {
                // TODO: proper error handling
                e.printStackTrace();
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
        treeView.setCellFactory(e -> new CustomTreeCellImpl(gameObjectsManager, nodeInstanceController));
        sidePane.getChildren().add(treeView);
        treeView.getStyleClass().add("myTree");
        sidePane.getStyleClass().add("mySide");
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
