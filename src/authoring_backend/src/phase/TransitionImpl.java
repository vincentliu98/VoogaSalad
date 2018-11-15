package phase;

import graph.SimpleEdge;
import groovy.api.BlockGraph;
import groovy.api.GroovyFactory;
import phase.api.GameEvent;
import phase.api.Phase;
import phase.api.Transition;

public class TransitionImpl extends SimpleEdge<Phase> implements Transition {
    private GameEvent trigger;
    private BlockGraph guard;
    private BlockGraph execution;

    public TransitionImpl(Phase from, GameEvent trigger, Phase to) {
        super(from, to);
        this.trigger = trigger;
        this.guard = GroovyFactory.emptyGraph();
        this.execution = GroovyFactory.emptyGraph();
    }

    @Override
    public GameEvent trigger() { return trigger; }
    @Override
    public BlockGraph guard() { return guard; }
    @Override
    public BlockGraph execution() { return execution; }
}
