package gameplay;

import groovy.lang.GroovyShell;
import javafx.scene.Group;

import java.util.*;

public class Node {
    private int myPhaseID;
    private int myID;
    private String myExecution; // Groovy code
    private Set<Integer> myOutgoingEdgeIDs;

    public Node(int phaseID, int id, String execution){
        this.myPhaseID = phaseID;
        this.myID = id;
        this.myExecution = execution;
        myOutgoingEdgeIDs = new HashSet<>();
    }

    public void setOutgoingEdges(Set<Integer> edgeIDs){
        myOutgoingEdgeIDs = edgeIDs;
    }

    public int getID(){
        return myID;
    }

    public void execute(){
        try{
            GroovyShell groovyShell = new GroovyShell();
            groovyShell.setVariable("gameDataClass", GameData.class);
            groovyShell.setVariable("edgeClass", Edge.class);
            groovyShell.setVariable("argListenerClass", ArgumentListener.class);
            groovyShell.setVariable("entityClass", Entity.class);
            groovyShell.setVariable("nodeClass", Node.class);
            groovyShell.setVariable("phaseClass", Phase.class);
            groovyShell.setVariable("playerClass", Player.class);
            groovyShell.setVariable("tagClass", Tag.class);
            groovyShell.setVariable("tileClass", Tile.class);
            groovyShell.setVariable("turnClass", Turn.class);
            groovyShell.setVariable("groupClass", Group.class);
            groovyShell.evaluate(myExecution);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}