package playing;

import authoringInterface.View;
import gameplay.GameData;
import gameplay.Initializer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class MainPlayer {


    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;

    private Initializer myInitializer;
    private Stage myStage;

    public MainPlayer(){

    }


    public void launchGame(String gameName){
        myInitializer = new Initializer(new File(getClass().getClassLoader().getResource(gameName).getFile()));
        myStage = new Stage();
        myInitializer.setScreenSize(700, 500);
        Scene newScene = new Scene(myInitializer.getRoot(), View.GAME_WIDTH, View.GAME_HEIGHT + 50);
        Button saveButton = new Button("Save state");
        saveButton.setLayoutY(View.GAME_HEIGHT);
        saveButton.setMinHeight(50);
        saveButton.setMinWidth(View.GAME_WIDTH);
        saveButton.setOnMouseClicked(e -> {
            String fileContent = GameData.saveGameData();
            try{
                FileWriter fileWriter = new FileWriter("c:/temp/samplefile2.txt");
                fileWriter.write(fileContent);
                fileWriter.close();
                GameData.saveGameData(); // TODO: Add to User's 
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
