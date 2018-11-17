package phase;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.graph.blocks.core.GroovyBlock;
import phase.api.Phase;

import java.util.UUID;

public class PhaseImpl extends SimpleNode implements Phase {
    private UUID uuid;
    private BlockGraph exec;

    public PhaseImpl(BlockGraph exec) {
        super();
        uuid = UUID.randomUUID();
        this.exec = exec;
    }

    public UUID id() { return uuid; }
    public BlockGraph exec() { return exec; }
}
