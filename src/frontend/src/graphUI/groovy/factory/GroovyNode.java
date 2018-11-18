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
import java.util.List;
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
    private List<Pair<Pos, Ports>> portPositions;
    private List<GroovyNode> incomingNodes = new ArrayList<>();

    private static final int PORT_RADIUS = 10;

    public GroovyNode(GroovyBlock block,
                      double xPos, double yPos,
                      double width, double height,
                      Color color,
                      List<Pair<Pos, Ports>> portPositions
    ) {
        model = block;
        rectangle = new Rectangle(xPos, yPos, width, height);
        rectangle.setFill(color);
        text = new Label(block.name());
        text.setTextFill(Color.BLACK);

        setLayoutX(xPos);
        setLayoutY(yPos);

        this.portPositions = portPositions;

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
        node.registerIncoming(this);
    }
    public void registerIncoming(GroovyNode incoming) { incomingNodes.add(incoming); }

    public Pair<Double, Double> portXY(Ports port) {
        Pos pos = null;
        for(var p: portPositions) {
            if(p.getValue() == port) pos = p.getKey();
        }

        double x = getCenterX();
        double y = getCenterY();

        switch(pos) {
            case TOP_CENTER: y = getCenterY(); break;
            case BOTTOM_CENTER: y = getCenterY()+rectangle.getHeight()/2; break;
            case CENTER_LEFT: x = getCenterX(); break;
            case CENTER_RIGHT: x = getCenterX()+rectangle.getWidth()/2;
            case TOP_RIGHT:
                x = getCenterX()+rectangle.getWidth()/2;
                y = getCenterY()-rectangle.getHeight()/2;
                break;
            case BOTTOM_RIGHT:
                x = getCenterX()+rectangle.getWidth()/2;
                y = getCenterY()+rectangle.getHeight()/2;
                break;
        }
        return new Pair<>(x, y);
    }

    public double getX() { return getLayoutX() + getTranslateX(); }
    public double getY() { return getLayoutY() + getTranslateY(); }
    public double getCenterX() { return getX() + rectangle.getWidth()/2; }
    public double getCenterY() { return getY() + rectangle.getHeight()/2; }

    // Helper method for updating the position when the GroovyNode is dragged
    public void updateLocations() {
        for(var port : connectedNodes.keySet()) {
            Line l = connectedNodes.get(port).getValue();
            GroovyNode neighbor = connectedNodes.get(port).getKey();

            var p = portXY(port);
            l.setStartX(p.getKey());
            l.setStartY(p.getValue());

            l.setEndX(neighbor.getCenterX());
            l.setEndY(neighbor.getCenterY());
        }
        incomingNodes.forEach(GroovyNode::updateLocations);
    }
}