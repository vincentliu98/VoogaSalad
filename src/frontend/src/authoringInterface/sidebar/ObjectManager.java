package authoringInterface.sidebar;

import java.util.ArrayList;
import java.util.List;

public class ObjectManager {
    private List<Entity> entityList;

    public ObjectManager() {
        entityList = new ArrayList<>();
    }

    public List<Entity> getEntityList() {
        return entityList;
    }
}
