package gameplay;

import java.util.Map;
import java.util.Set;

public class Player {
    /**
     * We should implement a HUD for this on the second sprint
     */
    private String myName;
    private Map<String, Double> myStats;
    private Set<Integer> myEntityIDs;

    public void addEntity(int entityID) {
        myEntityIDs.add(entityID);
    }

    public void removeEntity(int entityID) {
        myEntityIDs.remove(entityID);
    }

    public void addStat(String key, double value) {
        myStats.put(key, value);
    }

    public boolean isMyEntity(Entity entity) {
        return myEntityIDs.contains(entity.getID());
    }

    public double getValue(String key) {
        return myStats.get(key);
    }

    public Set<Integer> getMyEntities() {
        return myEntityIDs;
    }

    public void clearMyEntities() {
        myEntityIDs.clear();
    }

    public String getName() {
        return myName;
    }
}