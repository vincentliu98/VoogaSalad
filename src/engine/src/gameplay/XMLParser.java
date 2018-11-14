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

    public Set<Tile> getTiles(){
        NodeList tiles = myDocTree.getElementsByTagName("tiles").item(0).getChildNodes();
        Set<Tile> myTiles = new HashSet<>();
        for (int i = 0; i < tiles.getLength(); i++){
            String currentTile = tiles.item(i).toString();
            Tile tile = (Tile) mySerializer.fromXML(currentTile);
            myTiles.add(tile);
        }
        return myTiles;
    }

    public Set<Entity> getEntities(){
        NodeList entities = myDocTree.getElementsByTagName("entities").item(0).getChildNodes();
        Set<Entity> myEntities = new HashSet<>();
        for (int i = 0; i < entities.getLength(); i++){
            String currentEntity = entities.item(i).toString();
            Entity entity = (Entity) mySerializer.fromXML(currentEntity);
            myEntities.add(entity);
        }
        return myEntities;
    }

    public Set<GlobalData> getGlobalData(){
        NodeList globalData = myDocTree.getElementsByTagName("global").item(0).getChildNodes();
        Set<GlobalData> myGlobalData = new HashSet<>();
        for (int i = 0; i < globalData.getLength(); i++){
            //Element currentGlobalData = (Element) globalData.item(i);
            String currentGlobalData = globalData.item(i).toString();
            GlobalData globalDatum = (GlobalData) mySerializer.fromXML(currentGlobalData);
            myGlobalData.add(globalDatum);
        }
        return myGlobalData;

    }

    public void getPhases(){
        // etc. same process
    }
}
