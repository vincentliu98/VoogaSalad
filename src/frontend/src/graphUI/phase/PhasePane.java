package graphUI.phase;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import graphUI.phase.PhaseNodeFactory.PhaseNode;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import phase.api.GameEvent;
import phase.api.PhaseDB;
import phase.api.PhaseGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Playground for testing graph function
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author jl729
 */

public class PhasePane extends PopUpWindow {
    public static final Double WIDTH = 1200.0;
    public static final Double HEIGHT = 800.0;
    public static final Double ICON_WIDTH = 30.;
    public static final Double ICON_HEIGHT = 30.;

    private enum DRAG_PURPOSE {
        NOTHING,
        CHANGE_POS,
        CONNECT_LINE
    }

    private DRAG_PURPOSE draggingPurpose;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private GridPane root = new GridPane();
    private Pane graphBox = new Pane();
    private Group group = new Group();
    private ScrollPane itemBox = new ScrollPane();
    private Scene myScene;
    private double newNodeX;
    private double newNodeY;
    private PhaseDB phaseDB;
    private PhaseNodeFactory factory;

    private Set<TransitionLine> lines;
    private Set<PhaseNode> nodes;
    private SimpleObjectProperty<TransitionLine> selectedEdge;
    private SimpleObjectProperty<PhaseNode> selectedNode;

    private String name = "";
    private PhaseGraph graph;
    private PhaseNode edgeFrom;
    private Line tmpLine;

    public PhasePane(Stage primaryStage, PhaseDB phaseDB) {
        super(primaryStage);
        this.phaseDB = phaseDB;
        factory = new PhaseNodeFactory(phaseDB);

        while(name.equals("")) {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setContentText("Please enter the name of this phase graph:");
            dialog.showAndWait().ifPresent(name -> {
                var tryGraph = phaseDB.createGraph(name);
                if(tryGraph.isSuccess()) {
                    try {
                        graph = tryGraph.get();
                        this.name = name;
                    } catch (Throwable ignored) { }
                }
            });
        }

        lines = new HashSet<>();
        nodes = new HashSet<>();
        createNode(factory.source(graph.source(), 100, 50));

        selectedEdge = new SimpleObjectProperty<>();
        selectedNode = new SimpleObjectProperty<>();

        selectedEdge.addListener((e, o, n) -> {
            if(o != null && lines.contains(o)) o.setStroke(Color.BLACK);
            if(n != null) {
                if(selectedNode.get() != null) selectedNode.set(null);
                n.setStroke(Color.RED);
            }
        });

        selectedNode.addListener((e, o, n) -> {
            if(o != null) o.setBorder(new Border(new BorderStroke(null, null, null, null)));
            if(n != null) {
                if(selectedEdge.get() != null) selectedEdge.set(null);
                n.setBorder(
                    new Border(
                        new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(3))
                    )
                );
            }
        });

