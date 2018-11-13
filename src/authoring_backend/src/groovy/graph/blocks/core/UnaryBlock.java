package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import groovy.api.Try;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

import static groovy.api.Ports.A;

public class UnaryBlock extends SimpleNode implements GroovyBlock<UnaryBlock> {
    private SimpleStringProperty op;
    public UnaryBlock(String op) {
        super();
        this.op = new SimpleStringProperty(op);
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        return tryA.map(a -> String.format("%s(%s)", op.get(), a));
    }

    @Override
    public UnaryBlock replicate() { return new UnaryBlock(op.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(A); }

    @Override
    public SimpleStringProperty name() { return op; }
}
