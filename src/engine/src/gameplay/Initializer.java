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
                myXMLParser.getPhases(), myXMLParser.getNodes(), myXMLParser.getEdges(), myXMLParser.getTurn(), myRoot);
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
}