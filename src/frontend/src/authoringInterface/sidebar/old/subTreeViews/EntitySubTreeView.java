package authoringInterface.sidebar.old.subTreeViews;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * A list of TreeItem representing GameObjectClass
 *
 * @author jl729
 */

public class EntitySubTreeView extends SubTreeView {

    public EntitySubTreeView(Stage primaryStage) {
        super("GameObjectClass", primaryStage);
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("GameObjectClass" + i);
            getRootItem().getChildren().add(item);
        }
    }

}
