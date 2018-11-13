package groovy.api;

import groovy.graph.BlockEdgeImpl;
import groovy.graph.BlockGraphImpl;
import groovy.graph.blocks.core.*;
import groovy.graph.blocks.small_factory.LiteralFactory;

/**
 *  A Factory that can generate everything that one needs from a
 */
public class GroovyFactory {
    /**
     *  Makes an empty BlockGraph with one source node
     */
    public BlockGraph emptyGraph() {
        return new BlockGraphImpl();
    }

    /**
     *  Makes an edge
     */
    public BlockEdge makeEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        return new BlockEdgeImpl(from, fromPort, to);
    }

    /**
     *  Control Blocks
     */
    public static IfBlock ifBlock() { return new IfBlock(true); }
    public static IfBlock ifElseBlock() { return new IfBlock(false); }
    public static ElseBlock elseBlock() { return new ElseBlock(); }
    public static ForEachBlock forEachBlock() { return new ForEachBlock(); }

    /**
     *  Assignment Blocks
     *  TODO: Make utility function that helps when writing the lhs
     */
    public static AssignBlock assignBlock(String lhs) { return new AssignBlock(lhs); }

    /**
     * Literal Blocks
     */
    public static Try<LiteralBlock> integerBlock(String value) { return LiteralFactory.integerBlock(value); }
    public static Try<LiteralBlock> doubleBlock(String value) { return LiteralFactory.doubleBlock(value); }
    public static Try<LiteralBlock> listBlock(String value) { return LiteralFactory.listBlock(value); }
    public static Try<LiteralBlock> mapBlock(String value) { return LiteralFactory.mapBlock(value); }
    public static Try<LiteralBlock> stringBlock(String value) { return LiteralFactory.stringBlock(value); }

    /**
     * Unary Blocks
     */
    public static UnaryBlock unaryBlock(String op) { return new UnaryBlock(op); }

    /**
     * Binary Infix Blocks
     */
    public static InfixBinaryBlock binaryBlock(String op) { return new InfixBinaryBlock(op); }

    /**
     * Raw Groovy Blocks
     */
    public static RawGroovyBlock rawBlock(String code) { return new RawGroovyBlock(code); }
}
