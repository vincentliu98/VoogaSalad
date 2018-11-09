package authoringInterface.sidebar;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

public class SideView implements SubView {
    private final TreeView<String> rootTreeView;
    private final TreeItem<String> rootTreeItem;
    private final TreeItem<String> entityTreeView;
    private final TreeItem<String> soundTreeView;
    private final TreeItem<String> tilesetsTreeView;
    private StackPane sideView;

    public SideView() {
        sideView = new StackPane();
        rootTreeItem = new TreeItem<>("User Settings");
        entityTreeView = new EntitySubTreeView().getRootItem();
        soundTreeView = new SoundSubTreeView().getRootItem();
        tilesetsTreeView = new TilesetsSubTreeView().getRootItem();
        rootTreeItem.getChildren().addAll(entityTreeView,soundTreeView,tilesetsTreeView);
        rootTreeView = new TreeView<>(rootTreeItem);
        sideView.getChildren().addAll(rootTreeView);
    }

    @Override
    public Node getView() {
        return sideView;
    }
}
