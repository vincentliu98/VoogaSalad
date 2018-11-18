package gameplay;

import javafx.event.Event;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;


public class Edge implements ArgumentListener {
    private int myID;
    private int myPhaseID;
    private int myStartNodeID;
    private int myEndNodeID;
    //private Event myTrigger;
    private String myGuard; // Groovy code

    public Edge(int phaseID, int id, int startNodeID, int endNodeID, String guard) {
        this.myPhaseID = phaseID;
        this.myID = id;
        this.myStartNodeID = startNodeID;
        this.myEndNodeID = endNodeID;
        this.myGuard = guard;
    }

    private boolean checkValidity(List<Tag> arguments){
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        engine.put("arguments", arguments);
        engine.put("GameData", new GameData());
        try{
            engine.eval(myGuard);
            return (boolean) engine.get("answer");
        } catch (ScriptException e){
            System.out.println("Script exception in Edge");
        }
        return false;
        // execute Groovy guard, returns true if valid
        // include check for size, type, etc.
        /* PERFORM SOMETHING LIKE THIS:
            if (tag.getType().equals(Entity.class)){
                return GameData.getEntity(tag.getID());
            } else { // Tile.class
                return GameData.getEntity(tag.getID());
            }
         */
    }

    public int getID(){
        return myID;
    }

    @Override
    public void hasChanged() {
        if (checkValidity(GameData.getArguments())){
            GameData.removeArgumentListener(this);
            GameData.getNode(myEndNodeID).execute();
        } else {
            GameData.clearArguments();
        }
    }
}