package gameplay;

import javafx.event.Event;

public class Edge {
    private Phase myPhase;
    private Node myStartNode;
    private Node myEndNode;
    private Event myTrigger;
    private String myGuard; // Groovy code
    private String myExecution; // Groovy code

    public Edge(Node start, Node end, Event trigger, String guard, String execution) {
        this.myStartNode = start;
        this.myEndNode = end;
        this.myTrigger = trigger;
        this.myGuard = guard;
        this.myExecution = execution;
    }

    public void setListener(){
        // set listener based on myTrigger that calls validity and execution
        if (checkValidity()){
            execute();
        } else {
            setListener(); // start over again
        }
    }

    private boolean checkValidity(){
        // execute Groovy guard, returns true if valid
        return true;
    }

    private void execute(){
        // execute Groovy execution
        myPhase.step(myEndNode); // traversing next edge
    }
}

