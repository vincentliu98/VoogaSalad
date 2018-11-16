package gameplay;

import javafx.event.Event;

public class Edge {
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

    public void setListener(){
        // set listener based on myTrigger that calls validity and execution
        if (checkValidity()){
            GameData.getPhase(myPhaseID).step(myEndNodeID);
        } else {
            setListener(); // start over again
        }
    }

    private boolean checkValidity(){
        // execute Groovy guard, returns true if valid
        return true;
    }

    public int getID(){
        return myID;
    }
}