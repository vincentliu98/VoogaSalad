package graphUI.groovy.factory;

import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Draggable circle Nodes
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author jl729
 */
public class GroovyNode extends StackPane {
    private GroovyBlock model;
    private Rectangle rectangle;
    private Label text;

    private Map<Ports, Pair<GroovyNode, Line>> connectedNodes = new HashMap<>();
    private ArrayList<Line> edgesList = new ArrayList<>();

    private static final int PORT_RADIUS = 10;


    public GroovyNode(GroovyBlock block,
                      double xPos, double yPos,
                      double width, double height,
                      Color color,
                      ArrayList<Pair<Pos, Ports>> portPositions
    ) {
        model = block;
        rectangle = new Rectangle(xPos, yPos, width, height);
        rectangle.setFill(color);
        text = new Label(block.name());
        text.setTextFill(Color.WHITE);
        setLayoutX(xPos);
        setLayoutY(yPos);

        getChildren().addAll(rectangle, text);
        portPositions.forEach(p -> {
                        var c = new Circle(PORT_RADIUS);
                        setAlignment(c, p.getKey());
                        var label = new Label(p.getValue().name());
                        setAlignment(label, p.getKey());
                        getChildren().addAll(c, label);
        });

        layout();
    }

    public GroovyBlock model() { return model; }
    public void addNeighbor(Ports port, GroovyNode node, Line edge) {
        connectedNodes.put(port, new Pair<>(node, edge));
    }
    public Map<Ports, Pair<GroovyNode, Line>> getConnectedNodes() { return connectedNodes; }
    public double getX() { return getLayoutX() + getTranslateX(); }
    public double getY() { return getLayoutY() + getTranslateY(); }
    public double getCenterX() { return getX() + getWidth()/2; }
    public double getCenterY() { return getY() + getHeight()/2; }

}