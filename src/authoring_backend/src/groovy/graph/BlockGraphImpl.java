package groovy.graph;

import graph.Edge;
import graph.SimpleGraph;
import groovy.api.BlockEdge;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import frontendUtils.Try;
import groovy.graph.blocks.core.GroovyBlock;
import groovy.graph.blocks.core.RawGroovyBlock;
import groovy.graph.blocks.core.SourceBlock;
import groovy.graph.blocks.core.bumper.Bumper;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;

public class BlockGraphImpl extends SimpleGraph<GroovyBlock, BlockEdge> implements BlockGraph {
    private SourceBlock source;
    public BlockGraphImpl() {
        super();
        source = new SourceBlock();
        try { addNode(source); } catch (Throwable ignored) {} // not. going. to. happen.
    }

    @Override
    public SourceBlock source() { return source; }

    /**
     *  When adding an edge, the graph will check
     *  1. If the edge is syntactically correct
     *  2. Whether there's already an edge from the port
     *  It will throw an exception if it fails these checks
     */
    @Override
    public void addEdge(BlockEdge edge) throws Throwable {
        var typeCheck = Try.apply(() -> Bumper.typeCheck(edge, this));
        if(typeCheck.isSuccess()) {
            if(get(edge.from()).stream().noneMatch(p -> p.fromPort() == edge.fromPort())) super.addEdge(edge);
            else throw new PortAlreadyFilledException(edge.from(), edge.fromPort());
        } else typeCheck.get();
    }

    /**
     *  Testing the "fromPort" is enough.
     */
    @Override
    public void removeEdge(BlockEdge edge) {
        get(edge.from()).removeIf(t -> t.fromPort() == edge.fromPort());
    }

    @Override
    public Try<String> transformToGroovy() { return source.toGroovy(this); }

    /**
     *  Given a subset of vertices and edges, addSubset() creates a deep copy of them and
     *  adds them to the graph / returns those copies.
     */
    public Pair<Set<GroovyBlock>, Set<BlockEdge>> addSubset(Set<GroovyBlock> vertices, Set<BlockEdge> edges) {
        var addedVertices = new HashSet<GroovyBlock>();
        var freshVertices = new HashSet<GroovyBlock>();
        var freshEdges = new HashSet<BlockEdge>();

        for (var edge : edges) {
            var replica = edge.replicate();
            freshEdges.add(replica);
            freshVertices.add(replica.from());
            freshVertices.add(replica.to());
            addedVertices.add(edge.from());
            addedVertices.add(edge.to());
        }

        for (var vertex : vertices)
            if(!addedVertices.contains(vertex)) freshVertices.add(vertex.replicate());

        try {
            for(var edge: freshEdges) addEdge(edge);
            for(var vert: freshVertices) addNode(vert);
        } catch (Throwable ignored) {}

        return new Pair<>(freshVertices, freshEdges);
    }


    @Override
    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort, boolean canBeEmpty) {
        var find = Try.apply(() -> get(from)
                .stream()
                .filter(e -> e.fromPort() == fromPort)
                .findFirst()
                .orElseThrow(Try.supplyThrow(new PortNotConnectedException(from, fromPort))));

        if(find.isFailure() && canBeEmpty) { // handle can-be-createGraph s
            return Try.success(new RawGroovyBlock(""));
        } else return find.map(Edge::to);
    }

    @Override
    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort) {
        return findTarget(from, fromPort, fromPort == Ports.FLOW_OUT); // FLOW_OUT can be createGraph by default
    }
}
