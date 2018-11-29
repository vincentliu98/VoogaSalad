package gameplay;

import javafx.event.Event;
import phase.api.GameEvent;

public class Edge implements ArgumentListener {
    private int myPhaseID;
    private int myStartNodeID;
    private int myEndNodeID;
    private GameEvent myTrigger;
    private String myGuard; // Groovy code

    public Edge(int phaseID, int startNodeID, int endNodeID, String guard) {
        this.myPhaseID = phaseID;
        this.myStartNodeID = startNodeID;
        this.myEndNodeID = endNodeID;
        this.myGuard = guard;
    }

    private boolean checkValidity(){
        if(myGuard.isEmpty()) return false; // false by default ? or true by default ?
        try{
            // arguments are already set by each entities/tiles
            GameData.shell().evaluate(myGuard);
            return (boolean) GameData.shell().getVariable("$return");
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int getMyStartNodeID() { return myStartNodeID; }

    @Override
    public int trigger(Event event) {
        if(myTrigger.matches(event) && checkValidity()) {
            System.out.printf("Transitioning from Node %d to Node %d\n", myStartNodeID, myEndNodeID);
            return myEndNodeID;
        }
        else return DONT_PASS;
    }
}