package phase.api;

import graph.Node;
import groovy.api.BlockGraph;

import java.util.UUID;

/**
 *  Each Phase contains a Groovy script that gets executed whenever the user enters it;
 */
public interface Phase extends Node {
    UUID id();
    BlockGraph exec();
}
