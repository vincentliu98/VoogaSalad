package authoringInterface.sidebar;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class SideView implements SubView<StackPane> {
    private StackPane sideView;
    private TreeView<String> treeView;

    public SideView() {
        sideView = new StackPane();
        TreeItem<String> rootItem = new TreeItem<>("Inbox");
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<> ("Message" + i);
            rootItem.getChildren().add(item);
        }
        treeView = new TreeView<> (rootItem);
        sideView.getChildren().add(treeView);
    }


    @Override
    public StackPane getView() {
        return sideView;
    }
}
