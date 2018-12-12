package graphUI.groovy;

import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

/**
 * A StackPane that contains a single groovy block (rectangular shape).
 * <p>
 * Reference: https://stackoverflow.com/questions/46562957/define-object-position-at-runtime-with-javafx
 *
 * @author Inchan Hwang
 * @author jl729
 */
public class GroovyNode extends StackPane {
    private static final int PADDING = 10;
    private static final double magnetRadius = 20;
    private static final int PORT_RADIUS = 4;
    private GroovyBlock<?> model;
    private Rectangle rectangle;
    private Rectangle inner;
    private Label text;
    private List<Pair<Pos, Ports>> portPositions;

    GroovyNode(
            GroovyBlock<?> block,
            double width, double height,
            double labelSize,
            Color color,
            List<Pair<Pos, Ports>> portPositions
    ) {
        this(block, width, height, labelSize, color, portPositions, null);
    }

    GroovyNode(
            GroovyBlock<?> block,
            double width, double height,
            double labelSize,
            Color color,
            List<Pair<Pos, Ports>> portPositions,
            Map<Ports, String> portInfo
    ) {
        text = new Label(block.name());
        text.setFont(new Font(labelSize));
        text.setMaxWidth(width);
        text.setWrapText(true);
        text.setAlignment(Pos.CENTER);

        text.setTextFill(
                color.getRed() * 0.299 +
                        color.getGreen() * 0.587 +
                        color.getBlue() * 0.114 > 0.73 ? Color.BLACK : Color.WHITE
        );

        model = block;
        rectangle = new Rectangle(block.x(), block.x(), width, height);
        rectangle.setFill(color);

        inner = new Rectangle(PADDING, PADDING, width - 2 * PADDING, height - 2 * PADDING);
        inner.setFill(Color.TRANSPARENT);


        setLayoutX(block.x());
        setLayoutY(block.y());

        this.portPositions = portPositions;

        getChildren().addAll(rectangle, text, inner);
        inner.toFront();

        for (var p : portPositions) {
            var c = new Circle(PORT_RADIUS);
            setAlignment(c, p.getKey());

            Tooltip t;
            try {
                t = new Tooltip(portInfo.getOrDefault(p.getValue(), p.getValue().name()));
            } catch (Exception e) {
                t = new Tooltip(p.getValue().name());
            }

            Tooltip.install(c, t);
            t.setHideDelay(Duration.ZERO);
            t.setShowDelay(Duration.ZERO);
            getChildren().addAll(c);
        }

        layout();

    }

    public GroovyBlock model() {
        return model;
    }

    public Pair<Double, Double> portXY(Ports port) {
        Pos pos = null;
        for (var p : portPositions) if (p.getValue() == port) pos = p.getKey();

        double x = getCenterX();
        double y = getCenterY();

        switch (pos) {
            case TOP_LEFT:
                x = getCenterX() - rectangle.getWidth() / 2;
                y = getCenterY() - rectangle.getHeight() / 2;
                break;
            case TOP_CENTER:
                y = getCenterY() - rectangle.getHeight() / 2;
                break;
            case TOP_RIGHT:
                x = getCenterX() + rectangle.getWidth() / 2;
                y = getCenterY() - rectangle.getHeight() / 2;
                break;
            case CENTER_RIGHT:
                x = getCenterX() + rectangle.getWidth() / 2;
                break;
            case BOTTOM_CENTER:
                y = getCenterY() + rectangle.getHeight() / 2;
                break;
            case BOTTOM_RIGHT:
                x = getCenterX() + rectangle.getWidth() / 2;
                y = getCenterY() + rectangle.getHeight() / 2;
                break;
        }
        return new Pair<>(x, y);
    }

    public Ports findPortNear(double x, double y) {
        for (var p : model.ports()) {
            var pxy = portXY(p);
            double dx = pxy.getKey() - (getCenterX() - rectangle.getWidth() / 2) - x;
            double dy = pxy.getValue() - (getCenterY() - rectangle.getHeight() / 2) - y;
            double d2 = dx * dx + dy * dy;
            if (d2 < magnetRadius * magnetRadius) return p;
        }
        return null;
    }

    /**
     * Invisible inner box, that user can drag on to move the position of the rectangle
     */
    public Rectangle inner() {
        return inner;
    }

    public double getCenterX() {
        return getLayoutX() + getTranslateX() + rectangle.getWidth() / 2;
    }

    public void setCenterX(double x) {
        setCenterX(x, true);
    }

    public double getCenterY() {
        return getLayoutY() + getTranslateY() + rectangle.getHeight() / 2;
    }

    public void setCenterY(double y) {
        setCenterY(y, true);
    }

    public void setCenterX(double x, boolean modifyModel) {
        if (modifyModel) model.setXY(x - rectangle.getWidth() / 2, model.y());
        setLayoutX(x - rectangle.getWidth() / 2);
        setTranslateX(0);
    }

    public void setCenterY(double y, boolean modifyModel) {
        if (modifyModel) model.setXY(model.x(), y - rectangle.getHeight() / 2);
        setLayoutY(y - rectangle.getHeight() / 2);
        setTranslateY(0);
    }
}