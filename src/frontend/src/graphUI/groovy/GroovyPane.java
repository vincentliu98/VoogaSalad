package graphUI.groovy;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import graphUI.groovy.factory.GroovyNode;
import graphUI.groovy.factory.GroovyNodeFactory;
import groovy.api.BlockGraph;
import groovy.api.GroovyFactory;
import groovy.api.Ports;
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
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Playground for testing graph function
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author jl729
 */

public class GroovyPane extends PopUpWindow {
    public static final Double WIDTH = 1200.0;
    public static final Double HEIGHT = 800.0;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;
    EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();

            GroovyNode node = (GroovyNode) t.getSource();

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

            GroovyNode node = (GroovyNode) t.getSource();

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
    private List<GroovyNode> nodeList = new ArrayList<>();
    private GroovyNodeFactory nodeFactory;
    private GroovyFactory factory;
    private BlockGraph graph;

    public GroovyPane(Stage primaryStage, GroovyFactory factory) {
        super(primaryStage);
        dialog.setTitle("Graph Setting");
        this.nodeFactory = new GroovyNodeFactory(factory);
        this.factory = factory;
        graph = factory.createGraph();

        nodeList.add(createNode(100, 100));
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

    public void connectTwoNodes(Integer nodeOneIndex, Ports port, Integer nodeTwoIndex) {
        connectNodes(nodeList.get(nodeOneIndex), port, nodeList.get(nodeTwoIndex), "E" + edgeNum++);
    }

    private void initializeUI(List<GroovyNode> nodeList) {
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
                var newGraphNod = createNode(newNodeX - 50, newNodeY - 50);
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

    private void connectNodes(GroovyNode node1, Ports port, GroovyNode node2, String edgeText) {
        Line edgeLine = new Line(node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY());
        edgeLine.setStrokeWidth(3);
        Label edgeLabel = new Label(edgeText);

        // make the lines clickable and user-friendly
        edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        // TODO: 11/14/18 Pop-up window to add things to the Edge
//        edgeLine.setOnMouseClicked(e -> System.out.println("The Line is clicked"));
        edgeLine.setOnMouseClicked(e -> new EdgeSettingWindow(new Stage()));

        // add to the GroovyNode's storage
        node1.addNeighbor(port, node2, edgeLine);

        try {
            graph.addEdge(factory.createEdge(node1.model(), port, node2.model())); // connect also within the model
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        group.getChildren().addAll(edgeLine, edgeLabel);
    }

    private GroovyNode createNode(double xPos, double yPos) {
        var node = nodeFactory.forEach(xPos, yPos); // view



        try {
            graph.addNode(node.model());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        // Add mouseEvent to the GroovyNode to update position
        node.setOnMousePressed(circleOnMousePressedEventHandler);
        node.setOnMouseDragged(circleOnMouseDraggedEventHandler);
        node.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        node.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        // TODO: 11/14/18 Pop-up window to add things to the Node
//        node.setOnMouseClicked(e -> System.out.println("The GroovyNode is clicked"));
        node.setOnMouseClicked(e -> new NodeSettingWindow(new Stage()));
        return node;
    }

    // Helper method for updating the position when the GroovyNode is dragged
    private void updateLocations(GroovyNode node) {

        Map<Ports, Pair<GroovyNode, Line>> connectedNodes = node.getConnectedNodes();

        for(var port : connectedNodes.keySet()) {
            Line l = connectedNodes.get(port).getValue();
            GroovyNode neighbor = connectedNodes.get(port).getKey();

            l.setStartX(node.getCenterX());

            l.setStartY(node.getCenterY());

            l.setEndX(neighbor.getCenterX());

            l.setEndY(neighbor.getCenterY());
        }
    }
}


