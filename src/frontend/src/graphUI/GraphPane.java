package graphUI;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Playground for testing graph function
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author jl729
 */

public class GraphPane extends PopUpWindow {

    public static final Double WIDTH = 700.0;
    public static final Double HEIGHT = 500.0;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
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
    private GridPane root = new GridPane();
    private Pane graphBox = new Pane();
    private Group group = new Group();
    private VBox itemBox = new VBox();
    private Scene myScene;
    private double newNodeX;
    private double newNodeY;
    private Integer edgeNum = 0;
    private List<GraphNode> nodeList = new ArrayList<>();

    public GraphPane(Stage primaryStage) {
        super(primaryStage);
        dialog.setTitle("Graph Setting");

        nodeList.add(createNode("0", 100, 100, Color.DEEPSKYBLUE));
        initializeUI(nodeList);

        showWindow();
    }

    @Override
    public void showWindow() {
        dialog.setScene(myScene);
        dialog.show();
    }

    @Override
    protected void closeWindow() {
        dialog.close();
    }

    public void connectTwoNodes(Integer nodeOneIndex, Integer nodeTwoIndex) {
        connectNodes(nodeList.get(nodeOneIndex), nodeList.get(nodeTwoIndex), "E" + edgeNum++);
    }

    private void initializeUI(List<GraphNode> nodeList) {
        nodeList.forEach(e -> group.getChildren().add(e));

        graphBox.getChildren().add(group);
        graphBox.setPrefSize(500, 500);
        initializeGridPane();
        initializeItemBox();
        root.add(itemBox, 0, 0);
        HBox.setHgrow(itemBox, Priority.ALWAYS);
        root.add(graphBox, 1, 0);
        myScene = new Scene(root, WIDTH, HEIGHT);
    }

    private void initializeItemBox() {
        Circle circle = setUpCircleDragAndDrop();
        Label circleLabel = new Label("Drag node to the right");
        Label lineLabel = new Label("Click line to connect nodes");
        Line line = setUpLineDragAndDrop();

        itemBox.setSpacing(50);

        itemBox.getChildren().addAll(circleLabel, circle, lineLabel, line);
    }

    private Line setUpLineDragAndDrop() {
        Line line = new Line(0.0, 250.0, 100.0, 250.0);
        line.setStrokeWidth(10);
        // make the lines clickable and user-friendly
        line.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        line.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        line.setOnMouseClicked(e -> new EdgeConnectWindow(new Stage(), this));
        return line;
    }

    private Circle setUpCircleDragAndDrop() {
        Circle circle = new Circle(0, 0, 50.0);
        circle.setFill(Color.DEEPSKYBLUE);
        circle.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        circle.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        circle.setOnDragDetected(event -> {
            /* drag was detected, start drag-and-drop gesture*/
            System.out.println("onDragDetected");
            /* allow any transfer mode */
            Dragboard db = circle.startDragAndDrop(TransferMode.ANY);
            /* put a string on dragboard */
            ClipboardContent content = new ClipboardContent();
            // TODO: 11/14/18 Change the Image
            Image circleImg = new Image(getClass().getClassLoader().getResourceAsStream("circle.png"));
            content.putImage(circleImg);
            db.setContent(content);
            event.consume();
        });

        graphBox.setOnDragOver(event -> {
            /* data is dragged over the target */
            System.out.println("onDragOver");

            /* accept it only if it is  not dragged from the same node
             * and if it has a Image data */
            if (event.getGestureSource() != graphBox &&
                    event.getDragboard().hasImage()) {
                /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                newNodeX = event.getX();
                newNodeY = event.getY();
            }

            event.consume();
        });

        graphBox.setOnDragDropped(event -> {
            /* data dropped */
            System.out.println("onDragDropped");
            /* if there is a string data on dragboard, read it and use it */
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                success = true;
                var newGraphNod = createNode(String.valueOf(nodeList.size()), newNodeX - 50, newNodeY - 50, Color.DEEPSKYBLUE);
                nodeList.add(newGraphNod);
//                    connectNodes(nodeList.get(nodeList.size()-2), newGraphNod, "E" + (nodeList.size()-2));
                group.getChildren().add(newGraphNod);
            }
            /* let the source know whether the string was successfully
             * transferred and used */
            event.setDropCompleted(success);

            event.consume();
        });
        return circle;
    }

    private void initializeGridPane() {
        var col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(80);
        root.getColumnConstraints().addAll(col1, col2);
    }

    private void connectNodes(GraphNode node1, GraphNode node2, String edgeText) {
        Line edgeLine = new Line(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY());
        edgeLine.setStrokeWidth(3);
        Label edgeLabel = new Label(edgeText);

        // make the lines clickable and user-friendly
        edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        // TODO: 11/14/18 Pop-up window to add things to the Edge
//        edgeLine.setOnMouseClicked(e -> System.out.println("The Line is clicked"));
        edgeLine.setOnMouseClicked(e -> new EdgeSettingWindow(new Stage()));

        // add to the GraphNode's storage
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
        node1.addEdge(edgeLine, edgeLabel);
        node2.addEdge(edgeLine, edgeLabel);

        group.getChildren().addAll(edgeLine, edgeLabel);
    }

    private GraphNode createNode(String nodeName, double xPos, double yPos, Color color) {
        GraphNode node = new GraphNode(nodeName, xPos, yPos, color);
        // Add mouseEvent to the GraphNode to update position
        node.setOnMousePressed(circleOnMousePressedEventHandler);
        node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        // TODO: 11/14/18 Pop-up window to add things to the Node
//        node.setOnMouseClicked(e -> System.out.println("The GraphNode is clicked"));
        node.setOnMouseClicked(e -> new NodeSettingWindow(new Stage()));
        return node;
    }

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
}


