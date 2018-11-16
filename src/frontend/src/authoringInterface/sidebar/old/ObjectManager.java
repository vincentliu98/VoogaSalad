package authoringInterface.sidebar.old;

import authoringInterface.sidebar.EditTreeItem;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private List<EditTreeItem> entityList;

    public ObjectManager() {
        entityList = new ArrayList<>();
    }

    public List<EditTreeItem> getEntityList() {
        return entityList;
    }
}
