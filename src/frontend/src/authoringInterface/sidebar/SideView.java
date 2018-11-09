package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.sidebar.subTreeViews.EntitySubTreeView;
import authoringInterface.sidebar.subTreeViews.SoundSubTreeView;
import authoringInterface.sidebar.subTreeViews.TilesetsSubTreeView;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class SideView implements SubView {
    private final TreeView<String> rootTreeView;
    private final TreeItem<String> rootTreeItem;
    private final TreeItem<String> entityTreeView;
    private final TreeItem<String> soundTreeView;
    private final TreeItem<String> tileSetsTreeView;
    private StackPane sideView;

    public SideView() {
        sideView = new StackPane();
        rootTreeItem = new TreeItem<>("User Settings");
        rootTreeItem.setExpanded(true);
        entityTreeView = new EntitySubTreeView().getRootItem();
        soundTreeView = new SoundSubTreeView().getRootItem();
        tileSetsTreeView = new TilesetsSubTreeView().getRootItem();
        rootTreeItem.getChildren().addAll(entityTreeView,soundTreeView,tileSetsTreeView);
        rootTreeView = new TreeView<>(rootTreeItem);
        sideView.getChildren().addAll(rootTreeView);
    }

    @Override
    public Node getView() {
        return sideView;
    }
}
