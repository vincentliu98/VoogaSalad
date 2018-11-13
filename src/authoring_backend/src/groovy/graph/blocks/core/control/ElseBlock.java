package groovy.graph.blocks.core.control;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;

import java.util.Set;

import static groovy.graph.Ports.*;

public class ElseBlock extends SimpleNode implements GroovyBlock<ElseBlock> {
    public ElseBlock() { super(); }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryBody = graph.findTarget(this, IF_BODY, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
        return tryBody.flatMap( body ->
                tryOut.map( out ->
                    "else {\n" + body + "\n}\n" + out
                )
        );
    }

    @Override
    public ElseBlock replicate() { return new ElseBlock(); }

    @Override
    public Set<Ports> ports() {
        return Set.of(IF_BODY, FLOW_OUT);
    }
}