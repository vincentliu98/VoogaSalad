package graphUI.groovy;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Pop-up window for adding settings for nodes
 *
 * @author jl729
 */

public class NodeSettingWindow extends PopUpWindow {

    public static final Double WIDTH = 500.0;
    public static final Double HEIGHT = 60.0;
    private final Button applyBtn;
    private HBox root;

    public NodeSettingWindow(Stage primaryStage) {
        super(primaryStage);
        // TODO: 11/14/18 Title not working
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
    protected void closeWindow() {
        dialog.close();
    }

}
