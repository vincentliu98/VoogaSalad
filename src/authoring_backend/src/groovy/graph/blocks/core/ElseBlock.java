package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Try;
import groovy.api.Ports;

import java.util.Set;

import static groovy.api.Ports.*;

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
    public Set<Ports> ports() { return Set.of(IF_BODY, FLOW_OUT); }

    @Override
    public String name() { return "Else"; }
}
