package graphUI.groovy;

import api.SubView;
import authoringInterface.spritechoosingwindow.PopUpWindow;
import groovy.api.BlockGraph;
import groovy.api.GroovyFactory;
import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import utils.ErrorWindow;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A factory to product GroovyPane. The factory is used because many such GroovyPane is needed.
 * By calling `toView(BlockGraph model)`, the factory spits out a GroovyPane to be edited by the user.
 * <p>
 * It is only initialized once in the View class.
 *
 * @author Inchan Hwang
 */

public class GroovyPaneFactory {
    public static final Double WIDTH = 1200.0;
    public static final Double HEIGHT = 800.0;

    private GroovyNodeFactory nodeFactory;
    private GroovyFactory factory;
    private Stage primaryStage;
    private GroovyPane winCondition;

    public GroovyPaneFactory(Stage primaryStage, GroovyFactory factory, BlockGraph winCondition) {
        this.primaryStage = primaryStage;
        this.nodeFactory = new GroovyNodeFactory(factory);
        this.factory = factory;
        this.winCondition = new GroovyPane(primaryStage, winCondition);
    }

    public GroovyPane gen(BlockGraph model) {
        return new GroovyPane(primaryStage, model);
    }

    public GroovyPane gen(BlockGraph model, boolean openImmediately) {
        return new GroovyPane(primaryStage, model, openImmediately);
    }

    public GroovyPane winCondition() {
        return winCondition;
    }

    private enum DRAG_PURPOSE {
        NOTHING,
        CHANGE_POS,
        CONNECT_LINE
    }

    public class GroovyPane extends PopUpWindow implements SubView<GridPane> {
        private DRAG_PURPOSE draggingPurpose = DRAG_PURPOSE.NOTHING;
        private double orgSceneX, orgSceneY;
        private double orgTranslateX, orgTranslateY;

        private GridPane root = new GridPane();
        private Pane graphBox = new Pane();
        private Group group = new Group();
        private ScrollPane itemBox = new ScrollPane();
        private Scene myScene;
        private DoubleProperty newNodeX, newNodeY;

        private Map<Pair<GroovyNode, Ports>, Pair<GroovyNode, Line>> lines;
        private Set<GroovyNode> nodes;

        private BlockGraph graph;
        private Pair<GroovyNode, Ports> edgeFrom;
        private Line tmpLine;

        private double selectionX, selectionY;
        private Rectangle selection;

        private DraggableGroovyIconFactory iconFactory;

        // XStream ignored
        private TextArea codePane = new TextArea();
        private SimpleObjectProperty<Pair<GroovyNode, Ports>> selectedEdge;
        private SimpleObjectProperty<GroovyNode> selectedNode;

        private GroovyPane(Stage primaryStage, BlockGraph graph) {
            this(primaryStage, graph, true);
        }

        private GroovyPane(Stage primaryStage, BlockGraph graph, Boolean openImmediately) {
            super(primaryStage);
            myScene = new Scene(root, WIDTH, HEIGHT);

            this.graph = graph;

            codePane.setEditable(false);
            codePane.setWrapText(true);

            lines = new HashMap<>();
            nodes = new HashSet<>();

            newNodeX = new SimpleDoubleProperty();
            newNodeY = new SimpleDoubleProperty();
            iconFactory = new DraggableGroovyIconFactory(myScene, nodeFactory, newNodeX, newNodeY);

            selection = new Rectangle();
            selection.setFill(Color.rgb(0, 0, 0, 0.2));
            selectionX = selectionY = 0;

            selectedEdge = new SimpleObjectProperty<>();
            selectedNode = new SimpleObjectProperty<>();

            selectedEdge.addListener((e, o, n) -> {
                if (o != null && lines.get(o) != null) lines.get(o).getValue().setStroke(Color.BLACK);
                if (n != null) {
                    if (selectedNode.get() != null) selectedNode.set(null);
                    lines.get(n).getValue().setStroke(Color.RED);
                }
            });

            selectedNode.addListener((e, o, n) -> {
                if (o != null) o.setBorder(new Border(new BorderStroke(null, null, null, null)));
                if (n != null) {
                    if (selectedEdge.get() != null) selectedEdge.set(null);
                    n.setBorder(
                            new Border(
                                    new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(3))
                            )
                    );
                }
            });

