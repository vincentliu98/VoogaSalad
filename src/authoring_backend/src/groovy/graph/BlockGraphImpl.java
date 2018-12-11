package groovy.graph;

import authoringUtils.frontendUtils.Try;
import graph.Edge;
import graph.SimpleGraph;
import groovy.api.BlockEdge;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;
import groovy.graph.blocks.core.RawGroovyBlock;
import groovy.graph.blocks.core.SourceBlock;

public class BlockGraphImpl extends SimpleGraph<GroovyBlock, BlockEdge> implements BlockGraph {
    private SourceBlock source;

    public BlockGraphImpl() {
        super();
        source = new SourceBlock(1000, 1000); // this dirty little secret should be fixed
        try {
            addNode(source);
        } catch (Throwable ignored) {
        }
    }

    @Override
    public SourceBlock source() {
        return source;
    }

    /**
     * When adding an edge, the graph will check
     * 1. If the edge is syntactically correct
     * 2. Whether there's already an edge from the port
     * It will throw an exception if it fails these checks
     */
    @Override
    public void addEdge(BlockEdge edge) throws Throwable {
        if (get(edge.from()).stream().noneMatch(p -> p.fromPort() == edge.fromPort())) super.addEdge(edge);
        else throw new PortAlreadyFilledException(edge.from(), edge.fromPort());
    }

    /**
     * Testing the "fromPort" is enough.
     */
    @Override
    public void removeEdge(BlockEdge edge) {
        get(edge.from()).removeIf(t -> t.fromPort() == edge.fromPort());
    }

    @Override
    public Try<String> transformToGroovy() {
        return source.toGroovy(this);
    }


    @Override
    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort, boolean canBeEmpty) {
        var find = Try.apply(() -> get(from)
                .stream()
                .filter(e -> e.fromPort() == fromPort)
                .findFirst()
                .orElseThrow(Try.supplyThrow(new PortNotConnectedException(from, fromPort))));

        if (find.isFailure() && canBeEmpty) { // handle can-be-empties
            return Try.success(new RawGroovyBlock(0, 0, ""));
        } else return find.map(Edge::to);
    }

    @Override
    public Try<GroovyBlock> findTarget(GroovyBlock from, Ports fromPort) {
        return findTarget(from, fromPort, fromPort == Ports.FLOW_OUT); // FLOW_OUT can be createPhaseGraph by default
    }
}
