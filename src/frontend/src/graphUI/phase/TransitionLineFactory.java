package graphUI.phase;

import graphUI.groovy.GroovyPaneFactory.GroovyPane;
import graphUI.phase.PhaseNodeFactory.PhaseNode;
import groovy.api.BlockGraph;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import phase.api.GameEvent;
import phase.api.Transition;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *TransitionLineFactory
 *  -Handling the lines (i.e arrows)
 * @author Amy
 */
public class TransitionLineFactory {
    private Function<BlockGraph, GroovyPane> genGroovyPane;
    private Consumer<Node> removeFromScreen;
    private Consumer<Node> addToScreen;

    public TransitionLineFactory(
        Function<BlockGraph, GroovyPane> genGroovyPane,
        Consumer<Node> addToScreen,
        Consumer<Node> removeFromScreen
    ) {
        this.genGroovyPane = genGroovyPane;
        this.removeFromScreen = removeFromScreen;
        this.addToScreen = addToScreen;
    }

    public TransitionLine gen(
        double x, double y, double x2, double y2, int cnt,
        Transition transition, PhaseNode node1, PhaseNode node2
    ) {
        return new TransitionLine(x, y, x2, y2, cnt, transition, node1, node2);
    }

    public class TransitionLine extends Line {
        private static final int MAX_CNT = 5;
        private static final double ARROW_LENGTH = 20;
        private static final double ARROW_OFFSET = 30;

        private Line arrowL, arrowR;
        private Transition model;
        private PhaseNode node1, node2;
        private Text label;
        private int cnt;
        private GroovyPane groovyPane;

        public TransitionLine(
            double x, double y, double x2, double y2, int cnt,
            Transition transition, PhaseNode node1, PhaseNode node2
        ) {
            super(x, y, x2, y2);
            this.model = transition;
            this.node1 = node1;
            this.node2 = node2;
            this.label = new Text(transition.trigger().toString());
            this.cnt = cnt;

            arrowL = new Line();
            arrowR = new Line();
            arrowL.setStrokeWidth(3);
            arrowR.setStrokeWidth(3);

            addToScreen.accept(this);
            addToScreen.accept(this.label);
            addToScreen.accept(this.arrowL);
            addToScreen.accept(this.arrowR);

            repositionLabel();
            repositionArrow();

            groovyPane = genGroovyPane.apply(transition.guard());
            groovyPane.closeWindow();

            startXProperty().addListener((e, o, n) -> update());
            startYProperty().addListener((e, o, n) -> update());
            endXProperty().addListener((e, o, n) -> update());
            endYProperty().addListener((e, o, n) -> update());
        }

        private void update() {
            repositionLabel();
            repositionArrow();
        }

        private void repositionLabel() {
            double dx = getEndX() - getStartX();
            double dy = getEndY() - getStartY();
            double mx = dx * (cnt + 1) / MAX_CNT + getStartX();
            double my = dy * (cnt + 1) / MAX_CNT + getStartY();
            label.setX(mx);
            label.setY(my);
        }

        private void repositionArrow() {
            var dx = getEndX() - getStartX();
            var dy = getEndY() - getStartY();
            var dist = Math.sqrt(dx * dx + dy * dy);

            var pinX = dx * (dist - ARROW_OFFSET) / dist + getStartX();
            var pinY = dy * (dist - ARROW_OFFSET) / dist + getStartY();

            var th = Math.atan2(dy, dx);

            var lX = ARROW_LENGTH * Math.cos(Math.PI + th + Math.PI / 6) + pinX;
            var lY = ARROW_LENGTH * Math.sin(Math.PI + th + Math.PI / 6) + pinY;
            var rX = ARROW_LENGTH * Math.cos(Math.PI + th - Math.PI / 6) + pinX;
            var rY = ARROW_LENGTH * Math.sin(Math.PI + th - Math.PI / 6) + pinY;

            arrowL.setStartX(pinX);
            arrowL.setStartY(pinY);
            arrowL.setEndX(lX);
            arrowL.setEndY(lY);
            arrowR.setStartX(pinX);
            arrowR.setStartY(pinY);
            arrowR.setEndX(rX);
            arrowR.setEndY(rY);
        }

        public void removeFromScreen() {
            removeFromScreen.accept(this);
            removeFromScreen.accept(label);
            removeFromScreen.accept(arrowL);
            removeFromScreen.accept(arrowR);
        }

        public void setColor(Color c) {
            this.setStroke(c);
            arrowL.setStroke(c);
            arrowR.setStroke(c);
            label.setStroke(c);
        }

        public PhaseNode start() { return node1; }
        public PhaseNode end() { return node2; }
        public GameEvent trigger() { return model.trigger(); }
        public Text label() { return label; }
        public int cnt() { return cnt; }
        public void showGraph() { groovyPane.showWindow(); }
    }
}
