package graphUI.groovy.factory;

import groovy.api.GroovyFactory;
import groovy.api.Ports;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class GroovyNodeFactory {
    private GroovyFactory factory;
    public GroovyNodeFactory(GroovyFactory factory) { this.factory = factory; }

    public GroovyNode forEach(double xPos, double yPos) {
        var block = factory.forEachBlock("i"); // for now
         // FOREACH_LIST, FOREACH_BODY, FLOW_OUT
        var list = new ArrayList<>(
            List.of(
                new Pair<>(Pos.CENTER_LEFT, Ports.FOREACH_LIST),
                new Pair<>(Pos.CENTER_RIGHT, Ports.FOREACH_BODY),
                new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
            ));

        return new GroovyNode(block, xPos, yPos, 200, 100, Color.LIGHTBLUE, list);
    }
}
