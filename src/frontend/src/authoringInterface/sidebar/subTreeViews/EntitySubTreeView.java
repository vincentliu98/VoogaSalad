package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * A list of TreeItem representing EntityClass
 *
 * @author jl729
 */

public class EntitySubTreeView extends SubTreeView {

    public EntitySubTreeView(Stage primaryStage) {
        super("EntityClass", primaryStage);
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("EntityClass" + i);
            getRootItem().getChildren().add(item);
        }
    }

}
