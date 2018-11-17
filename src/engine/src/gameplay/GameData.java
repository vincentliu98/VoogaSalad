package gameplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameData {
    private static Map<Integer, Player> PLAYERS;
    private static Map<Integer, Entity> ENTITIES;
    private static Map<Integer, Tile> TILES;
    private static Map<Integer, Phase> PHASES;
    private static Map<Integer, Node> NODES;
    private static Map<Integer, Edge> EDGES;
    private static List<Tag> myArguments;
    private static List<ArgumentListener> myArgumentListeners;

    public static void setGameData(Map<Integer, Player> players, Map<Integer, Entity> entities, Map<Integer, Tile> tiles, Map<Integer, Phase> phases, Map<Integer, Node> nodes, Map<Integer, Edge> edges){
        PLAYERS = players;
        ENTITIES = entities;
        TILES = tiles;
        PHASES = phases;
        NODES = nodes;
        EDGES = edges;
        myArguments = new ArrayList<>();
        myArgumentListeners = new ArrayList<>();
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

    public static Tile getTile(int tileID){
        return TILES.get(tileID);
    }

    public static void addArgument(Tag tag){
        myArguments.add(tag);
        notifyArgumentListeners();
    }

    public static void clearArguments(){
        myArguments.clear();
    }

    public static void addArgumentListener(ArgumentListener argumentListener){
        myArgumentListeners.add(argumentListener);
    }

    public static void removeArgumentListener(ArgumentListener argumentListener){
        myArgumentListeners.remove(argumentListener);
    }

    private static void notifyArgumentListeners(){
        for (ArgumentListener argumentListener : myArgumentListeners){
            argumentListener.hasChanged(myArguments);
        }
    }
}
