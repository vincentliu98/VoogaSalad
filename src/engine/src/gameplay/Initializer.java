package gameplay;

import javafx.scene.layout.Pane;

import java.io.File;

public class Initializer {
    XMLParser myXMLParser;
    Pane myRoot;

    public Initializer(File file){
        myXMLParser = new XMLParser();
        myRoot = new Pane();
        initGameData(file);
    }

    public void initGameData(File file){
        myXMLParser.loadFile(file);
        GameData.setGameData(
            myXMLParser.getDimension(), myXMLParser.getPlayers(), myXMLParser.getEntities(),
            myXMLParser.getEntityPrototypes(), myXMLParser.getTiles(),
            myXMLParser.getPhases(), myXMLParser.getNodes(), myXMLParser.getEdges(), myXMLParser.getTurn(), myRoot);
        for (Tile tile : GameData.getTiles().values()){
            tile.setupView();
            myRoot.getChildren().add(tile.getImageView());
        }
        for (Entity entity : GameData.getEntities().values()){
            entity.setupView();
            myRoot.getChildren().add(entity.getImageView());
        }
        startGame();
    }

    public Pane getRoot(){ return myRoot; }
    public void setScreenSize(double screenWidth, double screenHeight) {
        myRoot.setPrefWidth(screenWidth);
        myRoot.setPrefHeight(screenHeight);
        GameData.getTiles().values().forEach(e -> e.adjustViewSize(screenWidth, screenHeight));
        GameData.getEntities().values().forEach(e -> e.adjustViewSize(screenWidth, screenHeight));
        GameData.updateViews();
    }

    public void startGame(){
        myXMLParser.getTurn().startPhase();
    }

    public void saveGame(){
        String xmlString = GameData.saveGameData();
    }
}