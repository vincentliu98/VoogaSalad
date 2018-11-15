package phase.api;

import graph.Graph;

/**
 *   Turn-based games have discrete "phases" that the user can decide on their moves.
 *   The PhaseGraph represents this idea by having nodes and edges represent the following.
 *
 *   Nodes (Phase) -> The phase that the user is on, at a specific point of the gameplay.
 *   Edges (Transition) -> GameEvents that can happen at a certain phase, that moves the game to the next phase.
 *                         Each Transition is associated with a specific event (e.g. MouseClick, MouseDrag, KeyPress)
 *
 */
public interface PhaseGraph extends Graph<Phase, Transition> {
    /**
     *  The initial source Node
     */
    Phase source();
}
