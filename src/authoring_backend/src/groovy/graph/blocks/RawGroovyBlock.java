package authoring_backend.src.groovy.graph.blocks;

import authoring_backend.src.groovy.graph.BlockGraph;
import authoring_backend.src.graph.SimpleNode;
import authoring_backend.src.groovy.Try;
import authoring_backend.src.groovy.graph.Ports;

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
