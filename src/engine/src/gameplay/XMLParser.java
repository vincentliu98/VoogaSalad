package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import grids.Point;
import grids.PointImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XMLParser {
    Document myDocTree;
    private DocumentBuilder myDocumentBuilder;
    private XStream mySerializer;

    public XMLParser(){
        mySerializer = new XStream(new DomDriver());
    }

    public void loadFile(File file){
        try {
            myDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            myDocTree = myDocumentBuilder.parse(file);
            myDocTree.getDocumentElement().normalize();
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
        }
        catch (Exception e){ }
    }

    public Point getDimension() {
        return new PointImpl(Integer.parseInt(myDocTree.getElementsByTagName("grid-width").item(0).getTextContent()),
                             Integer.parseInt(myDocTree.getElementsByTagName("grid-height").item(0).getTextContent()));
    }

    public Map<Integer, Player> getPlayers(){
        NodeList players = myDocTree.getElementsByTagName("gameplay.Player");
        Map<Integer, Player> myPlayers = new HashMap<>();
        for (int i = 0; i < players.getLength(); i++){
            String currentPlayer = nodeToString(players.item(i));
            Player player = (Player) mySerializer.fromXML(currentPlayer);
            myPlayers.put(player.getID(), player);
        }
        return myPlayers;
    }

    public Map<Integer, Entity> getEntities(){
        NodeList entities = myDocTree.getElementsByTagName("gameplay.Entity");
        Map<Integer, Entity> myEntities = new HashMap<>();
        for (int i = 0; i < entities.getLength(); i++){
            String currentEntity = nodeToString(entities.item(i));
            Entity entity = (Entity) mySerializer.fromXML(currentEntity);
            myEntities.put(entity.getID(), entity);
        }
        return myEntities;
    }

    public Map<String, EntityPrototype> getEntityPrototypes(){
        NodeList entities = myDocTree.getElementsByTagName("gameplay.EntityPrototype");
        Map<String, EntityPrototype> myEntities = new HashMap<>();
        for (int i = 0; i < entities.getLength(); i++){
            String currentEntity = nodeToString(entities.item(i));
            EntityPrototype entity = (EntityPrototype) mySerializer.fromXML(currentEntity);
            myEntities.put(entity.name(), entity);
        }
        return myEntities;
    }

    public Map<Integer, Tile> getTiles(){
        NodeList tiles = myDocTree.getElementsByTagName("gameplay.Tile");
        Map<Integer, Tile> myTiles = new HashMap<>();
        for (int i = 0; i < tiles.getLength(); i++){
            String currentTile = nodeToString(tiles.item(i));
            Tile tile = (Tile) mySerializer.fromXML(currentTile);
            myTiles.put(tile.getID(), tile);
        }
        return myTiles;
    }

    public Map<Integer, Phase> getPhases(){
        NodeList phases = myDocTree.getElementsByTagName("gameplay.Phase");
        Map<Integer, Phase> myPhases = new HashMap<>();
        for (int i = 0; i < phases.getLength(); i++){
            String currentPhase = nodeToString(phases.item(i));
            Phase phase = (Phase) mySerializer.fromXML(currentPhase);
            myPhases.put(phase.getID(), phase);
        }
        return myPhases;
    }

    public Map<Integer, Node> getNodes(){
        NodeList nodes = myDocTree.getElementsByTagName("gameplay.Node");
        Map<Integer, Node> myNodes = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); i++){
            String currentNode = nodeToString(nodes.item(i));
            Node node = (Node) mySerializer.fromXML(currentNode);
            myNodes.put(node.getID(), node);
        }
        return myNodes;
    }

    public Set<Edge> getEdges(){
        NodeList edges = myDocTree.getElementsByTagName("gameplay.Edge");
        Set<Edge> myEdges = new HashSet<>();
        for (int i = 0; i < edges.getLength(); i++){
            String currentEdge = nodeToString(edges.item(i));
            Edge edge = (Edge) mySerializer.fromXML(currentEdge);
            myEdges.add(edge);
        }
        return myEdges;
    }

    public Turn getTurn(){
        NodeList turns = myDocTree.getElementsByTagName("gameplay.Turn");
        String currentTurn = nodeToString(turns.item(0)); // only one Turn per game
        Turn turn = (Turn) mySerializer.fromXML(currentTurn);
        return turn;
    }

    /**
     * Taken from https://stackoverflow.com/questions/4412848/xml-node-to-string-in-java
     */
    private static String nodeToString(org.w3c.dom.Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return sw.toString();
    }

}