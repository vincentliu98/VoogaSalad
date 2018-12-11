package phase.api;

import graph.Edge;
import groovy.api.BlockGraph;

/**
 * Each Transition has a Guard, which is the script that decides whether the edge can
 * be taken or not depending on dynamic status, by registering the boolean value to $guardRet
 * <p>
 * (e.g. the swordsman has too low hp to attack, can't select other player's pieces)
 */

public interface Transition extends Edge<Phase> {
    GameEvent trigger();

    BlockGraph guard();
}
