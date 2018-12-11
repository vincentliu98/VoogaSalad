import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class SwitchingPaneTest extends Application {
    private GridPane testPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        testPane = new GridPane();

        var col1 = new ColumnConstraints(100);

        testPane.getColumnConstraints().addAll(col1);

        var leftCol = new VBox(10);

        var button1 = new Button("button1");
        var button2 = new Button("button2");

        leftCol.getChildren().addAll(button1, button2);

        testPane.add(leftCol, 0, 0);

        var pane1 = new Pane();
        pane1.getChildren().add(new Rectangle(100, 100, Color.BLACK));

        var pane2 = new Pane();
        pane2.getChildren().add(new Rectangle(100, 100, Color.RED));

        button1.setOnAction(e -> {
            clearRightPane();
            testPane.add(pane1, 1, 0);
        });

        button2.setOnAction(e -> {
            clearRightPane();
            testPane.add(pane2, 1, 0);
        });


        var myScene = new Scene(testPane, 300, 300);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private void clearRightPane() {
        var toRemove = new ArrayList<Node>();
        for (var node : testPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == 1) {
                toRemove.add(node);
            }
        }
        testPane.getChildren().removeAll(toRemove);
    }
}
