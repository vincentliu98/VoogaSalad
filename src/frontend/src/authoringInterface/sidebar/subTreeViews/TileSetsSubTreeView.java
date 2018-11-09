package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;

public class TileSetsSubTreeView extends SubTreeView {

    public TileSetsSubTreeView() {
        super("Tile Sets");
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("Tile Sets" + i);
            getRootItem().getChildren().add(item);
        }
    }
}
