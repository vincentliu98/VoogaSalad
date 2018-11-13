package groovy.graph;

import essentials.Replicable;
import graph.SimpleEdge;
import groovy.Try;
import groovy.graph.blocks.GroovyBlock;

/**
 *  BlockEdge class represents an edge that connects two GroovyBlocks;
 *  It has two additional parameters fromPort and toPort, which represents
 *  the port of each block that the edges are connected to.
 */
public class BlockEdge extends SimpleEdge<GroovyBlock> implements Replicable<BlockEdge> {
    private Ports fromPort;

    private BlockEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        super(from, to);
        this.fromPort = fromPort;
    }

    /**
     * This is the only way for the outside world to create BlockEdge
     * @return A BlockEdge, that may have failed the Syntax Checking or not.
     */
    public static Try<BlockEdge> gen(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        return Try.apply(() -> typeCheck(new BlockEdge(from, fromPort, to)));
    }

    /**
     * Does a basic type checking for the new edge
     * TODO
     */
    private static BlockEdge typeCheck(BlockEdge e) throws Exception {
        if("Something's wrong".length() == 1) throw new Exception("Type check isFailure for edge: " + e);
        return e;
    }


    @Override
    public BlockEdge replicate() { return new BlockEdge(from().replicate(), fromPort, to().replicate()); }

    public Ports fromPort() { return fromPort; }
}
