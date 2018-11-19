package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.Group;

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
    private static Turn TURN;
    private static Group ROOT;
    private static List<Tag> myArguments;
    private static List<ArgumentListener> myArgumentListeners;

    public static void setGameData(Map<Integer, Player> players, Map<Integer, Entity> entities,
                                   Map<Integer, Tile> tiles, Map<Integer, Phase> phases, Map<Integer, Node> nodes,
                                   Map<Integer, Edge> edges, Turn turn, Group root){
        PLAYERS = players;
        ENTITIES = entities;
        TILES = tiles;
        PHASES = phases;
        NODES = nodes;
        EDGES = edges;
        TURN = turn;
        ROOT = root;
        myArguments = new ArrayList<>();
        myArgumentListeners = new ArrayList<>();
    }

    public static Map<Integer, Entity> getEntities(){ // TODO: Get rid of this
        return ENTITIES;
    }

    public static void addEntity(Entity entity){
        ENTITIES.put(entity.getID(), entity);
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

    public static Turn getTurn(){ return TURN; }

    public static Group getRoot() { return ROOT; }

    public static void addArgument(Tag tag){
        myArguments.add(tag);
        notifyArgumentListeners();
    }

    public static int getNextEntityID(){
        return ENTITIES.size() + 1;
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
            argumentListener.hasChanged();
        }
    }

    public static List<Tag> getArguments(){
        return myArguments;
    }

    public static String saveGameData(){
        String xmlString = "";
        XStream serializer = new XStream(new DomDriver());
        serializeData(serializer, xmlString, PLAYERS);
        serializeData(serializer, xmlString, ENTITIES);
        serializeData(serializer, xmlString, TILES);
        serializeData(serializer, xmlString, PHASES);
        serializeData(serializer, xmlString, NODES);
        serializeData(serializer, xmlString, EDGES);
        xmlString = xmlString + serializer.toXML(TURN);
        return xmlString;
    }

    public static String serializeData(XStream serializer, String xmlString, Map<Integer, ?> dataMap){
        for (Map.Entry<Integer, ?> entry : dataMap.entrySet()){
            xmlString = serializer.toXML(entry.getValue()) + "\n";
        }
        return xmlString;
    }
}
