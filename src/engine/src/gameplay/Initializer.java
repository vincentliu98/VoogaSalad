package gameplay;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import javafx.scene.Group;

import java.io.File;

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
        Turn t2 = (Turn) xStream.fromXML("<gameplay.Turn>\n" +
                "  <myCurrentPhaseID>1</myCurrentPhaseID>\n" +
                "  <myCurrentPlayerID>1</myCurrentPlayerID>\n" +
                "</gameplay.Turn>");
    }*/
}