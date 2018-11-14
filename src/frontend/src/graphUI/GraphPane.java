package graphUI;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GraphPane extends PopUpWindow {

    public static final Double WIDTH = 700.0;
    public static final Double HEIGHT = 500.0;
    private Pane myPane;

    public GraphPane(Stage primaryStage) {
        super(primaryStage);
        myPane = new GridPane();
    }

    @Override
    public void showWindow() {
        Scene dialogScene = new Scene(myPane, WIDTH, HEIGHT);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    @Override
    protected void closeWindow() {
        // TODO: 11/13/18 do something with back end
        dialog.close();
    }

}
