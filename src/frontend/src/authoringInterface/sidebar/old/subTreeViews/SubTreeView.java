package authoringInterface.sidebar.old.subTreeViews;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

abstract class SubTreeView {
    private TreeItem<String> rootItem;
    private Stage primaryStage;

    SubTreeView(String name, Stage primaryStage) {
        this.primaryStage = primaryStage;
        rootItem = new TreeItem<>(name);
        rootItem.setExpanded(true);
    }

    public TreeItem<String> getRootItem() {
        return rootItem;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
