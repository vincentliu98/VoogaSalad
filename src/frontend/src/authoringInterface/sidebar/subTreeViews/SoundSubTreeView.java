package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;

public class SoundSubTreeView extends SubTreeView {

    public SoundSubTreeView() {
        super("Sound");
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("Sound" + i);
            getRootItem().getChildren().add(item);
        }
    }

}
