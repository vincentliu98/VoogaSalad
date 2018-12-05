package graphUI.phase;

import api.SubView;
import graphUI.graphData.SinglePhaseData;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import graphUI.phase.PhaseNodeFactory.PhaseNode;
import graphUI.phase.TransitionLineFactory.TransitionLine;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;
import phase.api.GameEvent;
import phase.api.PhaseDB;
import phase.api.PhaseGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A stack pane that contains a pane on the right.
 * It allows the user to add nodes(right side) on the graph
 *
 * @author jl729
 * @author Amy
 */

public class PhasePane implements SubView<StackPane> {
    public static final Double ICON_WIDTH = 95.0;
    public static final Double ICON_HEIGHT = 95.0;
    public static final Double INIT_X_POS = 100.0;
    public static final Double INIT_Y_POS = 50.0;

    private enum DRAG_PURPOSE {
        NOTHING,
        CHANGE_POS,
        CONNECT_LINE
    }

    private DRAG_PURPOSE draggingPurpose;
    private double orgSceneX, orgSceneY;
    private double orgTranslateX, orgTranslateY;

    private StackPane root = new StackPane();
    private Pane pane = new Pane();
    private Pane graphBox = new Pane();
    private Group group = new Group();
    private double newNodeX;
    private double newNodeY;
    private PhaseDB phaseDB;
    private PhaseNodeFactory factory;
    private TransitionLineFactory trFactory;
    private SinglePhaseData singlePhaseData;
    private PhaseChooserPane phaseChooserPane;

    private Set<TransitionLine> lines;
    private Set<PhaseNode> nodes;
    private SimpleObjectProperty<TransitionLine> selectedEdge;
    private SimpleObjectProperty<PhaseNode> selectedNode;

    private PhaseNode edgeFrom;
    private Line tmpLine;
    private PhaseGraph graph;

