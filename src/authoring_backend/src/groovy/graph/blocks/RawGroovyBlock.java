package groovy.graph.blocks;

import groovy.graph.BlockGraph;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;

import java.util.Set;

public class RawGroovyBlock extends SimpleNode implements GroovyBlock<RawGroovyBlock> {
    private String src;
    public RawGroovyBlock(String src) {
        this.src = src;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(src); }

    @Override
    public RawGroovyBlock replicate() { return new RawGroovyBlock(src); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