        dialog.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if(e.getCode() == KeyCode.DELETE) deleteSelected();
            if(e.getCode() == KeyCode.ESCAPE) {
                selectedNode.set(null);
                selectedEdge.set(null);
            }
        });

        initializeUI();
        showWindow();
    }

    @Override
    public void showWindow() {
        dialog.setScene(myScene);
        dialog.show();
    }

    /**
     *  We do not close the window; instead, we just hide it and show it when a button is clicked
     */
    @Override
    protected void closeWindow() { dialog.hide(); }

    private void initializeUI() {
        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);
        graphBox.getChildren().add(group);
        graphBox.setPrefSize(2*WIDTH, 3*HEIGHT);
        setupGraphbox();
        initializeGridPane();
        initializeItemBox();
        root.add(itemBox, 0, 0);
        HBox.setHgrow(itemBox, Priority.ALWAYS);
        var graphBoxWrapper = new ScrollPane();
        graphBoxWrapper.setContent(graphBox);
        root.add(graphBoxWrapper, 1, 0);
        myScene = new Scene(root, WIDTH, HEIGHT);
    }

    private void initializeItemBox() {
        var vbox = new VBox();

        vbox.setSpacing(10);
        vbox.getChildren().add(draggableGroovyIcon(
            new Image(this.getClass().getClassLoader().getResourceAsStream("phaseNode.png"))
        ));

        itemBox.setContent(vbox);
        itemBox.setMinHeight(HEIGHT);
    }

    private ImageView draggableGroovyIcon(Image icon) {
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
            event.consume();
        });

        return view;
    }

    private void setupGraphbox() {
        graphBox.setOnDragOver(this::graphBoxDragOverHandler);
        graphBox.setOnDragDropped(this::graphBoxDragDropHandler);
    }

    private void graphBoxDragOverHandler(DragEvent event) {
        if (event.getGestureSource() != graphBox && event.getDragboard().hasImage()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            newNodeX = event.getX();
            newNodeY = event.getY();
        }
        event.consume();
    }

    private void graphBoxDragDropHandler(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasImage()) {
            success = true;
            try {
                var dialog = new TextInputDialog();
                dialog.setHeaderText("Name of the phase");
                String name = dialog.showAndWait().get();
                createNode(factory.gen(newNodeX, newNodeY, name).get());
            } catch (Throwable t) { displayError(t.toString()); }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void deleteSelected() {
        if(selectedEdge.get() != null) {
            group.getChildren().remove(selectedEdge.get());
            lines.remove(selectedEdge.get());
            graph.removeEdge(phaseDB.createTransition(
                selectedEdge.get().start().model(), selectedEdge.get().trigger(), null
            ));
        }
        if(selectedNode.get() != null && selectedNode.get().model() != graph.source()) {
            nodes.remove(selectedNode.get());
            var toRemove = new HashSet<TransitionLine>();
            for(var l : lines) {
                if(l.start() == selectedNode.get() || l.end() == selectedNode.get()) {
                    toRemove.add(l);
                    group.getChildren().remove(l);
                }
            } // remove lines connected from or to that node
            lines.removeAll(toRemove);

            group.getChildren().remove(selectedNode.get());
            graph.removeNode(selectedNode.get().model());
        }
    }


    private void initializeGridPane() {
        var col1 = new ColumnConstraints();
        col1.setPercentWidth(15);
        var col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        var col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        root.getColumnConstraints().addAll(col1, col2, col3);
    }

    private void connectNodes(PhaseNode node1, GameEvent event, PhaseNode node2) {
        if(node1 == node2) return;
        try {
            var edgeLine = new TransitionLine(
                node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY(),
                node1, event, node2, group.getChildren()::add
            );
            edgeLine.setStrokeWidth(3);
            edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
            edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
            edgeLine.setOnMouseClicked(e -> selectedEdge.set(edgeLine));

            graph.addEdge(phaseDB.createTransition(node1.model(), event, node2.model()));
            lines.add(edgeLine);

            group.getChildren().addAll(edgeLine);
            edgeLine.toBack();
        } catch (Throwable t) { displayError(t.toString());}
    }

    private void createNode(PhaseNode node) {
        try {
            graph.addNode(node.model());
            nodes.add(node);
            // Add mouseEvent to the GroovyNode to update position
            node.setOnMousePressed(this::nodeMousePressedHandler);
            node.setOnMouseDragged(this::nodeMouseDraggedHandler);
            node.setOnMouseReleased(this::nodeMouseReleasedHandler);
            node.inner().setOnMouseEntered(e -> myScene.setCursor(Cursor.MOVE));
            node.inner().setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(e -> selectedNode.set(node));
            group.getChildren().add(node);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void nodeMousePressedHandler(MouseEvent t) {
        PhaseNode node = (PhaseNode) t.getSource();

        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        if(node.inner().contains(t.getX(), t.getY())) {
            draggingPurpose = DRAG_PURPOSE.CHANGE_POS;
            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();
        } else if(node.contains(t.getX(), t.getY())) {
            draggingPurpose = DRAG_PURPOSE.CONNECT_LINE;
            edgeFrom = node;
            tmpLine = new Line(node.getCenterX(), node.getCenterY(), node.getCenterX(), node.getCenterY());
            tmpLine.setStrokeWidth(3);
            tmpLine.getStrokeDashArray().addAll(20d, 20d);
            group.getChildren().add(tmpLine);
        }
    }

    private void nodeMouseReleasedHandler(MouseEvent t) {
        if(draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
            for (var n : nodes) {
                if(n.localToScreen(n.getBoundsInLocal()).contains(t.getScreenX(), t.getScreenY())) {
                    connectNodes(edgeFrom, GameEvent.mouseClick(), n); // TODO
                    break;
                }
            } group.getChildren().remove(tmpLine);
        }
        draggingPurpose = DRAG_PURPOSE.NOTHING;
    }

    private void nodeMouseDraggedHandler(MouseEvent t) {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;

        if(draggingPurpose == DRAG_PURPOSE.CHANGE_POS) {
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            PhaseNode node = (PhaseNode) t.getSource();

            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);

            updateLocations(node);
        } else if(draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
            tmpLine.setEndX(edgeFrom.getCenterX()+offsetX);
            tmpLine.setEndY(edgeFrom.getCenterY()+offsetY);
        }
    }

    private void displayError(String msg) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something's wrong");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    private void updateLocations(PhaseNode n) {
        for(var l : lines) {
            if (l.start() == n) {
                l.setStartX(n.getCenterX());
                l.setStartY(n.getCenterY());
            }
            if(l.end() == n) {
                l.setEndX(n.getCenterX());
                l.setEndY(n.getCenterY());
            }
        }
    }
}


