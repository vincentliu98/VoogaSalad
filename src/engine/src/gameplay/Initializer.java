package gameplay;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Initializer {
    XMLParser myXMLParser;
    Communicable myCommunicator;

    public Initializer(File file, Communicable communicator){
        myXMLParser = new XMLParser();
        myCommunicator = communicator;
        initGameData(file);
    }

    private void initGameData(File file){
        myXMLParser.loadFile(file);
        GameData.setGameData(myXMLParser.getPlayers(), myXMLParser.getEntities(), myXMLParser.getTiles(),
                myXMLParser.getPhases(), myXMLParser.getNodes(), myXMLParser.getEdges(), myXMLParser.getTurn());
        for (Tile tile : myXMLParser.getTiles().values()){
            myCommunicator.addNewEntity(new Tag(Tile.class, tile.getID()));
        }
        for (Entity entity : myXMLParser.getEntities().values()){
            myCommunicator.addNewEntity(new Tag(Entity.class, entity.getID()));
        }
        startGame();
    }

    public void startGame(){
        myXMLParser.getTurn().startPhase();
    }

    public void saveGame(){
        String xmlString = GameData.saveGameData();
    }
}