package groovy.graph.blocks.impl.control;

import groovy.graph.BlockGraph;
import groovy.graph.blocks.GroovyBlock;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;

import java.util.Set;

import static groovy.graph.Ports.*;

public class IfBlock extends SimpleNode implements GroovyBlock<IfBlock> {
    public IfBlock() { super(); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT, IF_PREDICATE, IF_BODY); }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryPredicate = graph.findTarget(this, IF_PREDICATE).flatMap(b -> b.toGroovy(graph));
        var tryBody = graph.findTarget(this, IF_BODY, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryPredicate.flatMap( predicate ->
                tryBody.flatMap( body ->
                    tryOut.map( out ->
                        "if("+predicate+") {\n" + body + "\n}\n" + out
                    )
                )
        );
    }

    @Override
    public IfBlock replicate() { return new IfBlock(); }
}
