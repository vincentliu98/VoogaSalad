package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import grids.Point;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.event.Event;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.*;

public class GameData {
    static int GRID_WIDTH, GRID_HEIGHT;
    static Map<Integer, Player> PLAYERS;
    static Map<Integer, Entity> ENTITIES;
    static Map<String, EntityPrototype> ENTITY_PROTOTYPES;
    static Map<Integer, Tile> TILES;
    static Map<Integer, Phase> PHASES;
    static Map<Integer, Node> NODES;
    static Set<Edge> EDGES;
    static Turn TURN;
    static Pane ROOT;
    static List<ArgumentListener> myArgumentListeners;
    static Initializer myInitializer;

    static GroovyShell shell;

    public static void setGameData(
        Point grid_dimension,
        Map<Integer, Player> players, Map<Integer, Entity> entities,
        Map<String, EntityPrototype> entityPrototypes,
        Map<Integer, Tile> tiles, Map<Integer, Phase> phases, Map<Integer, Node> nodes,
        Set<Edge> edges, Turn turn, Pane root, Initializer initializer
    ){
        GameData.GRID_WIDTH = grid_dimension.getX();
        GameData.GRID_HEIGHT = grid_dimension.getY();

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
        myInitializer = initializer;

        var shared = new Binding();
        shared.setVariable("GameMethods", GameMethods.class);
        shell = new GroovyShell(shared);

        printMaps(TILES);
    }

    public static void printMaps(Map<?, ?> myMap){
        for (Map.Entry k : myMap.entrySet()){
            System.out.println(k);
        }
    }

    public static GroovyShell shell() { return shell; }

    public static Map<Integer, Entity> getEntities() { return ENTITIES; }

    public static Phase getPhase(int phaseID){
        return PHASES.get(phaseID);
    }
    public static Node getNode(int nodeID){
        return NODES.get(nodeID);
    }
    public static Map<Integer, Tile> getTiles() { return TILES; }

    public static Player getPlayer(int playerID){
        return PLAYERS.get(playerID);
    }

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

    public static Turn getTurn(){ return TURN; }

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

    public static Collection<Edge> getEdges() { return EDGES; }

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

    public static String serializeData(XStream serializer, String xmlString, Set<?> dataSet){
        for (var entry : dataSet){
            xmlString = xmlString + serializer.toXML(entry) + "\n";
        }
        return xmlString;
    }

    public static String serializeData(XStream serializer, String xmlString, Map<?, ?> dataMap){
        for (Map.Entry<?, ?> entry : dataMap.entrySet()){
            xmlString = xmlString + serializer.toXML(entry.getValue()) + "\n";
        }
        return xmlString;
    }

    public static void restartGame(){
        myInitializer.resetRoot();
        myInitializer.initGameData();
    }

}
