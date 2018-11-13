package groovy.graph;

import graph.Edge;
import graph.SimpleGraph;
import groovy.api.BlockEdge;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import groovy.api.Try;
import groovy.graph.blocks.core.GroovyBlock;
import groovy.graph.blocks.core.RawGroovyBlock;

import java.util.HashSet;
import java.util.Set;

/**
 *  BlockGraph represents a piece of groovy code with GroovyBlocks and BlockEdges
 *  It provides a copy method that creates a deep copy of the given vertices and edges.
 */
public class BlockGraphImpl extends SimpleGraph<GroovyBlock, BlockEdge> implements BlockGraph {
    public BlockGraphImpl() { super(); }

    /**
     *  When adding an edge, the graph will check
     *  1. If the edge is syntactically correct
     *  2. Whether there's already an edge from the port
     *  It will throw an exception if it fails these checks
     */
    public void addEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) throws Throwable {
        makeEdge(from, fromPort, to).flatMap(edge -> {
            if(get(from).stream().noneMatch(p -> p.fromPort() == fromPort))
                return Try.success(addEdge(edge));
            else return Try.failure(new PortAlreadyFilledException(from, fromPort));
        }).get();
    }

    /**
     *  Initializes the graph with the given vertices and edges (their deep copy);
     */
    public static BlockGraph fromSubset(Set<GroovyBlock> vertices, Set<BlockEdge> edges) {
        var graph = new BlockGraphImpl();

        var addedVertices = new HashSet<GroovyBlock>();
        for (var edge : edges) {
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
     * This is the only way for the outside world to create BlockEdge
     * @return A BlockEdge, that may have failed the Syntax Checking or not.
     */
    public static Try<BlockEdge> makeEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        return Try.apply(() -> typeCheck(new BlockEdgeImpl(from, fromPort, to)));
    }

    /**
     * Does a basic type checking for the new edge
     */
    private static BlockEdge typeCheck(BlockEdge e) throws Exception {
        if("Something's wrong".length() == 1) throw new Exception("Type check isFailure for edge: " + e);
        return e;
    }

    /**
     *  Tries to find the GroovyBlock that's on the other side of a specific port.
     */
    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort, boolean canBeEmpty) {
        var find = Try.apply(() -> get(from)
                .stream()
                .filter(e -> e.fromPort() == fromPort)
                .findFirst()
                .orElseThrow(Try.supplyThrow(new PortNotConnectedException(from, fromPort))));

        if(find.isFailure() && canBeEmpty) { // handle can-be-empty s
            return Try.success(new RawGroovyBlock(""));
        } else return find.map(Edge::to);
    }

    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort) {
        return findTarget(from, fromPort, fromPort == Ports.FLOW_OUT); // FLOW_OUT can be empty by default
    }
}
