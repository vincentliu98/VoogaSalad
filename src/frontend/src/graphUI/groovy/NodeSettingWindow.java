package graphUI.groovy;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Pop-up window for adding settings for nodes
 *
 * @author Inchan Hwang
 * @author jl729
 */

public class NodeSettingWindow extends PopUpWindow {

    public static final Double WIDTH = 400.0;
    public static final Double HEIGHT = 400.0;
    private final Button applyBtn;
    private HBox root;

    public NodeSettingWindow(Stage primaryStage) {
        super(primaryStage);
        dialog.setTitle("Node Settings");
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
    public void closeWindow() {
        dialog.close();
    }

}
