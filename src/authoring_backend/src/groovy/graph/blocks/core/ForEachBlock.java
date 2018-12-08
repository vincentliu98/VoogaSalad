package groovy.graph.blocks.core;

import authoringUtils.frontendUtils.Try;
import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;

import java.util.Set;

import static groovy.api.Ports.*;

public class ForEachBlock extends SimpleNode implements GroovyBlock<ForEachBlock> {
    private String loopvar;
    public ForEachBlock(String loopvar) {
        this.loopvar = loopvar;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryList = graph.findTarget(this, FOREACH_LIST).flatMap(b -> b.toGroovy(graph));
        var tryBody = graph.findTarget(this, FOREACH_BODY, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));

        return tryList.flatMap(list ->
                tryBody.flatMap(body ->
                    tryOut.map(out ->
                        String.format("(%s).each({%s ->\n%s\n})\n%s", list, loopvar, body, out)
                    )
                )
        );
    }

    @Override
    public ForEachBlock replicate() { return new ForEachBlock(loopvar); }

    @Override
    public Set<Ports> ports() { return Set.of(FOREACH_LIST, FOREACH_BODY, FLOW_OUT); }

    @Override
    public String name() { return "each: "+loopvar; }
}
