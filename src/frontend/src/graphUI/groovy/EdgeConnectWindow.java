package graphUI.groovy;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import groovy.api.Ports;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Pop-up window for choosing two nodes that will be connected by a line
 *
 * @author jl729
 */

public class EdgeConnectWindow extends PopUpWindow {

    public static final Double WIDTH = 200.0;
    public static final Double HEIGHT = 400.0;
    //    private final GraphTest graphTest;
    private final Button applyBtn;
    private final GroovyPane graphPane;
    private TextField nodeOne = new TextField();
    private TextField nodeTwo = new TextField();
    private TextField portOne = new TextField();
    private Label labelOne = new Label("Node 1: ");
    private Label labelTwo = new Label("Node 2: ");
    private Label portLabel = new Label("From Port: ");
    private VBox root;

    public EdgeConnectWindow(Stage primaryStage, GroovyPane graphPane) {
        super(primaryStage);
        dialog.setTitle("Edge Connection");
//        this.graphTest = graphTest;
        this.graphPane = graphPane;
        root = new VBox();
        applyBtn = new Button("Apply");
        applyBtn.setOnMouseClicked(e -> closeWindow());
        root.getChildren().addAll(labelOne, nodeOne, labelTwo, nodeTwo, portLabel, portOne, applyBtn);
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
        Integer intOne = Integer.valueOf(nodeOne.getText());
        Integer intTwo = Integer.valueOf(nodeTwo.getText());
//        graphTest.connectTwoNodes(intOne, intTwo);
        graphPane.connectTwoNodes(intOne, Ports.valueOf(portOne.getText()), intTwo);
        dialog.close();
    }

}
