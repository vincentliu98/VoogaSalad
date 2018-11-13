package authoringInterface.sidebar;

import authoringInterface.TreeViewSample;

import java.util.ArrayList;

/**
 * A utility class that manages all the items in the SideView
 *
 * @author jl729
 */

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
