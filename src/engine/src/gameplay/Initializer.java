package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.Group;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Initializer {
    XMLParser myXMLParser;
    Group myRoot;

    public Initializer(File file){
        myXMLParser = new XMLParser();
        myRoot = new Group();
        initGameData(file);
    }

    private void initGameData(File file){
        myXMLParser.loadFile(file);
        GameData.setGameData(myXMLParser.getPlayers(), myXMLParser.getEntities(), myXMLParser.getTiles(),
                myXMLParser.getPhases(), myXMLParser.getNodes(), myXMLParser.getEdges(), myXMLParser.getTurn());
        for (Tile tile : myXMLParser.getTiles().values()){
            tile.setImageView();
            myRoot.getChildren().add(tile.getImageView());
        }
        for (Entity entity : myXMLParser.getEntities().values()){
            entity.setImageView();
            myRoot.getChildren().add(entity.getImageView());
        }
        startGame();
    }

    public Group getRoot(){
        return myRoot;
    }

    public void startGame(){
        myXMLParser.getTurn().startPhase();
    }

    public void saveGame(){
        String xmlString = GameData.saveGameData();
    }

    /*public static void main(String[] args){
        //Turn turn = new Turn(1, 1);
        XStream xStream = new XStream(new DomDriver());
        //String x = xStream.toXML(turn);
        //System.out.println(x);
        Set<Integer> edges = new HashSet<>();
        edges.add(1);
        Node n1 = new Node(1, 1, "Class clazz = gd.getClass();\n" +
                "            clazz.getMethod(\"clearArguments\", null).invoke(null, null);\n" +
                "            Edge edge = (Edge) clazz.getMethod(\"getEdge\", Integer.class).invoke(null, 1);\n" +
                "            clazz.getMethod(\"addArgumentListener\", ArgumentListener.class).invoke(null, edge);");
        n1.setOutgoingEdges(edges);
        System.out.println(xStream.toXML(n1));
        Edge edge = new Edge(1, 1, 1, 2, "if (arguments.size() != 1 || !arguments.get(0).getType().equals(Tile.class)){\n" +
                "            answer = false;\n" +
                "        } else {\n" +
                "            Class clazz = gd.getClass();\n" +
                "            Tile tile = (Tile) clazz.getMethod(\"getTile\", Integer.class).invoke(null, arguments.get(0).getID());\n" +
                "            answer = tile.hasNoEntities();\n" +
                "        }");
        System.out.println(xStream.toXML(edge));
    }*/
}