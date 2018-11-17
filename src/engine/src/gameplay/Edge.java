package gameplay;

import javafx.event.Event;

import java.util.List;

public class Edge implements ArgumentListener {
    private int myID;
    private int myPhaseID;
    private int myStartNodeID;
    private int myEndNodeID;
    private Event myTrigger;
    private String myGuard; // Groovy code

    public Edge(int phaseID, int id, int startNodeID, int endNodeID, Event trigger, String guard) {
        this.myPhaseID = phaseID;
        this.myID = id;
        this.myStartNodeID = startNodeID;
        this.myEndNodeID = endNodeID;
        this.myTrigger = trigger;
        this.myGuard = guard;
    }

    private boolean checkValidity(List<Tag> arguments){
        // execute Groovy guard, returns true if valid
        // include check for size, type, etc.
        /* PERFORM SOMETHING LIKE THIS:
            if (tag.getType().equals(Entity.class)){
                return GameData.getEntity(tag.getID());
            } else { // Tile.class
                return GameData.getEntity(tag.getID());
            }
         */
        return true;
    }

    public int getID(){
        return myID;
    }

    @Override
    public void hasChanged(List<Tag> arguments) {
        if (checkValidity(arguments)){
            GameData.removeArgumentListener(this);
            GameData.getNode(myEndNodeID).execute();
        } else {
            GameData.clearArguments();
        }
    }
}