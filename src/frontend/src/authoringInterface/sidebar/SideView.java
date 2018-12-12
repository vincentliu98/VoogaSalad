package authoringInterface.sidebar;

import api.SubView;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.entity.EntityClass;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectType;
import gameObjects.tile.TileClass;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import utils.imageManipulation.ImageManager;
import utils.imageManipulation.JavaFxOperation;
import utils.imageSelector.ImageSelectorController;
import utils.nodeInstance.NodeInstanceController;

/**
 * This class represents a new SideView implementation that has a JavaFx TreeView object inside, but with cleaner implementation.
 * <p>
 * It sources from gameObjectsManager to initialize its items
 *
 * @author Haotian Wang
 */
public class SideView implements SubView<StackPane> {
    private static final double ICON_WIDTH = 50;
    private static final double ICON_HEIGHT = 50;
    private static final String ROOT_NAME = "Game Objects";
    private StackPane sidePane;
    private GameObjectsCRUDInterface gameObjectsManager;
    private NodeInstanceController nodeInstanceController;

    public SideView(GameObjectsCRUDInterface manager, NodeInstanceController controller, ImageSelectorController imageSelectorController) {
        gameObjectsManager = manager;
        nodeInstanceController = controller;
        sidePane = new StackPane();
        TreeItem<String> rootNode = new TreeItem<>(ROOT_NAME);
        rootNode.setExpanded(true);

        TreeView<String> treeView = new TreeView<>(rootNode);
        treeView.setEditable(true);
        treeView.setCellFactory(e -> new CustomTreeCellImpl(gameObjectsManager, nodeInstanceController, imageSelectorController));

        for (GameObjectClass item : gameObjectsManager.getAllClasses()) {
            switch(item.getType()) {
                case ENTITY:
                    var entityClass = ((EntityClass) item);
                    imageSelectorController.groovyPaneOf(entityClass); // just to register
                    break;
                case TILE:
                    var tileClass = ((TileClass) item);
                    imageSelectorController.groovyPaneOf(tileClass); // just to register
                    break;
            }

            TreeItem<String> objectLeaf = new TreeItem<>(item.getClassName());
            boolean found = false;
            for (TreeItem<String> categoryNode : rootNode.getChildren()) {
                if (categoryNode.getValue().equals(item.getType().toString())) {
                    tryToAddIcon(objectLeaf, item);
                    categoryNode.getChildren().add(objectLeaf);
                    found = true;
                    break;
                }
            }
            if (!found && item.getType() != GameObjectType.CATEGORY) {
                TreeItem<String> categoryNode = new TreeItem<>(item.getType().toString());
                rootNode.getChildren().add(categoryNode);
                categoryNode.getChildren().add(objectLeaf);
                tryToAddIcon(objectLeaf, item);
            }
        }
        sidePane.getChildren().add(treeView);
        treeView.getStyleClass().add("myTree");
        sidePane.getStyleClass().add("mySide");
    }

    private void tryToAddIcon(TreeItem<String> objectLeaf, GameObjectClass item) {
        try {
            var icon = new ImageView(ImageManager.getPreview(item));
            JavaFxOperation.setWidthAndHeight(icon, ICON_WIDTH, ICON_HEIGHT);
            objectLeaf.setGraphic(icon);
        } catch (Exception ignored) {
        } // if it doesn't, i dunno
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
