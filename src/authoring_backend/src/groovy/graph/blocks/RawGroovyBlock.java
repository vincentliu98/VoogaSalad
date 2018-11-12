package groovy.graph.blocks;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;

import java.util.Set;

public class RawGroovyBlock extends SimpleNode implements GroovyBlock<RawGroovyBlock> {
    private String src;
    public RawGroovyBlock(String name, String src) {
        super(name);
        this.src = src;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(src); }

    @Override
    public RawGroovyBlock replicate() {
        return new RawGroovyBlock(name().get(), src);
    }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
