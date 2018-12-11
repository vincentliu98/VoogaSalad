package groovy.graph;

import graph.SimpleEdge;
import groovy.api.BlockEdge;
import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;

public class BlockEdgeImpl extends SimpleEdge<GroovyBlock> implements BlockEdge {
    private Ports fromPort;

    public BlockEdgeImpl(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        super(from, to);
        this.fromPort = fromPort;
    }

    @Override
    public BlockEdge replicate() {
        return new BlockEdgeImpl(from().replicate(), fromPort, to().replicate());
    }

    public Ports fromPort() {
        return fromPort;
    }
}
