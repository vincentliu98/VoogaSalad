package authoring_backend.src.groovy.graph.blocks.control;

import authoring_backend.src.groovy.graph.BlockGraph;
import authoring_backend.src.groovy.graph.blocks.GroovyBlock;
import authoring_backend.src.graph.SimpleNode;
import authoring_backend.src.groovy.Try;
import authoring_backend.src.groovy.graph.Ports;

import java.util.Set;

import static authoring_backend.src.groovy.graph.Ports.*;

public class IfBlock extends SimpleNode implements GroovyBlock<IfBlock> {
    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT, IF_PREDICATE, IF_BODY); }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryPredicate = graph.findTarget(this, IF_PREDICATE).flatMap(b -> b.toGroovy(graph));
        var tryBody = graph.findTarget(this, IF_BODY, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryPredicate.flatMap(predicate ->
                tryBody.flatMap(body ->
                    tryOut.map(out ->
                        "if("+predicate+") {\n" + body + "\n}\n" + out
                    )
                )
        );
    }

    @Override
    public IfBlock replicate() { return new IfBlock(); }
}
