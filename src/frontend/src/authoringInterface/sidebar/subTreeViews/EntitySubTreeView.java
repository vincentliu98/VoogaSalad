package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;

public class EntitySubTreeView extends SubTreeView {

    public EntitySubTreeView() {
        super("Entity");
        for (int i = 1; i < 3; i++) {
            TreeItem<String> item = new TreeItem<>("Entity" + i);
            getRootItem().getChildren().add(item);
        }
    }

}
