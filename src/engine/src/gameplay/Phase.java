package gameplay;

import java.util.Set;

public class Phase {
    private String myStartNodeName;
    private String myCurrentNodeName;
    private Set<String> myNodeNames;

    public Phase(String startNodeName, Set<String> nodeNames) {
        this.myStartNodeName = startNodeName;
        this.myNodeNames = nodeNames;
    }

    public void step(String nodeName) {
        myCurrentNodeName = nodeName;
        GameData.getNode(myCurrentNodeName).execute();
    }

    public void startTraversal() {
        step(myStartNodeName);
    }

    public String getName() {
        return myStartNodeName;
    }
}