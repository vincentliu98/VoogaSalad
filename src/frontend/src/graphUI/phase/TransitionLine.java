package graphUI.phase;

import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import phase.api.GameEvent;
import graphUI.phase.PhaseNodeFactory.PhaseNode;

import java.util.function.Consumer;

public class TransitionLine extends Line {
    private PhaseNode start, end;
    private GameEvent model;
    private Text label;

    public TransitionLine(
        double x, double y, double x2, double y2,
        PhaseNode start, GameEvent event, PhaseNode end,
        Consumer<Node> addChildren
    ) {
        super(x, y, x2, y2);
        this.start = start;
        this.end = end;
        this.label = new Text(event.eventType());
        addChildren.accept(this.label);

        startXProperty().addListener((e, o, n) -> repositionLabel());
        startYProperty().addListener((e, o, n) -> repositionLabel());
        endXProperty().addListener((e, o, n) -> repositionLabel());
        endYProperty().addListener((e, o, n) -> repositionLabel());
    }

    private void repositionLabel() {
        double mx = (getStartX() + getEndX())/2;
        double my = (getStartY() + getEndY())/2;
        label.setX(mx);
        label.setY(my);
    }
    public PhaseNode start() { return start; }
    public PhaseNode end() { return end; }
    public GameEvent trigger() { return model; }
}

