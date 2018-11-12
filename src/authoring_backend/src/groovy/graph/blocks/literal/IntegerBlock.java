package groovy.graph.blocks.literal;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Set;

public class IntegerBlock extends SimpleNode implements GroovyBlock<IntegerBlock> {
    private SimpleIntegerProperty value;

    public IntegerBlock() {
        value = new SimpleIntegerProperty(0);
    }

    public IntegerBlock(int value) {
        this.value = new SimpleIntegerProperty(value);
    }

    public SimpleIntegerProperty model() { return value; }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(String.valueOf(value.get())); }

    @Override
    public IntegerBlock replicate() { return new IntegerBlock(value.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
