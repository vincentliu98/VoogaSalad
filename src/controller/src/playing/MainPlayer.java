package playing;

import authoringInterface.View;
import gameplay.GameData;
import gameplay.Initializer;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import social.User;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Optional;

public class MainPlayer {


    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;

    private Initializer myInitializer;
    private Stage myStage;
    private User myUser;
    private File myFile;
    private String myReferencePath;

    public MainPlayer(User user, String referencePath) {
        myUser = user;
        myReferencePath = referencePath;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Start Game");
        ButtonType loadButton = new ButtonType("Continue");
        ButtonType newGameButton = new ButtonType("New Game");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(loadButton, newGameButton, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == loadButton) {
            if (myUser != null ){
                String xmlString = myUser.getGameState(myReferencePath.substring(0, myReferencePath.length() - 4));
                if (xmlString.equals("")){
                    myFile = getNewGameFile();
                } else {
                    try {
                        myFile = new File(getClass().getClassLoader().getResource("GameProgress.xml").getFile());
                        FileWriter fileWriter = new FileWriter(myFile);
                        fileWriter.write(xmlString);
                        fileWriter.close();
                    } catch (Exception e){ }
                }
            } else {
                myFile = getNewGameFile();
            }
            launchGame();
        } else if (result.get() == newGameButton) {
            myFile = getNewGameFile();
            launchGame();
        }
    }

    private File getNewGameFile(){
        return new File(getClass().getClassLoader().getResource(myReferencePath).getFile());
    }

    public void launchGame(){
        myInitializer = new Initializer(myFile);
        myStage = new Stage();
        myInitializer.setScreenSize(700, 500);
        Scene newScene = new Scene(myInitializer.getRoot(), View.GAME_WIDTH, View.GAME_HEIGHT + 50);
        Button saveButton = new Button("Save state");
        saveButton.setLayoutY(View.GAME_HEIGHT);
        saveButton.setMinHeight(50);
        saveButton.setMinWidth(View.GAME_WIDTH);
        saveButton.setOnMouseClicked(e -> {
            try{
                myUser.setGameState(myReferencePath, GameData.saveGameData());
            } catch (Exception ex){ }
        });
        myInitializer.getRoot().getChildren().add(saveButton);
        myStage.setScene(newScene);
        myStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
    }
}
