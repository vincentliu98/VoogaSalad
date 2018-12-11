package graphUI.phase;

import api.SubView;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import graphUI.phase.PhaseNodeFactory.PhaseNode;
import graphUI.phase.TransitionLineFactory.TransitionLine;
import groovy.api.BlockGraph;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import phase.api.Phase;
import phase.api.PhaseDB;
import phase.api.PhaseGraph;
import utils.ErrorWindow;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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
    private Set<TransitionLine> lines;
    private Set<PhaseNode> nodes;
    private SimpleObjectProperty<TransitionLine> selectedEdge;
    private SimpleObjectProperty<PhaseNode> selectedNode;
    private PhaseNode edgeFrom;
    private Line tmpLine;
    private PhaseGraph graph;
    public PhasePane(
            PhaseDB phaseDB,
            Function<BlockGraph, GroovyPane> genGroovyPane,
            PhaseGraph graph
    ) {
        this.graph = graph;
        this.phaseDB = phaseDB;

        factory = new PhaseNodeFactory(phaseDB, genGroovyPane);
        trFactory = new TransitionLineFactory(
                phaseDB,
                genGroovyPane,
                group.getChildren()::add,
                group.getChildren()::remove,
                this::countOverlap
        );

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
        buildFromGraph();
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
        var nodeImgView = draggableCircleIcon(nodeImg);
        nodeImgView.getStyleClass().add("cursorImage");
        pane.getChildren().add(nodeImgView);
        StackPane.setAlignment(pane, Pos.BOTTOM_RIGHT);
        root.getChildren().add(pane);
    }

    private void buildFromGraph() {
        for (var phase : graph.keySet()) createNode(factory.toView(phase));
        for (var phase : graph.keySet()) {
            for (var transition : graph.get(phase)) {
                var node1 = findNodeWithModel(transition.from());
                var node2 = findNodeWithModel(transition.to());
                if (node1.isPresent() && node2.isPresent()) {
                    connectNodes(trFactory.toView(transition, node1.get(), node2.get()));
                }
            }
        }
    }

    private int countOverlap(Phase from, Phase to) {
        var cnts = lines.stream()
                .filter(p -> p.start().model() == from &&
                        p.end().model() == to)
                .map(TransitionLine::cnt)
                .collect(Collectors.toSet());
        return Stream.iterate(0, x -> x + 1).dropWhile(cnts::contains).findFirst().get();
    }

    private Optional<PhaseNode> findNodeWithModel(Phase phase) {
        return nodes.stream().filter(n -> n.model() == phase).findFirst();
    }

    private ImageView draggableCircleIcon(Image icon) {
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
                createNode(factory.makeNode(newNodeX, newNodeY, name).get());
            } catch (Throwable t) {
                t.printStackTrace();
                ErrorWindow.display("Error", t.toString());
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

    private void connectNodes(TransitionLine trLine) {
        try {
            trLine.setStrokeWidth(3);
            trLine.setOnMouseEntered(e -> root.setCursor(Cursor.HAND));
            trLine.setOnMouseExited(e -> root.setCursor(Cursor.DEFAULT));
            trLine.setOnMouseClicked(e -> {
                if (e.getClickCount() == 1) selectedEdge.set(trLine);
                else selectedEdge.get().showGraph();
            });
            trLine.label().setOnMouseEntered(e -> root.setCursor(Cursor.HAND));
            trLine.label().setOnMouseExited(e -> root.setCursor(Cursor.DEFAULT));
            trLine.label().setOnMouseClicked(e -> {
                if (e.getClickCount() == 1) selectedEdge.set(trLine);
                else selectedEdge.get().showGraph();
            });

            lines.add(trLine);

            trLine.getStyleClass().add("cursorImage");
            trLine.toBack();
        } catch (Throwable t) {
            t.printStackTrace();
            ErrorWindow.display("Error", t.toString());
        }
    }

    private void createNode(PhaseNode node) {
        try {
            graph.addNode(node.model());
            nodes.add(node);

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
                    res.ifPresent(gameEvent -> {
                        try {
                            var edge = trFactory.makeEdge(edgeFrom, gameEvent, n);
                            graph.addEdge(edge.model());
                            connectNodes(edge);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    });
                    break;
                }
            }
            group.getChildren().remove(tmpLine);
        } else if (draggingPurpose == DRAG_PURPOSE.CHANGE_POS) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            PhaseNode node = (PhaseNode) t.getSource();
            node.model().setXY(node.model().x() + offsetX, node.model().y() + offsetY);
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
            updateLocations(node);

        } else if (draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
            tmpLine.setEndX(tmpLine.getStartX() + offsetX);
            tmpLine.setEndY(tmpLine.getStartY() + offsetY);
        }
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

    private enum DRAG_PURPOSE {
        NOTHING,
        CHANGE_POS,
        CONNECT_LINE
    }
}


