package graphUI.groovy;

import groovy.graph.blocks.core.RawGroovyBlock;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Shrunken representation of set of GroovyNode
 */
public class ShrunkGroovyNode extends GroovyNode {
    private List<GroovyNode> nodes;
    private List<Pair<Double, Double>> originalPos;
    public ShrunkGroovyNode(String description, double xPos, double yPos, double width, double height, double labelSize, Color color, Set<GroovyNode> nodes) {
        super(new RawGroovyBlock(description), xPos, yPos, width, height, labelSize, color, List.of());
        setCenterX(xPos);
        setCenterY(yPos);
        this.nodes = new ArrayList<>(nodes);
        originalPos = this.nodes.stream().map(n ->
            new Pair<>(n.getCenterX()-getCenterX(), n.getCenterY()-getCenterY())).collect(Collectors.toList()
        );
        this.nodes.forEach(n -> n.setMouseTransparent(true));
        updatePosition();
    }

    public void updatePosition() {
        this.nodes.forEach(n -> {
            n.setCenterX(getCenterX());
            n.setCenterY(getCenterY());
        });
    }

    public List<GroovyNode> nodes() { return nodes; }

    public void unShrink() {
        this.nodes.forEach(n -> n.setMouseTransparent(false));
        for(int i = 0 ; i < originalPos.size() ; i ++) {
            nodes.get(i).setCenterX(originalPos.get(i).getKey()+getCenterX());
            nodes.get(i).setCenterY(originalPos.get(i).getValue()+getCenterY());
        }
    }
}
