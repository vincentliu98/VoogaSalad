package graphUI;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Not used yet.
 *
 * @author jl729
 */

public class GraphPane extends PopUpWindow {

    public static final Double WIDTH = 700.0;
    public static final Double HEIGHT = 500.0;
    private Group root;

    public GraphPane(Stage primaryStage) {
        super(primaryStage);
        root = new Group();
        showWindow();
    }

    @Override
    public void showWindow() {
        Scene dialogScene = new Scene(root, WIDTH, HEIGHT);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    protected void closeWindow() {
        // TODO: 11/13/18 do something with back end
        dialog.close();
    }

}
