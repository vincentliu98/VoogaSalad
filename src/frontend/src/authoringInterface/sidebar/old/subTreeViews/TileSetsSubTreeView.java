package authoringInterface.sidebar.old.subTreeViews;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * A list of TreeItem representing TileSet
 *
 * @author jl729
 */

public class TileSetsSubTreeView extends SubTreeView {

    public TileSetsSubTreeView(Stage primaryStage) {
        super("TileClass Sets", primaryStage);
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("TileClass Sets" + i);
            getRootItem().getChildren().add(item);
        }
    }
}
