package phase.api;

import graph.Node;
import groovy.api.BlockGraph;

/**
 * Each Phase contains a Groovy script that gets executed whenever the user enters it;
 */
public interface Phase extends Node {
    String name();

    BlockGraph exec();

    boolean isSource();
}
