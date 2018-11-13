package groovy.api;

import essentials.Replicable;
import graph.Edge;
import groovy.graph.blocks.core.GroovyBlock;

public interface BlockEdge extends Edge<GroovyBlock>, Replicable<BlockEdge> {
    Ports fromPort();
}
