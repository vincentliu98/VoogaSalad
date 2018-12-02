package graphUI.groovy;

import authoringInterface.spritechoosingwindow.PopUpWindow;
import frontendUtils.Try;
import groovy.api.BlockGraph;
import groovy.api.GroovyFactory;
import groovy.api.Ports;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Playground for testing graph function
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author jl729
 */

public class GroovyPaneFactory {
    public static final Double WIDTH = 1200.0;
    public static final Double HEIGHT = 800.0;
    public static final Double ICON_WIDTH = 30.;
    public static final Double ICON_HEIGHT = 30.;

    private GroovyNodeFactory nodeFactory;
    private GroovyFactory factory;
    private Stage primaryStage;

    private enum DRAG_PURPOSE {
        NOTHING,
        CHANGE_POS,
        CONNECT_LINE
    }

    public GroovyPaneFactory(Stage primaryStage, GroovyFactory factory) {
        this.primaryStage = primaryStage;
        this.nodeFactory = new GroovyNodeFactory(factory);
        this.factory = factory;
    }

    public GroovyPaneFactory withStage(Stage anotherStage) {
        primaryStage = anotherStage;
        return this;
    }

    public GroovyPane gen() { return new GroovyPane(primaryStage); }

    public class GroovyPane extends PopUpWindow {
        private DRAG_PURPOSE draggingPurpose = DRAG_PURPOSE.NOTHING;
        private double orgSceneX, orgSceneY;
        private double orgTranslateX, orgTranslateY;

        private GridPane root = new GridPane();
        private Pane graphBox = new Pane();
        private Group group = new Group();
        private ScrollPane itemBox = new ScrollPane();
        private Scene myScene;
        private double newNodeX;
        private double newNodeY;

        private Map<Pair<GroovyNode, Ports>, Pair<GroovyNode, Line>> lines;
        private Set<GroovyNode> nodes;

        private BlockGraph graph;
        private String currentDragType;
        private boolean currentFetchArg;
        private Pair<GroovyNode, Ports> edgeFrom;
        private Line tmpLine;

        private double selectionX, selectionY;
        private Rectangle selection;

        private TextArea codePane = new TextArea();

        private SimpleObjectProperty<Pair<GroovyNode, Ports>> selectedEdge;
        private SimpleObjectProperty<GroovyNode> selectedNode;

        public GroovyPane(Stage primaryStage) {
            super(primaryStage);
            graph = factory.createGraph();

            codePane.setEditable(false);
            codePane.setWrapText(true);

            lines = new HashMap<>();
            nodes = new HashSet<>();
            createNode(nodeFactory.source(graph.source(), WIDTH-200, HEIGHT+50));

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

            dialog.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
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
                    if(selectedNode.get() != null && selectedNode.get() instanceof ShrunkGroovyNode) {
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
                        if(selected.size() > 1) {
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
            showWindow();
        }

        @Override
        public void showWindow() {
            dialog.setScene(myScene);
            dialog.show();
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
            setupGraphbox();
            initializeGridPane();
            initializeItemBox();
            root.add(itemBox, 0, 0);
            HBox.setHgrow(itemBox, Priority.ALWAYS);
            var graphBoxWrapper = new ScrollPane();
            graphBoxWrapper.setVvalue(graphBoxWrapper.getVmax()/2);
            graphBoxWrapper.setHvalue(graphBoxWrapper.getHmax()/2);
            graphBoxWrapper.setContent(graphBox);
            root.add(graphBoxWrapper, 1, 0);
            root.add(codePane, 2, 0);

            graphBox.getChildren().add(selection);
            setupSelectionListener();

            myScene = new Scene(root, WIDTH, HEIGHT);
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
                if(draggingPurpose == DRAG_PURPOSE.NOTHING) {
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

            vbox.getChildren().addAll(
                IconLoader.loadControls(img -> type -> fetchArg -> draggableGroovyIcon(img, type, fetchArg))
            );
            vbox.getChildren().addAll(
                IconLoader.loadBinaries(img -> type -> fetchArg -> draggableGroovyIcon(img, type, fetchArg))
            );
            vbox.getChildren().addAll(
                IconLoader.loadLiterals(img -> type -> fetchArg -> draggableGroovyIcon(img, type, fetchArg))
            );
            vbox.getChildren().addAll(
                IconLoader.loadFunctions(img -> type -> fetchArg -> draggableGroovyIcon(img, type, fetchArg))
            );
            vbox.getChildren().addAll(
                IconLoader.loadGameMethods(img -> type -> fetchArg -> draggableGroovyIcon(img, type, fetchArg))
            );

            vbox.setSpacing(10);
            vbox.getChildren().forEach(c -> {
                if (c instanceof HBox) ((HBox) c).setSpacing(5);
            });

            itemBox.setContent(vbox);
            itemBox.setMinHeight(HEIGHT);
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
                    String arg = "";
                    if (currentFetchArg) {
                        var dialog = new TextInputDialog();
                        dialog.setHeaderText("Type the parameter to initialize " + currentDragType);
                        arg = dialog.showAndWait().get();
                    }
                    createNode(nodeFactory.toModel(currentDragType, newNodeX, newNodeY, arg).get());
                } catch (Throwable t) {
                    displayError(t.toString());
                }
            }
            event.setDropCompleted(success);
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

            return view;
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
            if (node1 == node2 || node2 instanceof ShrunkGroovyNode || isInsideShrunkNode(node2)) return;
            try {
                var p = node1.portXY(port);
                Line edgeLine = new Line(p.getKey(), p.getValue(), node2.getCenterX(), node2.getCenterY());
                edgeLine.setStrokeWidth(3);
                edgeLine.setOnMouseEntered(e -> myScene.setCursor(Cursor.HAND));
                edgeLine.setOnMouseExited(e -> myScene.setCursor(Cursor.DEFAULT));
                edgeLine.setOnMouseClicked(e -> selectedEdge.set(new Pair<>(node1, port)));

                graph.addEdge(factory.createEdge(node1.model(), port, node2.model()));
                lines.put(new Pair<>(node1, port), new Pair<>(node2, edgeLine));

                group.getChildren().addAll(edgeLine);
                edgeLine.toBack();
                updateCodePane();
            } catch (Throwable t) {
                displayError(t.toString());
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
                node.setOnMouseClicked(e -> {
                    if (e.getClickCount() == 1) selectedNode.set(node);
                    else if (e.getClickCount() >= 2) new NodeSettingWindow(new Stage());
                });
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

                if(node instanceof ShrunkGroovyNode) {
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

        private void displayError(String msg) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something's wrong");
            alert.setContentText(msg);
            alert.showAndWait();
        }


        private void updateLocations(GroovyNode n) {
            if(n instanceof ShrunkGroovyNode)
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

        public Try<String> toGroovy() { return graph.transformToGroovy(); }
    }
}


