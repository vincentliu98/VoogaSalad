package authoring_backend.src.groovy.graph.blocks.binary_ops;

import authoring_backend.src.groovy.graph.BlockGraph;
import authoring_backend.src.groovy.graph.blocks.GroovyBlock;
import authoring_backend.src.graph.SimpleNode;
import authoring_backend.src.groovy.Try;
import authoring_backend.src.groovy.graph.Ports;

import java.util.Set;

import static authoring_backend.src.groovy.graph.Ports.*;

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
