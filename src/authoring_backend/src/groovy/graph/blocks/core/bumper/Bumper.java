package groovy.graph.blocks.core.bumper;

import groovy.api.BlockEdge;
import groovy.api.BlockGraph;
import groovy.graph.blocks.core.GroovyBlock;

/**
 *  Bumper "bumps" edges that are obviously not valid;
 *
 */
public class Bumper {
    public static BlockEdge typeCheck(BlockEdge e, BlockGraph graph) {
        return e; // TODO but what are obviously not valid?
    }

    private static boolean isAssign(GroovyBlock block) { return block.name().equals("="); }
    private static boolean isRef(GroovyBlock block) { return block.name().equals("ref"); }
}
