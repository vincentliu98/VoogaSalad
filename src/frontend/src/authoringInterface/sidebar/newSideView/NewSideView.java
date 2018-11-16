package authoringInterface.sidebar.newSideView;

import api.SubView;
import authoringInterface.sidebar.*;
import authoringInterface.sidebar.old.SideView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

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
}
