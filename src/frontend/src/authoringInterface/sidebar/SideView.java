package authoringInterface.sidebar;

import api.SubView;
import authoringInterface.sidebar.subTreeViews.EntitySubTreeView;
import authoringInterface.sidebar.subTreeViews.SoundSubTreeView;
import authoringInterface.sidebar.subTreeViews.TileSetsSubTreeView;
import authoringInterface.spritechoosingwindow.EntityWindow;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SideView implements SubView<StackPane> {
    private final TreeView<String> rootTreeView;
    private final TreeItem<String> rootTreeItem;
    private final TreeItem<String> entityTreeItem;
    private final TreeItem<String> soundTreeItem;
    private final TreeItem<String> tileSetsTreeItem;
    private StackPane sideView;

    public SideView(Stage primaryStage) {
        sideView = new StackPane();
        rootTreeItem = new TreeItem<>("User Settings");
        rootTreeItem.setExpanded(true);
        entityTreeItem = new EntitySubTreeView(primaryStage).getRootItem();
        soundTreeItem = new SoundSubTreeView(primaryStage).getRootItem();
        tileSetsTreeItem = new TileSetsSubTreeView(primaryStage).getRootItem();
        rootTreeItem.getChildren().addAll(entityTreeItem, soundTreeItem, tileSetsTreeItem);
        rootTreeView = new TreeView<>(rootTreeItem);
        rootTreeView.setOnMouseClicked(event -> new EntityWindow(primaryStage));

        rootTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> handle(newValue));

        sideView.getChildren().addAll(rootTreeView);
    }

    private void handle(Object newValue) {


        System.out.println(newValue);
    }

    @Override
    public StackPane getView() {
        return sideView;
    }
}
