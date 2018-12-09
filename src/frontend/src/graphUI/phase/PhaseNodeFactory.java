package graphUI.phase;

import authoringUtils.frontendUtils.Try;
import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import groovy.api.BlockGraph;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import phase.api.Phase;
import phase.api.PhaseDB;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A factory that produces a node in a phase graph
 *
 * @author Inchan Hwang
 */
public class PhaseNodeFactory {
    private PhaseDB db;
    private Function<BlockGraph, GroovyPane> genGroovyPane;

    public PhaseNodeFactory(PhaseDB db, Function<BlockGraph, GroovyPane> genGroovyPane) {
        this.db = db;
        this.genGroovyPane = genGroovyPane;
    }

    public PhaseNode source(Phase phase, double xPos, double yPos) {
        return new PhaseNode(xPos, yPos, phase, true);
    }
    public Try<PhaseNode> gen(double xPos, double yPos, String name) {
        return db.createPhase(name).map(p -> new PhaseNode(xPos, yPos, p, false));
    }

    public class PhaseNode extends StackPane {
        private static final double NODE_RADIUS = 30;
        private static final double PADDING = 10;
        private static final int LABEL_SIZE = 12;
        private Phase model;
        private Circle circle;
        private Circle inner;
        private Label text;
        private String name;
        private GroovyPane groovyPane;

        public PhaseNode(double xPos, double yPos, Phase model, boolean isSource) {
            this.model = model;
            name = model.name();

            circle = new Circle(xPos, yPos, NODE_RADIUS);
            circle.setFill(isSource ? Color.GRAY : Color.DIMGRAY);

            inner = new Circle(NODE_RADIUS, NODE_RADIUS, NODE_RADIUS-PADDING);
            inner.setFill((isSource ? Color.BLACK : Color.WHITE).darker());

            text = new Label(model.name());
            text.setFont(new Font(LABEL_SIZE));
            text.setTextFill(isSource ? Color.WHITE : Color.BLACK);

            setLayoutX(xPos);
            setLayoutY(yPos);

            getChildren().addAll(circle, text, inner);
            inner.toFront();
            text.toFront();
            text.setMouseTransparent(true);
            groovyPane = genGroovyPane.apply(model.exec());
            groovyPane.closeWindow();

            layout();
        }

        public Phase model() { return model; }
        public Circle inner() { return inner; }

        public String getName() {
            return name;
        }
        public double getCenterX() { return getLayoutX() + getTranslateX() + NODE_RADIUS; }
        public double getCenterY() { return getLayoutY() + getTranslateY() + NODE_RADIUS; }
        public double getX() { return getLayoutX() + getTranslateX(); }
        public double getY() { return getLayoutY() + getTranslateY(); }
        public void showGraph() { groovyPane.showWindow(); }
    }
}
