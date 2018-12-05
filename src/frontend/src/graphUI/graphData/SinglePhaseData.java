package graphUI.graphData;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data to be stored as XML:
 *  1. Phase Graph Name on the left --> List<String>
 *  2. For each ChooserPane:
 *      1. Name of the nodes on one ChooserPane --> List<String>
 *      2. Each phase contains X and Y of the node --> Map<String, Pair<Integer, Integer>>
 *      4. Connection between nodes. Map<Pair<String, String>, String(Action Type)>
 *         Action type --> String either "OnClick" or "onKeyPress"
 *
 * Load data:
 *  1. Take the names as List<String> and restore ChooserPanes on the left
 *  2. For each ChooserPane:
 *      1. restore all the nodes according to X and Y position and name
 *      2. restore connections between nodes by going through the map and generate edges.
 */

public class SinglePhaseData {
    private String phaseName;
    private List<String> nodesName;
    private Map<String, Pair<Double, Double>> nodesPos;
    enum ActionType {onClick, onKeyPress}
    private Map<Pair<String, String>, ActionType> nodesConnect;

    public SinglePhaseData(String phaseName) {
        this.phaseName = phaseName;
        nodesName = new ArrayList<>();
        nodesPos = new HashMap<>();
        nodesConnect = new HashMap<>();
    }

    public void addNode(String name){
        nodesName.add(name);
    }

    public void addPos(String nodeName, Pair<Double, Double> pair){
        nodesPos.put(nodeName, pair);
    }

    @Override
    public String toString() {
        return "SinglePhaseData{" +
                "phaseName='" + phaseName + '\'' +
                ", nodesName=" + nodesName +
                ", nodesPos=" + nodesPos +
                ", nodesConnect=" + nodesConnect +
                '}';
    }
}