    public PhasePane(PhaseDB phaseDB, Supplier<GroovyPane> genGroovyPane, PhaseGraph graph, SinglePhaseData singlePhaseData, PhaseChooserPane phaseChooserPane) {
        this.graph = graph;
        this.phaseDB = phaseDB;
        this.singlePhaseData = singlePhaseData;
        this.phaseChooserPane = phaseChooserPane;

        factory = new PhaseNodeFactory(phaseDB, genGroovyPane);
        trFactory = new TransitionLineFactory(genGroovyPane, group.getChildren()::add, group.getChildren()::remove);

        lines = new HashSet<>();
        nodes = new HashSet<>();

        selectedEdge = new SimpleObjectProperty<>();
        selectedNode = new SimpleObjectProperty<>();

        selectedEdge.addListener((e, o, n) -> {
            if (o != null && lines.contains(o)) o.setColor(Color.BLACK);
            if (n != null) {
                if (selectedNode.get() != null) selectedNode.set(null);
                n.setColor(Color.CORAL);
            }
        });

        selectedNode.addListener((e, o, n) -> {
            if (o != null) o.setBorder(new Border(new BorderStroke(null, null, null, null)));
            if (n != null) {
                if (selectedEdge.get() != null) selectedEdge.set(null);
                n.setBorder(
                        new Border(
                                new BorderStroke(Color.CORAL, BorderStrokeStyle.SOLID, null, new BorderWidths(3))
                        )
                );
            }
        });

        root.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if ((e.getCode() == KeyCode.BACK_SPACE) || (e.getCode() == KeyCode.DELETE)) {
                deleteSelected();
            }
            if (e.getCode() == KeyCode.ESCAPE) {
                selectedNode.set(null);
                selectedEdge.set(null);
            }
        });

        initializeUI();
        createNode(factory.source(graph.source(), INIT_X_POS, INIT_Y_POS));
    }

    private void initializeUI() {
        graphBox.getChildren().add(group);
        graphBox.setPrefSize(2000, 2000);
        setupGraphbox();
        root.getChildren().add(new ScrollPane(graphBox));

        pane.setMaxWidth(150);
        pane.setMaxHeight(150);
        var nodeImg = new Image(
                this.getClass().getClassLoader().getResourceAsStream("phaseNode.png"),
                ICON_WIDTH, ICON_HEIGHT, true, true
        );
        var nodeImgView = draggableGroovyIcon(nodeImg);
        nodeImgView.getStyleClass().add("cursorImage");
        pane.getChildren().add(nodeImgView);
        StackPane.setAlignment(pane, Pos.BOTTOM_RIGHT);
        root.getChildren().add(pane);
    }


    private ImageView draggableGroovyIcon(Image icon) {
        var view = new ImageView(icon);
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
            } catch (Throwable t) {
                displayError(t.toString());
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private void deleteSelected() {
        if (selectedEdge.get() != null) {
            selectedEdge.get().removeFromScreen();
            lines.remove(selectedEdge.get());
            graph.removeEdge(phaseDB.createTransition(
                    selectedEdge.get().start().model(), selectedEdge.get().trigger(), null
            ));
        }
        if (selectedNode.get() != null && selectedNode.get().model() != graph.source()) {
            nodes.remove(selectedNode.get());
            var toRemove = new HashSet<TransitionLine>();
            for (var l : lines) {
                if (l.start() == selectedNode.get() || l.end() == selectedNode.get()) {
                    toRemove.add(l);
                    l.removeFromScreen();
                }
            } // remove lines connected from or to that node
            lines.removeAll(toRemove);

            group.getChildren().remove(selectedNode.get());
            graph.removeNode(selectedNode.get().model());
        }
    }


    private void connectNodes(PhaseNode node1, GameEvent event, PhaseNode node2) {
        try {
            var cnts = lines.stream()
                    .filter(p -> p.start() == node1 && p.end() == node2)
                    .map(TransitionLine::cnt)
                    .collect(Collectors.toSet());

            var edgeLine = trFactory.gen(
                    node1.getCenterX(), node1.getCenterY(), node2.getCenterX(), node2.getCenterY(),
                    Stream.iterate(0, x -> x + 1).dropWhile(cnts::contains).findFirst().get(),
                    node1, event, node2
            );
            edgeLine.setStrokeWidth(3);
            edgeLine.setOnMouseEntered(e -> root.setCursor(Cursor.HAND));
            edgeLine.setOnMouseExited(e -> root.setCursor(Cursor.DEFAULT));
            edgeLine.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1) selectedEdge.set(edgeLine);
                else selectedEdge.get().showGraph();
            });
            edgeLine.label().setOnMouseEntered(e -> root.setCursor(Cursor.HAND));
            edgeLine.label().setOnMouseExited(e -> root.setCursor(Cursor.DEFAULT));
            edgeLine.label().setOnMouseClicked(e -> {
                if (e.getClickCount() == 1) selectedEdge.set(edgeLine);
                else selectedEdge.get().showGraph();
            });

            graph.addEdge(phaseDB.createTransition(node1.model(), event, node2.model()));
            lines.add(edgeLine);

            singlePhaseData.addConnect(new Pair<>(node1.getName(), node2.getName()), event);
            phaseChooserPane.checkMapUpdate();

            edgeLine.getStyleClass().add("cursorImage");
            edgeLine.toBack();
        } catch (Throwable t) {
            displayError(t.toString());
        }
    }

    private void createNode(PhaseNode node) {
        try {
            graph.addNode(node.model());
            nodes.add(node);

            updateFrontEndData(node);

            node.getStyleClass().add("cursorImage");
            // Add mouseEvent to the GroovyNode to update position
            node.setOnMousePressed(this::nodeMousePressedHandler);
            node.setOnMouseDragged(this::nodeMouseDraggedHandler);
            node.setOnMouseReleased(this::nodeMouseReleasedHandler);
            node.inner().setOnMouseEntered(e -> root.setCursor(Cursor.MOVE));
            node.inner().setOnMouseExited(e -> root.setCursor(Cursor.DEFAULT));
            node.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1) selectedNode.set(node);
                else node.showGraph();
            });
            group.getChildren().add(node);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void updateFrontEndData(PhaseNode node) {
        var nodeName = node.getName();
        singlePhaseData.addNode(nodeName);
        singlePhaseData.addPos(nodeName, new Pair<>(node.getX(), node.getY()));
        // TODO: 12/4/18 make an observableMap
        phaseChooserPane.setPhaseDataMap(new HashMap<>() {{
            put(nodeName, singlePhaseData);
        }});
        phaseChooserPane.checkMapUpdate();
    }

    private void nodeMousePressedHandler(MouseEvent t) {
        PhaseNode node = (PhaseNode) t.getSource();

        orgSceneX = t.getSceneX();
        orgSceneY = t.getSceneY();
        if (node.inner().contains(t.getX(), t.getY())) {
            draggingPurpose = DRAG_PURPOSE.CHANGE_POS;
            orgTranslateX = node.getTranslateX();
            orgTranslateY = node.getTranslateY();
        } else if (node.contains(t.getX(), t.getY())) {
            draggingPurpose = DRAG_PURPOSE.CONNECT_LINE;
            edgeFrom = node;
            tmpLine = new Line(node.getX() + t.getX(), node.getY() + t.getY(), node.getX() + t.getX(), node.getY() + t.getY());
            tmpLine.setStrokeWidth(3);
            tmpLine.getStrokeDashArray().addAll(20d, 20d);
            group.getChildren().add(tmpLine);
        }
    }

    private void nodeMouseReleasedHandler(MouseEvent t) {
        if (draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
            for (var n : nodes) {
                if (n.localToScreen(n.getBoundsInLocal()).contains(t.getScreenX(), t.getScreenY())) {
                    if (edgeFrom == n) continue;
                    var res = new EventTriggerDialog().showAndWait();
                    res.ifPresent(gameEvent -> connectNodes(edgeFrom, gameEvent, n));
                    break;
                }
            }
            group.getChildren().remove(tmpLine);
        }
        draggingPurpose = DRAG_PURPOSE.NOTHING;
    }

    private void nodeMouseDraggedHandler(MouseEvent t) {
        double offsetX = t.getSceneX() - orgSceneX;
        double offsetY = t.getSceneY() - orgSceneY;

        if (draggingPurpose == DRAG_PURPOSE.CHANGE_POS) {
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;

            PhaseNode node = (PhaseNode) t.getSource();

            node.setTranslateX(newTranslateX);
            node.setTranslateY(newTranslateY);

            updateFrontEndData(node);
            phaseChooserPane.checkMapUpdate();

            updateLocations(node);
        } else if (draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
            tmpLine.setEndX(tmpLine.getStartX() + offsetX);
            tmpLine.setEndY(tmpLine.getStartY() + offsetY);
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
        for (var l : lines) {
            if (l.start() == n) {
                l.setStartX(n.getCenterX());
                l.setStartY(n.getCenterY());
            }
            if (l.end() == n) {
                l.setEndX(n.getCenterX());
                l.setEndY(n.getCenterY());
            }
        }
    }

    @Override
    public StackPane getView() {
        return root;
    }
}


