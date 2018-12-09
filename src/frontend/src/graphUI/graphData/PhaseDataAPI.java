package graphUI.graphData;

import javafx.util.Pair;
import phase.api.GameEvent;

public interface PhaseDataAPI {

    /**
     * Add the new nodes into the list nodesName
     * @param name
     */
    void addNode(String name);

    /**
     * Add the new node's position to the map nodesPos or update existing node's position
     * @param nodeName
     * @param pair
     */
    void addPos(String nodeName, Pair<Double, Double> pair);

    /**
     * Add connections between nodes into the map nodesConnect
     * @param pair
     * @param gameEventType
     */
    void addConnect(Pair<String, String> pair, GameEvent gameEventType);

    /**
     * Delete the node and all pairs of connections containing this node
     * @param nodeName
     */
    void removeNode(String nodeName);
}
