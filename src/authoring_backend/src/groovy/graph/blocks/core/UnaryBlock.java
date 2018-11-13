package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import groovy.api.Try;

import java.util.Set;

import static groovy.api.Ports.A;

public class UnaryBlock extends SimpleNode implements GroovyBlock<UnaryBlock> {
    private String op;
    public UnaryBlock(String op) {
        super();
        this.op = op;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        return tryA.map(a -> String.format("%s(%s)", op, a));
    }

    @Override
    public UnaryBlock replicate() { return new UnaryBlock(op); }

    @Override
    public Set<Ports> ports() { return Set.of(A); }

    @Override
    public String name() { return op; }
}
