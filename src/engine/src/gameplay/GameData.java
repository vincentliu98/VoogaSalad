package gameplay;

import java.util.Map;

public class GameData {
    static Map<Integer, Player> PLAYERS;
    static Map<Integer, Entity> ENTITIES;
    static Map<Integer, Phase> PHASES;
    static Map<Integer, Node> NODES;
    static Map<Integer, Edge> EDGES;

    public static void setGameData(Map<Integer, Player> players, Map<Integer, Entity> entities, Map<Integer, Phase> phases, Map<Integer, Node> nodes, Map<Integer, Edge> edges){
        PLAYERS = players;
        ENTITIES = entities;
        PHASES = phases;
        NODES = nodes;
        EDGES = edges;
    }

    public static Player getPlayer(int playerID){
        return PLAYERS.get(playerID);
    }

    public static Entity getEntity(int entityID){
        return ENTITIES.get(entityID);
    }

    public static Phase getPhase(int phaseID){
        return PHASES.get(phaseID);
    }

    public static Node getNode(int nodeID){
        return NODES.get(nodeID);
    }

    public static Edge getEdge(int edgeID){
        return EDGES.get(edgeID);
    }
}
