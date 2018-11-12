package groovy.graph.blocks.literal;

import groovy.graph.BlockGraph;
import groovy.graph.blocks.GroovyBlock;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Set;

public class DoubleBlock extends SimpleNode implements GroovyBlock<DoubleBlock> {
    private SimpleDoubleProperty value;

    public DoubleBlock() {
        value = new SimpleDoubleProperty(0);
    }

    public DoubleBlock(double value) {
        this.value = new SimpleDoubleProperty(value);
    }

    public SimpleDoubleProperty model() { return value; }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(String.valueOf(value.get())); }

    @Override
    public DoubleBlock replicate() { return new DoubleBlock(value.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
