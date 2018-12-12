package authoringInterface.spritechoosingwindow;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EntityWindow {
    private GridPane myPane;

    public EntityWindow(Stage primaryStage) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        initializeGridPane();
        addElementsGridPane();
        Scene dialogScene = new Scene(myPane, 600, 500);
        dialog.setScene(dialogScene);
        dialog.show();
    }

    private GridPane initializeGridPane() {
        myPane = new GridPane();
        var row1 = new RowConstraints();
        row1.setPercentHeight(15);
        var row2 = new RowConstraints();
        row2.setPercentHeight(85);
        var col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        myPane.getColumnConstraints().addAll(col1, col2);
        myPane.getRowConstraints().addAll(row1, row2);
        return myPane;
    }

    private void addElementsGridPane() {
        myPane.add(new Button("Sprite"), 0, 0);
        myPane.add(new VBox(new Label("Image is here")), 0, 1);
        myPane.add(new Label("Other Settings"), 1, 0);
        myPane.add(new VBox(new Label("Player Actions go here")), 1, 1);
    }
}
