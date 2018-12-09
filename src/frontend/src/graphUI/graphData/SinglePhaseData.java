package graphUI.graphData;

import javafx.util.Pair;
import phase.api.GameEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data on a single phaseGraph to be stored as XML:
 * 1. phaseGraph Name from the ListView --> phaseName (also the name of the first node)
 * 2. Name of the nodes on one ChooserPane --> List<String>
 * 3. Each phase contains X and Y of the node --> Map<String, Pair<Double, Double>>
 * 4. Connection between nodes. Map<Pair<String, String>, GameEvent>
 *
 * @author jl729
 */

public class SinglePhaseData implements PhaseDataAPI {
    private String phaseName;
    private List<String> nodesName;
    private Map<String, Pair<Double, Double>> nodesPos;
    private Map<Pair<String, String>, GameEvent> nodesConnect;

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

    @Override
    public void addNode(String name) {
        if (!nodesName.contains(name)) nodesName.add(name);
    }

    @Override
    public void addPos(String nodeName, Pair<Double, Double> pair) {
        if (nodesPos.containsKey(nodeName)) {
            nodesPos.replace(nodeName, pair);
        } else {
            nodesPos.put(nodeName, pair);
        }
    }

    @Override
    public void addConnect(Pair<String, String> pair, GameEvent gameEventType) {
        nodesConnect.put(pair, gameEventType);
    }

    @Override
    public void removeNode(String nodeName){
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

    private void removeConnect(Pair<String, String> pair){
        if (nodesConnect.containsKey(pair)) nodesConnect.remove(pair);
        else nodesConnect.remove(new Pair<>(pair.getValue(), pair.getKey()));
    }

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
