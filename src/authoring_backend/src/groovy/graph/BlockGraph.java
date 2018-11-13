package groovy.graph;

import graph.SimpleEdge;
import graph.SimpleGraph;
import groovy.Try;
import groovy.graph.blocks.GroovyBlock;
import groovy.graph.blocks.core.RawGroovyBlock;

import java.util.HashSet;
import java.util.Set;

/**
 *  BlockGraph represents a piece of groovy code with GroovyBlocks and BlockEdges
 *  It provides a copy method that creates a deep copy of the given vertices and edges.
 */
public class BlockGraph extends SimpleGraph<GroovyBlock, BlockEdge> {
    public BlockGraph() {
        super();
    }

    /**
     *  When adding an edge, the graph will check
     *  1. If the edge is syntactically correct
     *  2. Whether there's already an edge from the port
     *  It will throw an exception if it fails these checks
     */
    public void addEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) throws Throwable {
        BlockEdge.gen(from, fromPort, to).flatMap(edge -> {
            if(get(from).stream().noneMatch(p -> p.fromPort() == fromPort))
                return Try.success(addEdge(edge));
            else return Try.failure(new PortAlreadyFilledException(from, fromPort));
        }).get();
    }

    public static BlockGraph copy(Set<GroovyBlock> vertices, Set<BlockEdge> edges) {
        var graph = new BlockGraph();
        var addedVertices = new HashSet<GroovyBlock>();
        for(var edge: edges) {
            graph.addEdge(edge.replicate());
            addedVertices.add(edge.from());
            addedVertices.add(edge.to());
        }
        vertices.stream()
                .filter(p -> !addedVertices.contains(p))
                .forEach(v -> graph.addNode(v.replicate()));
        return graph;
    }

    /**
     *  Tries to find the GroovyBlock that's on the other side.
     */
    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort, boolean canBeEmpty) {
        var find = Try.apply(() -> get(from)
                .stream()
                .filter(e -> e.fromPort() == fromPort)
                .findFirst()
                .orElseThrow(Try.supplyThrow(new PortNotConnectedException(from, fromPort))));

        if(find.isFailure() && canBeEmpty) { // handle can-be-empty s
            return Try.success(new RawGroovyBlock(""));
        } else return find.map(SimpleEdge::to);
    }

    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort) {
        return findTarget(from, fromPort, fromPort == Ports.FLOW_OUT); // FLOW_OUT can be empty by default
    }
}
