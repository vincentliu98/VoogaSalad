package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * A list of TreeItem representing Entity
 *
 * @author jl729
 */

public class EntitySubTreeView extends SubTreeView {

    public EntitySubTreeView(Stage primaryStage) {
        super("Entity", primaryStage);
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("Entity" + i);
            getRootItem().getChildren().add(item);
        }
    }

}
