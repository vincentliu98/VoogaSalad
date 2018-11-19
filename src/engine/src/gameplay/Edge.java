package gameplay;

import groovy.lang.GroovyShell;
import java.util.List;

public class Edge implements ArgumentListener {
    private int myID;
    private int myPhaseID;
    private int myStartNodeID;
    private int myEndNodeID;
    //private Event myTrigger;
    private String myGuard; // Groovy code

    public Edge(int phaseID, int id, int startNodeID, int endNodeID, String guard) {
        this.myPhaseID = phaseID;
        this.myID = id;
        this.myStartNodeID = startNodeID;
        this.myEndNodeID = endNodeID;
        this.myGuard = guard;
    }

    private boolean checkValidity(List<Tag> arguments){
        try{
            GroovyShell groovyShell = new GroovyShell();
            groovyShell.setVariable("arguments", arguments);
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
            groovyShell.evaluate(myGuard);
            return (boolean) groovyShell.getVariable("answer");
        } catch (Exception e){
            System.out.println("Script exception in Edge");
        }
        return false;
    }

    public int getID(){
        return myID;
    }

    @Override
    public void hasChanged() {
        if (checkValidity(GameData.getArguments())){
            GameData.removeArgumentListener(this);
            GameData.getNode(myEndNodeID).execute();
        } else {
            GameData.clearArguments();
        }
    }
}