package authoringInterface.sidebar;

import javafx.scene.control.TreeItem;

abstract class SubTreeView {
    private TreeItem<String> rootItem;

    SubTreeView(String name){
        rootItem = new TreeItem<>(name);
        rootItem.setExpanded(true);
    }

    TreeItem<String> getRootItem(){
        return rootItem;
    }
}
