package graphUI.phase;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Pop-up window for adding settings for edges
 *
 * @author jl729
 */

public class EdgeSettingWindow extends PopUpWindow {
    public static final Double WIDTH = 500.0;
    public static final Double HEIGHT = 60.0;
    private final Button applyBtn;
    private HBox root;

    public EdgeSettingWindow(Stage primaryStage) {
        super(primaryStage);
        dialog.setTitle("Edge Setting");

        root = new HBox();
        applyBtn = new Button("Apply");
        applyBtn.setOnMouseClicked(e -> closeWindow());
        root.getChildren().addAll(applyBtn);
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
        dialog.close();
    }

}
