package gameplay;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Set;

public class Node {
    private int myPhaseID;
    private int myID;
    private String myExecution; // Groovy code
    private Set<Integer> myOutgoingEdgeIDs;

    public Node(int phaseID, int id, String execution){
        this.myPhaseID = phaseID;
        this.myID = id;
        this.myExecution = execution;
        myOutgoingEdgeIDs = null;
    }

    public void setOutgoingEdges(Set<Integer> edgeIDs){
        myOutgoingEdgeIDs = edgeIDs;
    }

    public int getID(){
        return myID;
    }

    public void execute(){
        int edgeID = 1; // TODO: Erase this from being a default val; should be in myOutgoingEdgeIDs
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("groovy");
        try{

            engine.eval(myExecution);
        } catch (ScriptException e){
            System.out.println("Script exception in Edge");
        }
        GameData.clearArguments();
        // execute Groovy execution that defines which edge to execute (which ID)
        // also must remember to shut off listener
        // GameData.addArgumentListener(GameData.getEdge(edgeID)); FOR NON-TERMINAL
    }
}