package authoringInterface.sidebar.newSideView;

import api.SubView;
import authoringInterface.sidebar.Category;
import authoringInterface.sidebar.EditTreeItem;
import authoringInterface.sidebar.Entity;
import authoringInterface.sidebar.Tile;
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
