package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.sidebar.old.subTreeViews.EntitySubTreeView;
import authoringInterface.sidebar.old.subTreeViews.SoundSubTreeView;
import authoringInterface.sidebar.old.subTreeViews.TileSetsSubTreeView;
import authoringInterface.spritechoosingwindow.EntityWindow;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SideView implements SubView {
    private final TreeView<String> rootTreeView;
    private final TreeItem<String> rootTreeItem;
    private final TreeItem<String> entityTreeView;
    private final TreeItem<String> soundTreeView;
    private final TreeItem<String> tileSetsTreeView;
    private StackPane sideView;

    public SideView(Stage primaryStage) {
        sideView = new StackPane();
        rootTreeItem = new TreeItem<>("User Settings");
        rootTreeItem.setExpanded(true);
        entityTreeView = new EntitySubTreeView(primaryStage).getRootItem();
        soundTreeView = new SoundSubTreeView(primaryStage).getRootItem();
        tileSetsTreeView = new TileSetsSubTreeView(primaryStage).getRootItem();
        rootTreeItem.getChildren().addAll(entityTreeView, soundTreeView, tileSetsTreeView);
        rootTreeView = new TreeView<>(rootTreeItem);
        rootTreeView.setOnMouseClicked(event -> new EntityWindow(primaryStage));

        sideView.getChildren().addAll(rootTreeView);
    }

    @Override
    public Node getView() {
        return sideView;
    }
}
