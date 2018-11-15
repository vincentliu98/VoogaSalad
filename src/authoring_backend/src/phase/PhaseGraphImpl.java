package phase;

import graph.SimpleGraph;
import phase.api.Phase;
import phase.api.PhaseGraph;
import phase.api.Transition;

public class PhaseGraphImpl extends SimpleGraph<Phase, Transition> implements PhaseGraph {
    private Phase source;

    public PhaseGraphImpl() {
        super();
        this.source = new Phase();
        this.source.description().setValue("source");
    }

    @Override
    public Phase source() { return source; }
}
