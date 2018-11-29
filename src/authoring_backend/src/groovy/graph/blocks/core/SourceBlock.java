package groovy.graph.blocks.core;

import groovy.api.BlockGraph;
import groovy.api.Ports;
import graph.SimpleNode;
import frontendUtils.Try;

import java.util.Set;

import static groovy.api.Ports.FLOW_OUT;

public class SourceBlock extends SimpleNode implements GroovyBlock<SourceBlock> {
    public SourceBlock() { super(); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT); }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        return graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
    }

    @Override
    public SourceBlock replicate() { return new SourceBlock(); }

    @Override
    public String name() { return "source"; }
}
