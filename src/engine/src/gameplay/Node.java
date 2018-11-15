package gameplay;

public class Node {
    private int myID;
    private Edge myOutgoingEdge;

    public Node(int id, Edge edge){
        this.myID = id;
        this.myOutgoingEdge = edge;
    }

    public int getID(){
        return myID;
    }

    public Edge getOutgoingEdge(){
        return myOutgoingEdge;
    }
}