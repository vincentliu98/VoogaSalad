package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import authoringUtils.frontendUtils.Try;
import groovy.api.Ports;

import java.util.Set;

import static groovy.api.Ports.*;

/**
 *  InfixBinaryBlocks represent binary operators that are placed in between the operands
 */
public class InfixBinaryBlock extends SimpleNode implements GroovyBlock<InfixBinaryBlock> {
    private String op;
    public InfixBinaryBlock(String op) {
        super();
        this.op = op;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        var tryB = graph.findTarget(this, B).flatMap(b -> b.toGroovy(graph));
        return tryA.flatMap ( a ->
            tryB.map( b ->
                String.format("((%s) %s (%s))", a, op, b)
            )
        );
    }

    @Override
    public InfixBinaryBlock replicate() { return new InfixBinaryBlock(op); }

    @Override
    public Set<Ports> ports() { return Set.of(A, B); }

    @Override
    public String name() { return op; }
}
