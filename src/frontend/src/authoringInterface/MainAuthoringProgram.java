package authoringInterface;

import authoringInterface.editor.EmptySkeleton;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainAuthoringProgram extends Application {
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
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("VoogaSalad!");
        primaryStage.setScene(new Scene((Parent) new EmptySkeleton().getView(), 1200, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}