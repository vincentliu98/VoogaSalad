package gameplay;

import javafx.event.Event;

public class Edge {
    private int myID;
    private Phase myPhase;
    private Node myStartNode;
    private Node myEndNode;
    private Event myTrigger;
    private String myGuard; // Groovy code

    public Edge(Phase phase, int id, Node start, Node end, Event trigger, String guard) {
        this.myPhase = phase;
        this.myID = id;
        this.myStartNode = start;
        this.myEndNode = end;
        this.myTrigger = trigger;
        this.myGuard = guard;
    }

    public void setListener(){
        // set listener based on myTrigger that calls validity and execution
        if (checkValidity()){
            myPhase.step(myEndNode);
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

