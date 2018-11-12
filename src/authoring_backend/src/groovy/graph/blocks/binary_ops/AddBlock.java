package groovy.graph.blocks.binary_ops;

import groovy.graph.BlockGraph;
import groovy.graph.blocks.GroovyBlock;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;

import java.util.Set;

import static groovy.graph.Ports.*;

public class AddBlock extends SimpleNode implements GroovyBlock<AddBlock> {
    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryA = graph.findTarget(this, A).flatMap(b -> b.toGroovy(graph));
        var tryB = graph.findTarget(this, B).flatMap(b -> b.toGroovy(graph));
        return tryA.flatMap(a ->
            tryB.map(b ->
                String.format("(%s + %s)", a, b)
            )
        );
    }

    @Override
    public AddBlock replicate() {
        return new AddBlock();
    }

    @Override
    public Set<Ports> ports() {
        return Set.of(A, B); // not including flow_out here since control flow should... end here
    }
}
