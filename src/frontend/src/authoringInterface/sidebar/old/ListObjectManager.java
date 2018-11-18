package authoringInterface.sidebar.old;

import java.util.ArrayList;

/**
 * A utility class that manages all the items in the SideView.
 * These items only appear on UI but do not represent real objects
 *
 * @author jl729
 */

public class ListObjectManager extends ArrayList<OldSideView.ListObject> {

    public ListObjectManager() {
        add(new OldSideView.ListObject("O", "Entity", 0));
        add(new OldSideView.ListObject("X", "Entity", 1));
        add(new OldSideView.ListObject("Default Grid", "Tile", 0));
    }

    @Override
    public boolean add(OldSideView.ListObject listObject) {
        return super.add(listObject);
    }
}
