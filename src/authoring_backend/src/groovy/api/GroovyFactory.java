package groovy.api;

import essentials.GameData;
import groovy.graph.BlockEdgeImpl;
import groovy.graph.BlockGraphImpl;
import groovy.graph.blocks.core.*;
import groovy.graph.blocks.small_factory.LiteralFactory;

/**
 *  A Factory that can generate some pieces of groovy code
 *  TODO: Make a utility function that helps the author when writing things out, using the gameData
 */
public class GroovyFactory {
    /**
     *  Makes an empty BlockGraph with one source node
     */
    public static BlockGraph emptyGraph() { return new BlockGraphImpl(); }

    /**
     *  Makes an edge
     */
    public static BlockEdge makeEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        return new BlockEdgeImpl(from, fromPort, to);
    }

    /**
     *  Control Blocks
     */
    public static IfBlock ifBlock() { return new IfBlock(false); }
    public static IfBlock ifElseBlock() { return new IfBlock(true); }
    public static ElseBlock elseBlock() { return new ElseBlock(); }
    public static ForEachBlock forEachBlock(String loopvar) { return new ForEachBlock(loopvar); }

    /**
     *  Assignment Blocks
     */
    public static AssignBlock assignBlock() { return new AssignBlock(); }

    /**
     * Literal Blocks
     */
    public static Try<LiteralBlock> integerBlock(String value) { return LiteralFactory.integerBlock(value); }
    public static Try<LiteralBlock> doubleBlock(String value) { return LiteralFactory.doubleBlock(value); }
    public static Try<LiteralBlock> listBlock(String value) { return LiteralFactory.listBlock(value); }
    public static Try<LiteralBlock> mapBlock(String value) { return LiteralFactory.mapBlock(value); }
    public static LiteralBlock stringBlock(String value) { return LiteralFactory.stringBlock(value); }
    public static Try<LiteralBlock> refBlock(String value, GameData data) {
        return LiteralFactory.refBlock(value, data);
    }

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
