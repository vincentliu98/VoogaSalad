package authoringInterface.sidebar;

import java.util.ArrayList;

/**
 * A utility class that manages all the items in the SideView.
 * These items only appear on UI but do not represent real objects
 *
 * @author jl729
 */

public class ListObjectManager extends ArrayList<SideView.ListObject> {

    public ListObjectManager() {
        add(new SideView.ListObject("O", "EntityClass", 0));
        add(new SideView.ListObject("X", "EntityClass", 1));
        add(new SideView.ListObject("Default Grid", "TileClass", 0));
    }

    @Override
    public boolean add(SideView.ListObject listObject) {
        return super.add(listObject);
    }
}
