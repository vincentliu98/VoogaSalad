package groovy.api;

import authoringUtils.frontendUtils.Replicable;
import graph.Edge;
import groovy.graph.blocks.core.GroovyBlock;

/**
 * Represents a directed edge that starts from a port of the starting node
 */
public interface BlockEdge extends Edge<GroovyBlock>, Replicable<BlockEdge> {
    Ports fromPort();
}
