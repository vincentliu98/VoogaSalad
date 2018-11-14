package phase.api;

import graph.Edge;
import groovy.api.BlockGraph;

/**
      Each Transition consists of two Groovy scripts, which are represented by BlockGraphs.
      1. Guard -> The script that decides whether the edge can be taken or not depending on dynamic status
                  (e.g. the swordsman has too low hp to attack, can't select other player's pieces)
      2. Execution ->  The script that gets executed once Guard approves of the transition.
 */

public interface Transition extends Edge<Phase> {
    GameEvent trigger();
    BlockGraph guard();
    BlockGraph execution();
}
