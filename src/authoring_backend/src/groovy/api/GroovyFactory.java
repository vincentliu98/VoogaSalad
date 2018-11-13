package groovy.api;

import groovy.graph.BlockGraphImpl;
import groovy.graph.blocks.core.GroovyBlock;
import groovy.graph.blocks.core.LiteralBlock;
import groovy.graph.blocks.factory.LiteralFactory;

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
     *  Literal Blocks
     */
    public static Try<LiteralBlock> integerBlock(String value) { return LiteralFactory.integerBlock(value); }
    public static Try<LiteralBlock> doubleBlock(String value) { return LiteralFactory.doubleBlock(value); }
    public static Try<LiteralBlock> stringBlock(String value) { return LiteralFactory.stringBlock(value); }
    public static Try<LiteralBlock> listBlock(String value) { return LiteralFactory.listBlock(value); }
    public static Try<LiteralBlock> mapBlock(String value) { return LiteralFactory.mapBlock(value); }
}
