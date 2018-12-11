package authoringInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAuthoringProgram extends Application {

    public static final int SCREEN_WIDTH = 1200;
    public static final int SCREEN_HEIGHT = 700;
    public static final String STYLESHEET = "style.css";
    private static final String GROOVE = "GROOVE";

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(GROOVE);
        View myView = new View(primaryStage);
        var myScene = new Scene(myView.getRootPane(), SCREEN_WIDTH, SCREEN_HEIGHT);
        myScene.getStylesheets().add(STYLESHEET);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }
}

