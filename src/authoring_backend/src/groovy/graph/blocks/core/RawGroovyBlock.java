package groovy.graph.blocks.core;

import groovy.api.BlockGraph;
import graph.SimpleNode;
import groovy.api.Try;
import groovy.api.Ports;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

public class RawGroovyBlock extends SimpleNode implements GroovyBlock<RawGroovyBlock> {
    private SimpleStringProperty src;

    public RawGroovyBlock(String src) {
        super();
        this.src = new SimpleStringProperty(src);
    }

    public SimpleStringProperty model() { return src; }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(src.get()); }

    @Override
    public RawGroovyBlock replicate() { return new RawGroovyBlock(src.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }

    @Override
    public SimpleStringProperty name() { return new SimpleStringProperty("raw"); }
}
