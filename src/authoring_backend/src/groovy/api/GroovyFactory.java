package groovy.api;

import groovy.graph.BlockGraphImpl;
import groovy.graph.blocks.core.*;

import java.util.Set;

/**
 *  The GroovyFactory can generate Graphs and GroovyBlocks
 */
public class GroovyFactory {
    /**
     *  Initializes an empty BlockGraph
     */
    public BlockGraph emptyGraph() {
        return new BlockGraphImpl();
    }

    /**
     *  Initializes the graph with the given vertices and edges (their deep copy);
     */
    public BlockGraph fromSubset(Set<GroovyBlock> vertices, Set<BlockEdge> edges) {
        return BlockGraphImpl.fromSubset(vertices, edges);
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
     */
    public static AssignBlock assignBlock() { return new AssignBlock(); }

    /**
     * Literal Blocks
     */
    public static LiteralBlock literalBlock() { return new LiteralBlock(""); }

    /**
     * Unary Blocks
     */
    public static UnaryBlock unaryBlock() { return new UnaryBlock(""); }

    /**
     * Binary Infix Blocks
     */
    public static InfixBinaryBlock binaryBlock() { return new InfixBinaryBlock(""); }

    /**
     * Binary Infix Blocks
     */
    public static RawGroovyBlock rawBlock() { return new RawGroovyBlock(""); }
}
