package launchingGame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;

    public static final String STAGE_TITLE = "Game Launcher";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(STAGE_TITLE);
        LauncherWindow myWindow = new LauncherWindow();
        Scene myScene = new Scene(myWindow.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
