package authoring_backend.groovy.graph;

import authoring_backend.graph.SimpleGraph;
import authoring_backend.groovy.graph.blocks.GroovyBlock;

import java.util.Set;

/**
 *  BlockGraph is a replicable,
 */
public class BlockGraph extends SimpleGraph<GroovyBlock, BlockEdge> {
    public boolean addEdge(GroovyBlock from, int fromPort, GroovyBlock to, int toPort) {
        var e = BlockEdge.gen(from, fromPort, to, toPort);
        e.forEach(this::addEdge);
        return e.isSuccess();
    }

    public static BlockGraph copy(Set<GroovyBlock> vertices, Set<BlockEdge> edges) {
        var graph = new BlockGraph();
        for(var vertex: vertices) graph.addNode(vertex.replicate());
        for(var edge: edges) graph.addEdge(edge.replicate());
        return graph;
    }
}
