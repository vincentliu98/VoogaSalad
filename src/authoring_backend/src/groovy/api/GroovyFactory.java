package groovy.api;

import groovy.graph.BlockEdgeImpl;
import groovy.graph.BlockGraphImpl;
import groovy.graph.blocks.core.*;
import groovy.graph.blocks.small_factory.LiteralFactory;
import frontendUtils.Try;

/**
 *  A Factory that can generate Graph/Node/Edge that represents Groovy code.
 */
public class GroovyFactory {
    public GroovyFactory() { }

    /**
     *  Makes an createGraph BlockGraph with one source node
     */
    public BlockGraph createGraph() { return new BlockGraphImpl(); }

    /**
     *  Makes a default BlockGraph for guards, passing everything in.
     */
    public BlockGraph createGuard() {
        try {
            var graph = new BlockGraphImpl();
            var pass = rawBlock("$guardRet = true");
            graph.addEdge(createEdge(graph.source(), Ports.FLOW_OUT, pass));
            return graph;
        } catch(Throwable ignored) { return createGraph(); } // but it's not gonna fail
    }

    /**
     *  Makes an edge
     */
    public BlockEdge createEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        return new BlockEdgeImpl(from, fromPort, to);
    }

    /**
     *  Core Blocks
     */
    public IfBlock ifBlock() { return new IfBlock(false); }
    public IfBlock ifElseBlock() { return new IfBlock(true); }
    public ElseBlock elseBlock() { return new ElseBlock(); }
    public ForEachBlock forEachBlock(String loopvar) { return new ForEachBlock(loopvar); }

    public AssignBlock assignBlock() { return new AssignBlock(); }

    public Try<LiteralBlock> booleanBlock(String value) { return LiteralFactory.booleanBlock(value); }
    public Try<LiteralBlock> integerBlock(String value) { return LiteralFactory.integerBlock(value); }
    public Try<LiteralBlock> keyBlock(String value) { return LiteralFactory.keyBlock(value); }
    public Try<LiteralBlock> doubleBlock(String value) { return LiteralFactory.doubleBlock(value); }
    public Try<LiteralBlock> listBlock(String value) { return LiteralFactory.listBlock(value); }
    public Try<LiteralBlock> mapBlock(String value) { return LiteralFactory.mapBlock(value); }
    public LiteralBlock stringBlock(String value) { return LiteralFactory.stringBlock(value); }
    public Try<LiteralBlock> refBlock(String value) {
        return LiteralFactory.refBlock(value);
    }

    public FunctionBlock functionBlock(String op) { return new FunctionBlock(op); }
    public InfixBinaryBlock binaryBlock(String op) { return new InfixBinaryBlock(op); }
    // We'll eventually remove this
    public RawGroovyBlock rawBlock(String code) { return new RawGroovyBlock(code); }
}

