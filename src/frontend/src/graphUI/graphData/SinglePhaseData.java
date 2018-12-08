package graphUI.graphData;

import javafx.util.Pair;
import phase.api.GameEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data to be stored as XML:
 * 1. Phase Graph Name on the left --> List<String>
 * 2. For each ChooserPane:
 * 1. Name of the nodes on one ChooserPane --> List<String>
 * 2. Each phase contains X and Y of the node --> Map<String, Pair<Integer, Integer>>
 * 4. Connection between nodes. Map<Pair<String, String>, String(Action Type)>
 * Action type --> String either "OnClick" or "onKeyPress"
 * <p>
 * Load data:
 * 1. Take the names as List<String> and restore ChooserPanes on the left
 * 2. For each ChooserPane:
 * 1. restore all the nodes according to X and Y position and name
 * 2. restore connections between nodes by going through the map and generate edges.
 *
 * @author jl729
 */

public class SinglePhaseData {
    private String phaseName;
    private List<String> nodesName;
    private Map<String, Pair<Double, Double>> nodesPos;
    private Map<Pair<String, String>, GameEvent> nodesConnect;

    public String getPhaseName() {
        return phaseName;
    }

    public List<String> getNodesName() {
        return nodesName;
    }

    public Map<String, Pair<Double, Double>> getNodesPos() {
        return nodesPos;
    }

    public Map<Pair<String, String>, GameEvent> getNodesConnect() {
        return nodesConnect;
    }

    public SinglePhaseData(String phaseName) {
        this.phaseName = phaseName;
        nodesName = new ArrayList<>();
        nodesPos = new HashMap<>();
        nodesConnect = new HashMap<>();
    }

    public SinglePhaseData(String phaseName, List<String> nodesName, Map<String, Pair<Double, Double>> nodesPos,
                           Map<Pair<String, String>, GameEvent> nodesConnect){
        this.phaseName = phaseName;
        this.nodesName = nodesName;
        this.nodesPos = nodesPos;
        this.nodesConnect = nodesConnect;
    }

    public void addNode(String name) {
        if (!nodesName.contains(name)) nodesName.add(name);
    }

    public void addPos(String nodeName, Pair<Double, Double> pair) {
        if (nodesPos.containsKey(nodeName)) {
            nodesPos.replace(nodeName, pair);
        } else {
            nodesPos.put(nodeName, pair);
        }
    }

    public void addConnect(Pair<String, String> pair, GameEvent gameEventType) {
        nodesConnect.put(pair, gameEventType);
    }

    public void removeNode(String nodeName){
        // delete the node first
        // then delete all pairs of connections containing this node
        nodesName.remove(nodeName);
        nodesPos.remove(nodeName);
        for (Pair<String, String> key: nodesConnect.keySet()) {
            var k = key.getKey();
            var v = key.getValue();
            if (k.equals(nodeName)  || v.equals(nodeName)){
                removeConnect(key);
            }
        }
    }

    public void removeConnect(Pair<String, String> pair){
        if (nodesConnect.containsKey(pair)) nodesConnect.remove(pair);
        else nodesConnect.remove(new Pair<>(pair.getValue(), pair.getKey()));
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
