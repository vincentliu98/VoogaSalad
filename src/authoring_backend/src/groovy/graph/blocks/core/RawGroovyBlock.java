package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Try;
import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.Set;

public class RawGroovyBlock extends SimpleNode implements GroovyBlock<RawGroovyBlock> {
    private String src;

    public RawGroovyBlock(String src) {
        super();
        this.src = src;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(src); }

    @Override
    public RawGroovyBlock replicate() { return new RawGroovyBlock(src); }

    @Override
    public Set<Ports> ports() { return Set.of(); }

    @Override
    public String name() { return src; }
}
