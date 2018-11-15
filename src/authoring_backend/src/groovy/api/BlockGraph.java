package groovy.api;

import graph.Graph;
import groovy.graph.blocks.core.GroovyBlock;
import groovy.graph.blocks.core.SourceBlock;
import javafx.util.Pair;
import utils.Try;

import java.util.Set;

/**
 *  A basic graph of GroovyBlocks and BlockEdges representing some groovy code.
 */
public interface BlockGraph extends Graph<GroovyBlock, BlockEdge> {
    /**
     *  The initial source Node
     */
    SourceBlock source();

    /**
     *  Transforms entire graph into a groovy code
     */
    Try<String> transformToGroovy();

    /**
     *  Adds copy of existing vertices and edges to the graph
     *  and returns the newly added vertices and edges;
     */
    Pair<Set<GroovyBlock>, Set<BlockEdge>> addSubset(Set<GroovyBlock> vertices, Set<BlockEdge> edges);

    /**
     *  Finds the target block of an edge starting from specific port of a GroovyBlock.
     *  if canBeEmpty is true, it returns an createGraph GroovyBlock.
     */
    Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort, boolean canBeEmpty);

    /**
     *  Finds the target block of an edge starting from specific port of a GroovyBlock.
     *  if fromPort is FLOW_OUT, it defaults to canBeEmpty = true.
     */
    Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort);
}
