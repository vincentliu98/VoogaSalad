package authoring_backend.src.graph;

import java.util.List;
import java.util.Map;

/**
 *  Generic representation of a graph.
 *  https://docs.oracle.com/javase/tutorial/java/generics/index.html
 */
public interface Graph<N extends Node, E extends Edge<N>> extends Map<N, List<E>> {
    /**
     * @param n an node to add
     * @return true if this operation was successful; the meaning of "successful" are defined
     * differently for each Graphs
     */
    boolean addNode(N n);

    /**
     * @param e an edge to add
     * @return true if this operation was successful; the meaning of "successful" are defined
     * differently for each Graphs
     */
    boolean addEdge(E e);
}
