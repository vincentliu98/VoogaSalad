package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Try;
import groovy.api.Ports;

import java.util.Set;

import static groovy.api.Ports.*;

public class ForEachBlock extends SimpleNode implements GroovyBlock<ForEachBlock> {
    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryList = graph.findTarget(this, FOREACH_LIST).flatMap(b -> b.toGroovy(graph));
        var tryVar = graph.findTarget(this, FOREACH_LOOPVAR).flatMap(b -> b.toGroovy(graph));
        var tryBody = graph.findTarget(this, FOREACH_BODY, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));

        return tryList.flatMap(list ->
            tryVar.flatMap(var ->
                tryBody.flatMap(body ->
                    tryOut.map(out ->
                        String.format("(%s).each({%s ->\n%s\n})\n%s", list, var, body, out)
                    )
                )
            )
        );
    }

    @Override
    public ForEachBlock replicate() { return new ForEachBlock(); }

    @Override
    public Set<Ports> ports() { return Set.of(FOREACH_LIST, FOREACH_LOOPVAR, FOREACH_BODY, FLOW_OUT); }

    @Override
    public String name() { return "ForEach"; }
}
