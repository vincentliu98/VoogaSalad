package gameplay;

import java.util.Set;

public class Phase {
    private int myID;
    private int myStartNodeID;
    private int myCurrentNodeID;
    private Set<Integer> myNodeIDs;

    public Phase(int id, int startNodeID, Set<Integer> nodeIDs){
        this.myID = id;
        this.myStartNodeID = startNodeID;
        this.myNodeIDs = nodeIDs;
    }

    public void step(int nodeID){
        myCurrentNodeID = nodeID;
        GameData.getNode(myCurrentNodeID).execute();
    }

    public void startTraversal(){
        step(myStartNodeID);
    }

    public int getID(){
        return myID;
    }
}