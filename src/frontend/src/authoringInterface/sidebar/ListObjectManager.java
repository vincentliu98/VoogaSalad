package authoringInterface.sidebar;

import authoringInterface.TreeViewSample;

import java.util.ArrayList;

public class ListObjectManager extends ArrayList<TreeViewSample.ListObject>{

    public ListObjectManager() {
        add(new TreeViewSample.ListObject("O", "Entity", 0));
        add(new TreeViewSample.ListObject("X", "Entity", 1));
        add(new TreeViewSample.ListObject("Default Grid", "Tile", 0));
    }

    @Override
    public boolean add(TreeViewSample.ListObject listObject) {
        return super.add(listObject);
    }
}
