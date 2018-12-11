package phase.api;

import graph.Graph;

/**
 * Turn-based games have discrete "phaseGraphs" that the user can decide on their moves.
 * The PhaseGraph represents this idea by having nodes and edges represent the following.
 * <p>
 * Nodes (Phase) -> The createPhase that the user can be on, at a specific point of the gameplay.
 * Edges (Transition) -> GameEvents that can happen at a certain createPhase, that moves the game to the next createPhase.
 * Each Transition is associated with a specific event (e.g. MouseClick, MouseDrag, KeyPress)
 */
public interface PhaseGraph extends Graph<Phase, Transition> {
    /**
     * Returns the name of the PhaseGraph; it is unique across all PhaseGraph instances.
     */
    String name();

    /**
     * Sets the name of this PhaseGraph; it returns false if the name is already taken.
     */
    boolean setName(String another);

    /**
     * The initial source Node
     */
    Phase source();
}
