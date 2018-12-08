package playing;

import authoringInterface.View;
import gameplay.Initializer;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

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
        Scene newScene = new Scene(myInitializer.getRoot(), View.GAME_WIDTH, View.GAME_HEIGHT);
        myStage.setScene(newScene);
        myStage.show();
    }


    public static void main(String[] args) {
        //launch(args);
    }
}
