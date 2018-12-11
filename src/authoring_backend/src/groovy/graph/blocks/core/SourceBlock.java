package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Try;
import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.Map;
import java.util.Set;

import static groovy.api.Ports.FLOW_OUT;

public class SourceBlock extends SimpleNode implements GroovyBlock<SourceBlock> {
    public SourceBlock(double x, double y) {
        super(x, y);
    }

    @Override
    public Set<Ports> ports() {
        return Set.of(FLOW_OUT);
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        return graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
    }

    @Override
    public SourceBlock replicate() {
        return new SourceBlock(x(), y());
    }

    @Override
    public String name() {
        return "source";
    }

    @Override
    public Map<String, Object> params() {
        return null;
    }
}
