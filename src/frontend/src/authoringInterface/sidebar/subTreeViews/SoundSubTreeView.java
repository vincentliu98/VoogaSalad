package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

public class SoundSubTreeView extends SubTreeView {

    public SoundSubTreeView(Stage primaryStage) {
        super("Sound", primaryStage);
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("Sound" + i);
            getRootItem().getChildren().add(item);
        }
    }

}
