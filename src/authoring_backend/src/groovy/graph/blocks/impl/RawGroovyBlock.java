package groovy.graph.blocks.impl;

import groovy.graph.BlockGraph;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

public class RawGroovyBlock extends SimpleNode implements GroovyBlock<RawGroovyBlock> {
    private SimpleStringProperty src;

    public RawGroovyBlock(String src) {
        super();
        this.src = new SimpleStringProperty(src);
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(src.get()); }

    @Override
    public RawGroovyBlock replicate() { return new RawGroovyBlock(src.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
