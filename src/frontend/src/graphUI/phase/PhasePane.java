package graphUI.phase;

import api.SubView;
import authoringInterface.spritechoosingwindow.PopUpWindow;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import graphUI.phase.PhaseNodeFactory.PhaseNode;
import graphUI.phase.TransitionLineFactory.TransitionLine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import phase.api.GameEvent;
import phase.api.Phase;
import phase.api.PhaseDB;
import phase.api.PhaseGraph;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Playground for testing graph function
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author jl729
 * @author Amy
 */

public class PhasePane implements SubView<GridPane> {
    public static final Double WIDTH = 1200.0;
    public static final Double HEIGHT = 800.0;
    public static final Double ICON_WIDTH = 100.0;
    public static final Double ICON_HEIGHT = 100.0;

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
    private TransitionLineFactory trFactory;

    private Set<TransitionLine> lines;
    private Set<PhaseNode> nodes;
    private SimpleObjectProperty<TransitionLine> selectedEdge;
    private SimpleObjectProperty<PhaseNode> selectedNode;

    private String name = "";
    private PhaseGraph graph;
    private PhaseNode edgeFrom;
    private Line tmpLine;

    public PhasePane(PhaseDB phaseDB, Supplier<GroovyPane> genGroovyPane) {
        this.phaseDB = phaseDB;
        factory = new PhaseNodeFactory(phaseDB, genGroovyPane);
        trFactory = new TransitionLineFactory(genGroovyPane, group.getChildren()::add, group.getChildren()::remove);

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
            if(o != null && lines.contains(o)) o.setColor(Color.BLACK);
            if(n != null) {
                if(selectedNode.get() != null) selectedNode.set(null);
                n.setColor(Color.RED);
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

        root.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if((e.getCode() == KeyCode.BACK_SPACE) || (e.getCode() == KeyCode.DELETE)) {
                System.out.println("try to delete");
                deleteSelected();
            }
            if(e.getCode() == KeyCode.ESCAPE) {
                selectedNode.set(null);
                selectedEdge.set(null);
            }
        });

        initializeUI();
    }

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
        var nodeImg = new Image(
            this.getClass().getClassLoader().getResourceAsStream("phaseNode.png"),
            ICON_WIDTH, ICON_HEIGHT, true, true
        );
        vbox.getChildren().add(draggableGroovyIcon(nodeImg));

        itemBox.setContent(vbox);
        itemBox.setMinHeight(HEIGHT);
    }

    private ImageView draggableGroovyIcon(Image icon) {
        var view = new ImageView(icon);
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
            System.out.print("try to delete");
            selectedEdge.get().removeFromScreen();
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
                    l.removeFromScreen();
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
        col2.setPercentWidth(85);
        root.getColumnConstraints().addAll(col1, col2);
    }

    private void connectNodes(PhaseNode node1, GameEvent event, PhaseNode node2) {
        try {
            var cnts = lines.stream()
                 .filter(p -> p.start() == node1 && p.end() == node2)
                 .map(TransitionLine::cnt)
                 .collect(Collectors.toSet());

            var edgeLine = trFactory.gen(
                node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY(),
                Stream.iterate(0, x -> x+1).dropWhile(cnts::contains).findFirst().get(),
                node1, event, node2
            );
            edgeLine.setStrokeWidth(3);
            edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
            edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
            edgeLine.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1) selectedEdge.set(edgeLine);
                else selectedEdge.get().showGraph();
            });
            edgeLine.label().setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
            edgeLine.label().setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
            edgeLine.label().setOnMouseClicked(e -> {
                if(e.getClickCount() == 1) selectedEdge.set(edgeLine);
                else selectedEdge.get().showGraph();
            });

            graph.addEdge(phaseDB.createTransition(node1.model(), event, node2.model()));
            lines.add(edgeLine);
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
            node.setOnMouseClicked(e -> {
                if(e.getClickCount() == 1) selectedNode.set(node);
                else node.showGraph();
            });
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
            tmpLine = new Line(node.getX()+t.getX(), node.getY()+t.getY(), node.getX()+t.getX(), node.getY()+t.getY());
            tmpLine.setStrokeWidth(3);
            tmpLine.getStrokeDashArray().addAll(20d, 20d);
            group.getChildren().add(tmpLine);
        }
    }

    private void nodeMouseReleasedHandler(MouseEvent t) {
        if(draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
            for (var n : nodes) {
                if(n.localToScreen(n.getBoundsInLocal()).contains(t.getScreenX(), t.getScreenY())) {
                    if(edgeFrom == n) continue;
                    var res = new EventTriggerDialog().showAndWait();
                    res.ifPresent(gameEvent -> connectNodes(edgeFrom, gameEvent, n));
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
            tmpLine.setEndX(tmpLine.getStartX()+offsetX);
            tmpLine.setEndY(tmpLine.getStartY()+offsetY);
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

    @Override
    public GridPane getView() {
        return root;
    }
}