            root.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
                if (e.getCode() == KeyCode.DELETE) deleteSelected();
                if (e.getCode() == KeyCode.ESCAPE) {
                    selectedNode.set(null);
                    selectedEdge.set(null);
                    selection.setWidth(0);
                    selection.setHeight(0);
                }
                // if shrunk node is selected, it unshrinks it;
                // if shrunk node is NOT selected, it tries to shrink whatever that's inside the selection box;
                if (e.getCode() == KeyCode.ENTER) {
                    if (selectedNode.get() != null && selectedNode.get() instanceof ShrunkGroovyNode) {
                        var shrunkNode = (ShrunkGroovyNode) selectedNode.get();
                        shrunkNode.unShrink();
                        group.getChildren().remove(shrunkNode);
                        nodes.remove(shrunkNode);
                        selectedNode.set(null);
                        updateLocations(shrunkNode);
                    } else {
                        var selected = nodes.stream()
                                .filter(n -> selection.contains(n.getCenterX(), n.getCenterY()))
                                .collect(Collectors.toSet());
                        if (selected.size() > 1) {
                            var dialog = new TextInputDialog();
                            dialog.setHeaderText("Give a description to this group");
                            var shrunkBlock = nodeFactory.shrunkBlock(selected, dialog.showAndWait().get());
                            createNode(shrunkBlock);
                            updateLocations(shrunkBlock);
                        }
                    }
                    selection.setWidth(0);
                    selection.setHeight(0);
                }
            });

            initializeUI();
            buildFromGraph();

            if (openImmediately) showWindow();
        }

        // private Map<Pair<GroovyNode, Ports>, Pair<GroovyNode, Line>> lines;
        private void buildFromGraph() {
            for (var block : graph.keySet()) createNode(nodeFactory.toView(block));
            for (var block : graph.keySet()) {
                for (var edge : graph.get(block)) {
                    var node1 = getNodeWithModel(edge.from());
                    var node2 = getNodeWithModel(edge.to());
                    if (node1.isPresent() && node2.isPresent()) {
                        connectNodes(node1.get(), edge.fromPort(), node2.get(), true);
                    }
                }
            }
        }

        private Optional<GroovyNode> getNodeWithModel(GroovyBlock<?> block) {
            return nodes.stream().filter(p -> p.model() == block).findFirst();
        }

        @Override
        public void showWindow() {
            dialog.setScene(myScene);
            dialog.show();
            dialog.toFront();
        }

        /**
         * We do not close the window; instead, we just hide it and show it when a button is clicked
         */
        @Override
        public void closeWindow() {
            dialog.hide();
        }

        private void initializeUI() {
            root.setPrefWidth(WIDTH);
            root.setPrefHeight(HEIGHT);
            graphBox.getChildren().add(group);
            graphBox.setPrefSize(2 * WIDTH, 3 * HEIGHT);
            setupGraphBox();
            initializeGridPane();
            initializeItemBox();
            root.add(itemBox, 0, 0);
            HBox.setHgrow(itemBox, Priority.ALWAYS);
            var graphBoxWrapper = new ScrollPane();
            graphBoxWrapper.setVvalue(graphBoxWrapper.getVmax() / 2);
            graphBoxWrapper.setHvalue(graphBoxWrapper.getHmax() / 2);
            graphBoxWrapper.setContent(graphBox);
            root.add(graphBoxWrapper, 1, 0);
            root.add(codePane, 2, 0);

            graphBox.getChildren().add(selection);
            setupSelectionListener();
        }

        private void setupSelectionListener() {
            graphBox.setOnMousePressed(e -> {
                selection.setWidth(0);
                selection.setHeight(0);
                selectionX = e.getX();
                selectionY = e.getY();
                selection.setX(e.getX());
                selection.setY(e.getY());
            });

            graphBox.setOnMouseDragged(e -> {
                if (draggingPurpose == DRAG_PURPOSE.NOTHING) {
                    if (e.getX() < selectionX) {
                        selection.setX(e.getX());
                        selection.setWidth(selectionX - e.getX());
                    } else selection.setWidth(e.getX() - selectionX);

                    if (e.getY() < selectionY) {
                        selection.setY(e.getY());
                        selection.setHeight(selectionY - e.getY());
                    } else selection.setHeight(e.getY() - selectionY);
                }
            });
        }

        private void initializeItemBox() {
            var vbox = new VBox();

            vbox.getChildren().addAll(
                    new Text("Press ENTER to merge selected blocks"),
                    new Separator()
            );

            vbox.getChildren().addAll(IconLoader.loadControls(iconFactory::gen));
            vbox.getChildren().addAll(IconLoader.loadBinaries(iconFactory::gen));
            vbox.getChildren().addAll(IconLoader.loadLiterals(iconFactory::gen));
            vbox.getChildren().addAll(IconLoader.loadFunctions(iconFactory::gen));
            vbox.getChildren().addAll(IconLoader.loadGameMethods(iconFactory::genWithInfo));

            vbox.setSpacing(10);
            vbox.getChildren().forEach(c -> {
                if (c instanceof HBox) ((HBox) c).setSpacing(5);
            });

            itemBox.setContent(vbox);
        }

        private void setupGraphBox() {
            graphBox.setOnDragOver(this::graphBoxDragOverHandler);
            graphBox.setOnDragDropped(e -> iconFactory.dropHandler(e, this::createNode));
        }

        private void graphBoxDragOverHandler(DragEvent event) {
            if (event.getGestureSource() != graphBox && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                newNodeX.set(event.getX());
                newNodeY.set(event.getY());
            }
            event.consume();
        }

        private void deleteSelected() {
            if (selectedEdge.get() != null) {
                var line = lines.get(selectedEdge.get()).getValue();
                group.getChildren().remove(line);
                lines.remove(selectedEdge.get());
                graph.removeEdge(factory.createEdge(
                        selectedEdge.get().getKey().model(), selectedEdge.get().getValue(), null
                ));
            }
            if (selectedNode.get() != null && selectedNode.get() instanceof ShrunkGroovyNode) {
                var shrunk = (ShrunkGroovyNode) selectedNode.get();
                shrunk.nodes().forEach(n -> {
                    selectedNode.set(n);
                    deleteSelected();
                });
                nodes.remove(shrunk);
                group.getChildren().remove(shrunk);
                graph.removeNode(shrunk.model());
            } else if (selectedNode.get() != null && selectedNode.get().model() != graph.source()) {
                nodes.remove(selectedNode.get());
                var toRemove = new HashSet<Pair<GroovyNode, Ports>>();
                for (var p : lines.keySet()) {
                    var line = lines.get(p).getValue();
                    var target = lines.get(p).getKey();
                    if (p.getKey() == selectedNode.get() || target == selectedNode.get()) {
                        toRemove.add(p);
                        group.getChildren().remove(line);
                    }
                } // remove lines connected from or to that node
                lines.keySet().removeAll(toRemove);

                group.getChildren().remove(selectedNode.get());
                graph.removeNode(selectedNode.get().model());
            }
            updateCodePane();
        }


        private void initializeGridPane() {
            var col1 = new ColumnConstraints();
            col1.setPercentWidth(30);
            var col2 = new ColumnConstraints();
            col2.setPercentWidth(50);
            var col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            root.getColumnConstraints().addAll(col1, col2, col3);
        }

        private void connectNodes(GroovyNode node1, Ports port, GroovyNode node2) {
            connectNodes(node1, port, node2, false);
        }
        private void connectNodes(GroovyNode node1, Ports port, GroovyNode node2, boolean useExistingEdge) {
            if (node1 == node2 || node2 instanceof ShrunkGroovyNode || isInsideShrunkNode(node2)) return;
            try {
                var p = node1.portXY(port);
                Line edgeLine = new Line(p.getKey(), p.getValue(), node2.getCenterX(), node2.getCenterY());
                edgeLine.setStrokeWidth(3);
                edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
                edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
                edgeLine.setOnMouseClicked(e -> selectedEdge.set(new Pair<>(node1, port)));

                if(!useExistingEdge) graph.addEdge(factory.createEdge(node1.model(), port, node2.model()));
                lines.put(new Pair<>(node1, port), new Pair<>(node2, edgeLine));

                group.getChildren().addAll(edgeLine);
                edgeLine.toBack();
                updateCodePane();
            } catch (Throwable t) {
                ErrorWindow.display("Error while connecting nodes", t.toString());
            }
        }

        private boolean isInsideShrunkNode(GroovyNode node) {
            return nodes.stream()
                    .filter(n -> n instanceof ShrunkGroovyNode)
                    .flatMap(n -> ((ShrunkGroovyNode) n).nodes().stream())
                    .anyMatch(n -> n == node);
        }

        private void createNode(GroovyNode node) {
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
                updateCodePane();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        private void nodeMousePressedHandler(MouseEvent t) {
            GroovyNode node = (GroovyNode) t.getSource();

            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            if (node.inner().contains(t.getX(), t.getY())) {
                draggingPurpose = DRAG_PURPOSE.CHANGE_POS;
                orgTranslateX = node.getTranslateX();
                orgTranslateY = node.getTranslateY();
            } else if (node.contains(t.getX(), t.getY())) {
                var p = node.findPortNear(t.getX(), t.getY());
                if (p != null) {
                    draggingPurpose = DRAG_PURPOSE.CONNECT_LINE;
                    edgeFrom = new Pair<>(node, p);
                    var xy = node.portXY(p);
                    tmpLine = new Line(xy.getKey(), xy.getValue(), xy.getKey(), xy.getValue());
                    tmpLine.setStrokeWidth(3);
                    tmpLine.getStrokeDashArray().addAll(20d, 20d);
                    group.getChildren().add(tmpLine);
                }
            }
        }

        private void nodeMouseReleasedHandler(MouseEvent t) {
            if (draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
                for (var n : nodes) {
                    if (n.localToScreen(n.getBoundsInLocal()).contains(t.getScreenX(), t.getScreenY())) {
                        connectNodes(edgeFrom.getKey(), edgeFrom.getValue(), n);
                        break;
                    }
                }
                group.getChildren().remove(tmpLine);
            } else if (draggingPurpose == DRAG_PURPOSE.CHANGE_POS) {
                double offsetX = t.getSceneX() - orgSceneX;
                double offsetY = t.getSceneY() - orgSceneY;
                GroovyNode node = (GroovyNode) t.getSource();
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

                GroovyNode node = (GroovyNode) t.getSource();

                node.setTranslateX(newTranslateX);
                node.setTranslateY(newTranslateY);

                if (node instanceof ShrunkGroovyNode) {
                    ((ShrunkGroovyNode) node).nodes().forEach(n -> {
                        n.setTranslateX(newTranslateX);
                        n.setTranslateY(newTranslateY);
                    });
                }

                updateLocations(node);
            } else if (draggingPurpose == DRAG_PURPOSE.CONNECT_LINE) {
                var xy = edgeFrom.getKey().portXY(edgeFrom.getValue());
                tmpLine.setEndX(xy.getKey() + offsetX);
                tmpLine.setEndY(xy.getValue() + offsetY);
            }
        }

        private void updateLocations(GroovyNode n) {
            if (n instanceof ShrunkGroovyNode)
                ((ShrunkGroovyNode) n).nodes().forEach(this::updateLocations);

            for (var p : lines.keySet()) {
                var from = p.getKey();
                var port = p.getValue();
                var tmp = lines.get(p);
                if (tmp != null) {
                    var to = tmp.getKey();
                    var line = tmp.getValue();

                    if (to == n || from == n) {
                        var portXY = from.portXY(port);
                        line.setStartX(portXY.getKey());
                        line.setStartY(portXY.getValue());

                        line.setEndX(to.getCenterX());
                        line.setEndY(to.getCenterY());
                    }
                }
            }
        }

        private void updateCodePane() {
            try {
                codePane.setText(graph.transformToGroovy().get());
            } catch (Throwable t) {
                codePane.setText(t.toString());
            }
        }

        @Override
        public GridPane getView() {
            return root;
        }
    }
}


