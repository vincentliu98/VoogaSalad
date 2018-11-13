package groovy.graph.blocks.core;

import groovy.graph.BlockGraph;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;

import java.util.Set;

import static groovy.graph.Ports.FLOW_OUT;

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
}
