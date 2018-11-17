package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

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

    public Map<Integer, Player> getPlayers(){
        NodeList players = myDocTree.getElementsByTagName("players").item(0).getChildNodes();
        Map<Integer, Player> myPlayers = new HashMap<>();
        for (int i = 0; i < players.getLength(); i++){
            String currentPlayer = players.item(i).toString();
            Player player = (Player) mySerializer.fromXML(currentPlayer);
            myPlayers.put(player.getID(), player);
        }
        return myPlayers;
    }

    public Map<Integer, Entity> getEntities(){
        NodeList entities = myDocTree.getElementsByTagName("entities").item(0).getChildNodes();
        Map<Integer, Entity> myEntities = new HashMap<>();
        for (int i = 0; i < entities.getLength(); i++){
            String currentEntity = entities.item(i).toString();
            Entity entity = (Entity) mySerializer.fromXML(currentEntity);
            myEntities.put(entity.getID(), entity);
        }
        return myEntities;
    }

    public Map<Integer, Tile> getTiles(){
        NodeList tiles = myDocTree.getElementsByTagName("tiles").item(0).getChildNodes();
        Map<Integer, Tile> myTiles = new HashMap<>();
        for (int i = 0; i < tiles.getLength(); i++){
            String currentTile = tiles.item(i).toString();
            Tile tile = (Tile) mySerializer.fromXML(currentTile);
            myTiles.put(tile.getID(), tile);
        }
        return myTiles;
    }

    public Map<Integer, Phase> getPhases(){
        NodeList phases = myDocTree.getElementsByTagName("phases").item(0).getChildNodes();
        Map<Integer, Phase> myPhases = new HashMap<>();
        for (int i = 0; i < phases.getLength(); i++){
            String currentPhase = phases.item(i).toString();
            Phase phase = (Phase) mySerializer.fromXML(currentPhase);
            myPhases.put(phase.getID(), phase);
        }
        return myPhases;
    }

    public Map<Integer, Node> getNodes(){
        NodeList nodes = myDocTree.getElementsByTagName("nodes").item(0).getChildNodes();
        Map<Integer, Node> myNodes = new HashMap<>();
        for (int i = 0; i < nodes.getLength(); i++){
            String currentNode = nodes.item(i).toString();
            Node node = (Node) mySerializer.fromXML(currentNode);
            myNodes.put(node.getID(), node);
        }
        return myNodes;
    }

    public Map<Integer, Edge> getEdges(){
        NodeList edges = myDocTree.getElementsByTagName("edges").item(0).getChildNodes();
        Map<Integer, Edge> myEdges = new HashMap<>();
        for (int i = 0; i < edges.getLength(); i++){
            String currentEdge = edges.item(i).toString();
            Edge edge = (Edge) mySerializer.fromXML(currentEdge);
            myEdges.put(edge.getID(), edge);
        }
        return myEdges;
    }

    public Turn getTurn(){
        NodeList turns = myDocTree.getElementsByTagName("turn").item(0).getChildNodes();
        String currentTurn = turns.item(0).toString(); // only one Turn per game
        Turn turn = (Turn) mySerializer.fromXML(currentTurn);
        return turn;
    }
}