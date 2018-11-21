package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.*;

public class GameData {
    private static int GRID_WIDTH, GRID_HEIGHT;
    private static Map<Integer, Player> PLAYERS;
    private static Map<Integer, Entity> ENTITIES;
    private static Map<String, EntityPrototype> ENTITY_PROTOTYPES;
    private static Map<Integer, Tile> TILES;
    private static Map<Integer, Phase> PHASES;
    private static Map<Integer, Node> NODES;
    private static Map<Integer, Edge> EDGES;
    private static Turn TURN;
    private static Pane ROOT;
    private static List<ArgumentListener> myArgumentListeners;

    private static GroovyShell shell;

    public static void setGameData(
        Pair<Integer, Integer> grid_dimension,
        Map<Integer, Player> players, Map<Integer, Entity> entities,
        Map<String, EntityPrototype> entityPrototypes,
        Map<Integer, Tile> tiles, Map<Integer, Phase> phases, Map<Integer, Node> nodes,
        Map<Integer, Edge> edges, Turn turn, Pane root
    ){
        GameData.GRID_WIDTH = grid_dimension.getKey();
        GameData.GRID_HEIGHT = grid_dimension.getValue();

        PLAYERS = players;
        ENTITIES = entities;
        ENTITY_PROTOTYPES = entityPrototypes;
        TILES = tiles;
        PHASES = phases;
        NODES = nodes;
        EDGES = edges;
        TURN = turn;
        ROOT = root;
        myArgumentListeners = new ArrayList<>();

        var shared = new Binding();
        shared.setVariable("GameData", GameData.class);
        shell = new GroovyShell(shared);
    }

    public static GroovyShell shell() { return shell; }

    public static int gridWidth() { return GRID_WIDTH; }

    public static int gridHeight() { return GRID_HEIGHT; }

    public static Map<Integer, Entity> getEntities(){ // TODO: Get rid of this
        return ENTITIES;
    }

    public static Entity createEntity(String entityName, int tileID, int ownerID){
        var nextID = ENTITIES.keySet().stream().max(Comparator.comparingInt(a -> a)).orElse(0)+1;
        var newEntity = ENTITY_PROTOTYPES.get(entityName).build(nextID);
        newEntity.adjustViewSize(ROOT.getWidth(), ROOT.getHeight());
        ENTITIES.put(nextID, newEntity);
        ROOT.getChildren().add(newEntity.getImageView());
        TILES.get(tileID).addEntity(newEntity.getID());
        return newEntity;
    }

    public static void removeEntity(Entity entity) {
        ROOT.getChildren().remove(entity.getImageView());
        ENTITIES.remove(entity.getID());
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

    public static Map<Integer, Tile> getTiles() { return TILES; }
    public static Tile getTile(int tileID){
        return TILES.get(tileID);
    }

    public static int getCurrentPlayerID(){ return TURN.getCurrentPlayerID(); }
    public static void setCurrentPlayerID(int id){ TURN.setPlayer(id); }

    public static Turn getTurn(){ return TURN; }

    public static Pane getRoot() { return ROOT; }

    public static void addArgument(MouseEvent event, ClickTag tag){
        var target = (tag.getType().equals(Tile.class) ? TILES : ENTITIES).get(tag.getID());
        shell.setVariable("$clicked", target);
        notifyArgumentListeners(event);
    }

    public static void addArgument(KeyEvent event, KeyTag tag) { // todo: connect this with the window
        shell.setVariable("$pressed", tag.code());
        notifyArgumentListeners(event);
    }


    public static void updateViews() {
        ENTITIES.values().forEach(Entity::updateView);
        TILES.values().forEach(Tile::updateView);
    }

    public static int getNextEntityID(){
        return ENTITIES.size() + 1;
    }

    public static void addArgumentListener(ArgumentListener argumentListener){
        myArgumentListeners.add(argumentListener);
    }

    public static void clearArgumentListeners() { myArgumentListeners.clear(); }

    // I really liked your way of having everything pipelined and tried to keep it,
    // but the consequent execution() from the destination node
    // clears/reinitialize the ArgumentListeners. That leads to ConcurrentModificationException
    // So I had to explicitly separate the validity check and execution
    private static void notifyArgumentListeners(Event event){
        var destination = -1;
        // at most one of the listeners should pass
        for (ArgumentListener argumentListener : myArgumentListeners){
            var dest = argumentListener.trigger(event);
            if(dest != ArgumentListener.DONT_PASS) destination = dest;
        }
        if(destination != ArgumentListener.DONT_PASS) NODES.get(destination).execute();
    }

    public static Collection<Edge> getEdges() { return EDGES.values(); }

    public static String saveGameData(){
        String xmlString = "";
        XStream serializer = new XStream(new DomDriver());
        serializeData(serializer, xmlString, PLAYERS);
        serializeData(serializer, xmlString, ENTITIES);
        serializeData(serializer, xmlString, ENTITY_PROTOTYPES);
        serializeData(serializer, xmlString, TILES);
        serializeData(serializer, xmlString, PHASES);
        serializeData(serializer, xmlString, NODES);
        serializeData(serializer, xmlString, EDGES);
        xmlString = xmlString + serializer.toXML(TURN);
        return xmlString;
    }

    public static String serializeData(XStream serializer, String xmlString, Map<?, ?> dataMap){
        for (Map.Entry<?, ?> entry : dataMap.entrySet()){
            xmlString = serializer.toXML(entry.getValue()) + "\n";
        }
        return xmlString;
    }
}
