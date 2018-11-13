package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Try;
import groovy.api.Ports;

import java.util.Set;

import static groovy.api.Ports.*;

public class AssignBlock extends SimpleNode implements GroovyBlock<AssignBlock> {
    private AssignBlock() { super(); }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryLHS = graph.findTarget(this, ASSIGN_LHS).flatMap(b -> b.toGroovy(graph));
        var tryRHS = graph.findTarget(this, ASSIGN_RHS).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryLHS.flatMap( lhs ->
            tryRHS.flatMap( rhs ->
                tryOut.map( out ->
                    String.format("%s = %s\n%s", tryLHS, tryRHS, out)
                )
            )
        );
    }
    @Override
    public AssignBlock replicate() { return new AssignBlock(); }
    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT, ASSIGN_LHS, ASSIGN_RHS); }
}
