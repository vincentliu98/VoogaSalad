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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    public static final Double ICON_WIDTH = 30.;
    public static final Double ICON_HEIGHT = 30.;

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

            node.updateLocations();
        }
    };
    private GridPane root = new GridPane();
    private Pane graphBox = new Pane();
    private Group group = new Group();
    private ScrollPane itemBox = new ScrollPane();
    private Scene myScene;
    private double newNodeX;
    private double newNodeY;
    private Integer edgeNum = 0;
    private List<GroovyNode> nodeList = new ArrayList<>();
    private GroovyNodeFactory nodeFactory;
    private GroovyFactory factory;
    private BlockGraph graph;
    private String currentDragType;
    private boolean currentFetchArg;

    public GroovyPane(Stage primaryStage, GroovyFactory factory) {
        super(primaryStage);
        dialog.setTitle("Graph Setting");
        this.nodeFactory = new GroovyNodeFactory(factory);
        this.factory = factory;
        graph = factory.createGraph();

        createNode(nodeFactory.source(graph.source(), 100, 50));

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
        var vbox = new VBox();

        vbox.getChildren().addAll(
            new Label("Drag node to the right"),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("ForEachIcon.png")), "ForEach", true),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("IfIcon.png")), "If", false),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("IfElseIcon.png")), "IfElse", false),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("ElseIcon.png")), "ForEach", false),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("AssignIcon.png")), "Assign", false),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("AddIcon.png")), "Binary", true),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("AddIcon.png")), "Unary", true),
            draggableGroovyIcon(new Image(
                getClass().getClassLoader().getResourceAsStream("IntegerIcon.png")), "Integer", true),
            setUpLineDragAndDrop(),
            new Label("Click line to connect nodes")
        );

        vbox.setSpacing(20);

        itemBox.setContent(vbox);
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

    private ImageView draggableGroovyIcon(Image icon, String blockType, boolean fetchArg) {
        var view = new ImageView(icon);
        view.setFitWidth(ICON_WIDTH);
        view.setFitHeight(ICON_HEIGHT);
        view.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
        view.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
        view.setOnDragDetected(event -> {
            Dragboard db = view.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(icon);
            db.setContent(content);
            currentDragType = blockType;
            currentFetchArg = fetchArg;
            event.consume();
        });

        graphBox.setOnDragOver(event -> {
            if (event.getGestureSource() != graphBox && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                newNodeX = event.getX();
                newNodeY = event.getY();
            }
            event.consume();
        });

        graphBox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                success = true;
                try {
                    AtomicReference<String> arg = new AtomicReference<>("");
                    if(currentFetchArg) {
                        var dialog = new TextInputDialog();
                        dialog.setHeaderText("Type the value of the " + currentDragType);
                        Optional<String> result = dialog.showAndWait();
                        result.ifPresent(arg::set);
                    }
                    createNode(nodeFactory.fromType(currentDragType, newNodeX, newNodeY, arg.get()).get());
                } catch (Throwable t) {
                    var alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Dialog");
                    alert.setHeaderText("Something's wrong");
                    alert.setContentText(t.toString());
                    alert.showAndWait();
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        return view;
    }

    private void initializeGridPane() {
        var col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(80);
        root.getColumnConstraints().addAll(col1, col2);
    }

    private void connectNodes(GroovyNode node1, Ports port, GroovyNode node2, String edgeText) {
        var p = node1.portXY(port);
        Line edgeLine = new Line(p.getKey(), p.getValue(), node2.getCenterX(), node2.getCenterY());
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

    private void createNode(GroovyNode node) {
        nodeList.add(node);

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

        group.getChildren().add(node);
    }
}


