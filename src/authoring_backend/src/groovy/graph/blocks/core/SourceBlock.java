package groovy.graph.blocks.core;


import groovy.graph.Ports;
import groovy.graph.BlockGraph;
import graph.SimpleNode;
import groovy.Try;
<<<<<<< HEAD:src/authoring_backend/src/groovy/graph/blocks/core/SourceBlock.java
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
=======
>>>>>>> eee9985cb2cb55cba247b1bd2c96ad7b448fdaf0:src/authoring_backend/src/groovy/graph/blocks/SourceBlock.java

import java.util.Set;

import static groovy.graph.Ports.FLOW_OUT;

public class SourceBlock extends SimpleNode implements GroovyBlock<SourceBlock> {
    public SourceBlock() { super(); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT); }

    @Override
    public Try<String> toGroovy(BlockGraph graph) {
        return graph.findTarget(this, FLOW_OUT).flatMap(b -> b.toGroovy(graph));
    }

    @Override
    public SourceBlock replicate() { return new SourceBlock(); }
}
