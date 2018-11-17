package gameplay;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Player {
    int myID;
    Map<String, Double> myStats;
    Set<Integer> myEntityIDs;

    public Player(int id){
        this.myID = id;
        myStats = new HashMap<>();
        myEntityIDs = new HashSet<>();
    }

    public void addEntity(int entityID){
        myEntityIDs.add(entityID);
    }

    public void removeEntity(int entityID){
        myEntityIDs.remove(entityID);
    }

    public void addStat(String key, double value){
        myStats.put(key, value);
    }

    public double getValue(String key){
        return myStats.get(key);
    }

    public int getID(){
        return myID;
    }
}