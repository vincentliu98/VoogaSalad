package gameplay;

public class Phase {
    private Node myStartNode;
    private Node myTerminalNode;
    private Node myCurrentNode;

    public Phase(Node start, Node terminal){
        this.myStartNode = start;
        this.myTerminalNode = terminal;
    }

    public void step(Node node){
        myCurrentNode = node;
        if (node.equals(myTerminalNode)){
            // exit code here
            return;
        }
        node.getOutgoingEdge().setListener();
    }

    public void startTraversal(){
        step(myStartNode);
    }
}