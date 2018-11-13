package groovy.graph.blocks.core.operators;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

import static groovy.graph.Ports.*;

/**
 *  InfixBinaryBlocks represent binary operators that are placed in between the operands
 */
public class InfixBinaryBlock extends SimpleNode implements GroovyBlock<InfixBinaryBlock> {
    private SimpleStringProperty op;
    public InfixBinaryBlock(String op) {
        super();
        this.op = new SimpleStringProperty(op);
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        var tryB = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        return tryA.flatMap ( a ->
            tryB.map( b ->
                String.format("(%s %s %s)", a, op.get(), b)
            )
        );
    }

    @Override
    public InfixBinaryBlock replicate() {
        return new InfixBinaryBlock(op.get());
    }

    @Override
    public Set<Ports> ports() { return Set.of(A, B); }
}
