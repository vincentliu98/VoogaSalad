package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import utils.Try;

import java.util.Set;

import static groovy.api.Ports.A;
import static groovy.api.Ports.FLOW_OUT;

public class UnaryBlock extends SimpleNode implements GroovyBlock<UnaryBlock> {
    private String op;
    public UnaryBlock(String op) {
        super();
        this.op = op;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryA.flatMap(a ->
            tryOut.map(out ->
                String.format("%s(%s)%s", op, a, out.toString().length() > 0 ? ";\n"+out : "")
            )
        );
    }

    @Override
    public UnaryBlock replicate() { return new UnaryBlock(op); }

    @Override
    public Set<Ports> ports() { return Set.of(A, FLOW_OUT); }

    @Override
    public String name() { return op; }
}
