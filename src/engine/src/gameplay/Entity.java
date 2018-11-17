package gameplay;

import javafx.event.Event;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class Entity implements EventHandler {
    private int myPlayerID;
    private int myID;
    Map<String, Double> myStats;
    private String myImagePath;

    public Entity(int id){
        this.myID = id;
        myStats = new HashMap<>();
        myImagePath = "";
    }

    public void setPlayer(int playerID){
        this.myPlayerID = playerID;
    }

    public void setImagePath(String imagePath) {
        this.myImagePath = imagePath;
    }

    public void addStat(String key, Double value){
        myStats.put(key, value);
    }

    public int getID(){
        return myID;
    }

    @Override
    public void handle(Event event) {
        GameData.addArgument(new Tag(Entity.class, myID));
    }
}
