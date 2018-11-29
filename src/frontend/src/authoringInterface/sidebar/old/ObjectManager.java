package authoringInterface.sidebar.old;

import authoringInterface.sidebar.treeItemEntries.EditTreeItem;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private List<EditTreeItem> entityList;
    private List<EditTreeItem> tileList;

    public ObjectManager() {
        entityList = new ArrayList<>();
        tileList = new ArrayList<>();
    }

    public List<EditTreeItem> getEntityList() {
        return entityList;
    }

    public List<EditTreeItem> getTileList() {
        return tileList;
    }
}
