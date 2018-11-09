package authoringInterface.sidebar.subTreeViews;

import javafx.scene.control.TreeItem;

abstract class SubTreeView {
    private TreeItem<String> rootItem;

    SubTreeView(String name){
        rootItem = new TreeItem<>(name);
        rootItem.setExpanded(true);
    }

    public TreeItem<String> getRootItem(){
        return rootItem;
    }
}
