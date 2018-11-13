package groovy.api;

import graph.Graph;
import groovy.graph.blocks.core.GroovyBlock;

public interface BlockGraph extends Graph<GroovyBlock, BlockEdge> {
    void addEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) throws Throwable;
    Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort, boolean canBeEmpty);
    Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort);
}
