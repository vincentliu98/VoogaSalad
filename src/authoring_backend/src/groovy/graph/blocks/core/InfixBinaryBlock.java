package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Try;
import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.Map;
import java.util.Set;

import static groovy.api.Ports.A;
import static groovy.api.Ports.B;

/**
 * InfixBinaryBlocks represent binary operators that are placed in between the operands
 */
public class InfixBinaryBlock extends SimpleNode implements GroovyBlock<InfixBinaryBlock> {
    private String op;

    public InfixBinaryBlock(double x, double y, String op) {
        super(x, y);
        this.op = op;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        var tryB = graph.findTarget(this, B).flatMap(b -> b.toGroovy(graph));
        return tryA.flatMap(a ->
                tryB.map(b ->
                        String.format("((%s) %s (%s))", a, op, b)
                )
        );
    }

    @Override
    public InfixBinaryBlock replicate() {
        return new InfixBinaryBlock(x(), y(), op);
    }

    @Override
    public Set<Ports> ports() {
        return Set.of(A, B);
    }

    @Override
    public String name() {
        return op;
    }

    @Override
    public Map<String, Object> params() {
        return Map.of("op", op);
    }
}
