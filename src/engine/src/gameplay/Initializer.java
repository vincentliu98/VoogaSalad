package gameplay;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.io.File;

public class Initializer {
    XMLParser myXMLParser;
    Pane myRoot;
    String myFileName;

    public Initializer() {
        myXMLParser = new XMLParser();
        myRoot = new Pane();
    }

    public Initializer(File file) {
        this();
        myFileName = file.getName().substring(0, file.getName().length() - 4);
        myXMLParser.loadFile(file);
        initGameData();
    }

    public Initializer(String xml) {
        this();
        myFileName = "";
        myXMLParser.loadXML(xml);
        initGameData();
    }

    public void initGameData() {
        GameData.setGameData(
                myXMLParser.getDimension(), myXMLParser.getBGMpath(), myXMLParser.getPlayers(), myXMLParser.getEntities(),
                myXMLParser.getEntityPrototypes(), myXMLParser.getTiles(),
                myXMLParser.getPhases(), myXMLParser.getWinCondition(),
                myXMLParser.getNodes(), myXMLParser.getEdges(), myXMLParser.getTurn(), myRoot, this);

        for (Tile tile : GameData.getTiles().values()) {
            tile.setupView();
            myRoot.getChildren().add(tile.getImageView());
        }
        for (Entity entity : GameData.getEntities().values()) {
            entity.setupView();
            myRoot.getChildren().add(entity.getImageView());
        }
        startGame();
    }

    /**
     *  SHOULD NOT BE USED WHEN RUNNING FROM AUTHORING ENV
     */
    public String getFileName() { return myFileName; }

    public Pane getRoot() {
        return myRoot;
    }

    public void keyFilter(KeyEvent ev) {
        GameData.addArgument(ev, new KeyTag(ev.getCode()));
    }

    public void setScreenSize(double screenWidth, double screenHeight) {
        myRoot.setPrefWidth(screenWidth);
        myRoot.setPrefHeight(screenHeight);
        GameData.getTiles().values().forEach(e -> e.adjustViewSize(screenWidth, screenHeight));
        GameData.getEntities().values().forEach(e -> e.adjustViewSize(screenWidth, screenHeight));
        GameData.updateViews();
    }

    public void resetRoot() {
        myRoot.getChildren().clear();
    }

    public void startGame() {
        myXMLParser.getTurn().startPhase();
    }

    public void saveGame() {
        String xmlString = GameData.saveGameData();
    }

    public void stopMusic() { GameData.stopMusic(); }
}