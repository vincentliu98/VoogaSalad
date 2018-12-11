package phase;

import graph.SimpleEdge;
import groovy.api.BlockGraph;
import phase.api.GameEvent;
import phase.api.Phase;
import phase.api.Transition;

/**
 * The implementation of Transition initializes
 * guard -> createPhaseGraph guard that passes everything in
 */
public class TransitionImpl extends SimpleEdge<Phase> implements Transition {
    private GameEvent trigger;
    private BlockGraph guard;

    public TransitionImpl(Phase from, GameEvent trigger, Phase to, BlockGraph guard) {
        super(from, to);
        this.trigger = trigger;
        this.guard = guard;
    }

    @Override
    public GameEvent trigger() {
        return trigger;
    }

    @Override
    public BlockGraph guard() {
        return guard;
    }
}
