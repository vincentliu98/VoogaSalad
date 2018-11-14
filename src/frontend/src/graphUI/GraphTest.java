package graphUI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GraphTest extends Application {

    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    private Group root = new Group();
    private Scene myScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GraphNode node1 = createNode("A", 100, 100, Color.RED);
        GraphNode node2 = createNode("B", 300, 200, Color.GREEN);
        GraphNode node3 = createNode("C", 80, 300, Color.PURPLE);

        connectNodes(node1, node2, "C1");
        connectNodes(node3, node1, "C2");
        connectNodes(node3, node2, "C3");

        root.getChildren().addAll(node1, node2, node3);

        myScene = new Scene(root, 400, 400);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    private void connectNodes(GraphNode node1, GraphNode node2, String edgeText) {
        Line edgeLine = new Line(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY());
        edgeLine.setStrokeWidth(3);
        Label edgeLabel = new Label(edgeText);

        // make the lines clickable and user-friendly
        edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        edgeLine.setOnMouseClicked(e -> System.out.println("The Line is clicked"));

        // add to the GraphNode's storage
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
        node1.addEdge(edgeLine, edgeLabel);
        node2.addEdge(edgeLine, edgeLabel);

        root.getChildren().addAll(edgeLine, edgeLabel);
    }

    private GraphNode createNode(String nodeName, double xPos, double yPos, Color color) {
        GraphNode node = new GraphNode(nodeName, xPos, yPos, color);
        // Add mouseEvent to the GraphNode to update position
        node.setOnMousePressed(circleOnMousePressedEventHandler);
        node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        node.setOnMouseClicked(e -> System.out.println("The GraphNode is clicked"));

        return node;
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<>() {

        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            GraphNode node = (GraphNode) t.getSource();

            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();
        }
    };

    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = new EventHandler<>() {

        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            GraphNode node = (GraphNode) t.getSource();

            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);

            updateLocations(node);
        }
    };

    // Helper method for updating the position when the GraphNode is dragged
    private void updateLocations(GraphNode node) {

        ArrayList<GraphNode> connectedNodes = node.getConnectedNodes();

        ArrayList<Line> edgesList = node.getEdges();

        for (int i = 0; i < connectedNodes.size(); i++) {

            GraphNode neighbor = connectedNodes.get(i);
            Line l = edgesList.get(i);

            l.setStartX(node.getCenterX());

            l.setStartY(node.getCenterY());

            l.setEndX(neighbor.getCenterX());

            l.setEndY(neighbor.getCenterY());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}


