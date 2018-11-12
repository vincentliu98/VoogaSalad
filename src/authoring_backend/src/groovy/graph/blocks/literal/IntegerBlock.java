package authoring_backend.src.groovy.graph.blocks.literal;

import authoring_backend.src.groovy.graph.BlockGraph;
import authoring_backend.src.groovy.graph.blocks.GroovyBlock;
import authoring_backend.src.graph.SimpleNode;
import authoring_backend.src.groovy.Try;
import authoring_backend.src.groovy.graph.Ports;
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
