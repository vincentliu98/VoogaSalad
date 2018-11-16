package gameplay;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private int myPlayerID;
    private int myID;
    Map<String, Double> myStats;

    public Entity(int id){
        this.myID = id;
        myStats = new HashMap<>();
    }

    public void setPlayer(int playerID){
        this.myPlayerID = playerID;
    }

    public void addStat(String key, Double value){
        myStats.put(key, value);
    }
}
