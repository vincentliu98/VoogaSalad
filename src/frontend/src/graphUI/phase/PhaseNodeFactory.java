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

/**
 * A factory that produces a node in a phase graph
 *
 * @author Inchan Hwang
 */
public class PhaseNodeFactory {
    public static final double PHASE_NODE_RADIUS = 30;
    private PhaseDB db;
    private Function<BlockGraph, GroovyPane> genGroovyPane;

    public PhaseNodeFactory(PhaseDB db, Function<BlockGraph, GroovyPane> genGroovyPane) {
        this.db = db;
        this.genGroovyPane = genGroovyPane;
    }

    public Try<Phase> toModel(double xPos, double yPos, String name) {
        return db.createPhase(xPos, yPos, name, false);
    }

    public PhaseNode toView(Phase phase) {
        return new PhaseNode(phase.x(), phase.y(), phase);
    }

    public Try<PhaseNode> makeNode(double xPos, double yPos, String name) {
        return toModel(xPos, yPos, name).map(this::toView);
    }

    public class PhaseNode extends StackPane {
        private static final double PADDING = 10;
        private static final int LABEL_SIZE = 12;
        private Phase model;
        private Circle circle;
        private Circle inner;
        private Label text;
        private String name;
        private GroovyPane groovyPane;

        public PhaseNode(double xPos, double yPos, Phase model) {
            this.model = model;
            name = model.name();

            circle = new Circle(xPos, yPos, PHASE_NODE_RADIUS);
            circle.setFill(model.isSource() ? Color.GRAY : Color.DIMGRAY);

            inner = new Circle(PHASE_NODE_RADIUS, PHASE_NODE_RADIUS, PHASE_NODE_RADIUS - PADDING);
            inner.setFill((model.isSource() ? Color.BLACK : Color.WHITE).darker());

            text = new Label(model.name());
            text.setFont(new Font(LABEL_SIZE));
            text.setTextFill(model.isSource() ? Color.WHITE : Color.BLACK);

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

        public Phase model() {
            return model;
        }

        public Circle inner() {
            return inner;
        }

        public String getName() {
            return name;
        }

        public double getCenterX() {
            return getLayoutX() + getTranslateX() + PHASE_NODE_RADIUS;
        }

        public double getCenterY() {
            return getLayoutY() + getTranslateY() + PHASE_NODE_RADIUS;
        }

        public double getX() {
            return getLayoutX() + getTranslateX();
        }

        public double getY() {
            return getLayoutY() + getTranslateY();
        }

        public void showGraph() {
            groovyPane.showWindow();
        }
    }
}
