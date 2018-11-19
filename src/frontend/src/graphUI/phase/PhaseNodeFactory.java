package graphUI.phase;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import phase.api.Phase;
import phase.api.PhaseDB;
import utils.Try;

public class PhaseNodeFactory {
    private PhaseDB db;
    public PhaseNodeFactory(PhaseDB db) { this.db = db; }

    public PhaseNode source(Phase phase, double xPos, double yPos) { return new PhaseNode(xPos, yPos, phase); }
    public Try<PhaseNode> gen(double xPos, double yPos, String name) {
        return db.createPhase(name).map(p -> new PhaseNode(xPos, yPos, p));
    }

    public class PhaseNode extends StackPane {
        private static final double NODE_RADIUS = 30;
        private static final double PADDING = 10;
        private static final int LABEL_SIZE = 12;
        private Phase model;
        private Circle circle;
        private Circle inner;
        private Label text;

        public PhaseNode(double xPos, double yPos, Phase model) {
            this.model = model;
            circle = new Circle(xPos, yPos, NODE_RADIUS);
            circle.setFill(Color.LIGHTBLUE);

            inner = new Circle(PADDING, PADDING, NODE_RADIUS-PADDING);
            inner.setFill(Color.TRANSPARENT);

            text = new Label(model.name());
            text.setFont(new Font(LABEL_SIZE));
            text.setTextFill(Color.WHITE);

            setLayoutX(xPos);
            setLayoutY(yPos);

            getChildren().addAll(circle, text, inner);
            inner.toFront();

            layout();
        }

        public Phase model() { return model; }
        public Circle inner() { return inner; }
        public double getCenterX() { return getLayoutX() + getTranslateX() + NODE_RADIUS; }
        public double getCenterY() { return getLayoutY() + getTranslateY() + NODE_RADIUS; }
    }
}
