package playing;

import authoringInterface.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPlayer extends Application {

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("VoogaSalad!");
        View myView = new View(primaryStage);
        primaryStage.setScene(new Scene(myView.getRootPane(), SCREEN_WIDTH, SCREEN_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
