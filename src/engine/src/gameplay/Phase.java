package gameplay;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Phase {
    private int myID;
    private Node myStartNode;
    private Node myCurrentNode;
    private Map<Integer, Node> myNodes;

    public Phase(int id, Node start, Set<Node> nodes){
        this.myID = id;
        this.myStartNode = start;
        this.myNodes = new HashMap<>();
        for (Node n : nodes){
            myNodes.put(n.getID(), n);
        }
    }

    public void step(Node node){
        myCurrentNode = node;
        myCurrentNode.execute();
    }

    public void startTraversal(){
        step(myStartNode);
    }

}