package launching;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import launchingGame.LauncherWindow;

public class GameLauncher extends Application {
    public static final String STYLESHEET_PATH = "/stylesheets/testing.css";
    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;

    public static final String STAGE_TITLE = "Game Launcher";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        String style = getClass().getResource(STYLESHEET_PATH).toExternalForm();

        primaryStage.setTitle(STAGE_TITLE);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        LauncherWindow myWindow = new LauncherWindow(primaryStage);
        Scene myScene = new Scene(myWindow.getView(), SCREEN_WIDTH, SCREEN_HEIGHT);

        myScene.getStylesheets().add(style);

        primaryStage.setScene(myScene);
        primaryStage.show();


    }
}
