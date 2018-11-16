package gameplay;

import java.util.Map;
import java.util.Set;

public class Node {
    private Phase myPhase;
    private int myID;
    private String myExecution; // Groovy code
    private Map<Integer, Edge> myOutgoingEdges;

    public Node(Phase phase, int id, String execution){
        this.myPhase = phase;
        this.myID = id;
        this.myExecution = execution;
        myOutgoingEdges = null;
    }

    public void setOutgoingEdges(Set<Edge> edges){
        for (Edge e : edges){
            myOutgoingEdges.put(e.getID(), e);
        }
    }

    public int getID(){
        return myID;
    }

    public void execute(){
        int edgeID = 1; // TODO: Erase this from being a default val
        // execute Groovy execution that defines which edge to execute (which ID)
        // also must remember to shut off listener
        myOutgoingEdges.get(edgeID).setListener();
    }

}