package groovy.graph.blocks.control;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;

import java.util.Set;

import static groovy.graph.Ports.*;

public class ForBlock extends SimpleNode implements GroovyBlock<ForBlock> {
    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        var tryInit = graph.findTarget(this, FOR_INIT, true).flatMap(b -> b.toGroovy(graph));
        var tryCheck = graph.findTarget(this, FOR_CHECK, true).flatMap(b -> b.toGroovy(graph));
        var tryMutate = graph.findTarget(this, FOR_MUTATE, true).flatMap(b -> b.toGroovy(graph));
        var tryBody = graph.findTarget(this, FOR_BODY, true).flatMap(b -> b.toGroovy(graph));
        var tryOut = graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));

        return tryInit.flatMap(init ->
                tryCheck.flatMap(check ->
                    tryMutate.flatMap(mutate ->
                        tryBody.flatMap(body ->
                            tryOut.map(out ->
                                String.format("for( %s ; %s ; %s ) {\n%s\n}\n%s",
                                    init.toString().replaceAll(";", ""),
                                    check, mutate, body, out)
                            )
                        )
                    )
                )
        );
    }

    @Override
    public ForBlock replicate() { return new ForBlock(); }

    @Override
    public Set<Ports> ports() {
        return Set.of(FLOW_OUT, FOR_INIT, FOR_CHECK, FOR_MUTATE, FOR_BODY);
    }
}
