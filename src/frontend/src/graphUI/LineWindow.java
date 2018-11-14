package graphUI;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Pop-up window for choosing two nodes that will be connected by a line
 *
 * @author jl729
 */

public class LineWindow extends PopUpWindow {

    public static final Double WIDTH = 500.0;
    public static final Double HEIGHT = 60.0;
    private TextField nodeOne = new TextField();
    private TextField nodeTwo = new TextField();
    private Label labelOne = new Label("Node 1: ");
    private Label labelTwo = new Label("Node 2: ");
    private HBox root;

    public LineWindow(Stage primaryStage) {
        super(primaryStage);
        root = new HBox();
        root.getChildren().addAll(labelOne, nodeOne, labelTwo, nodeTwo);
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
