package phase;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import phase.api.Phase;

public class PhaseImpl extends SimpleNode implements Phase {
    private String name;
    private BlockGraph exec;

    public PhaseImpl(BlockGraph exec, String name) {
        super();
        this.name = name;
        this.exec = exec;
    }

    @Override
    public String name() { return name; }

    @Override
    public BlockGraph exec() { return exec; }
}
