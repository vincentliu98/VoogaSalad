package graph;

import java.util.List;
import java.util.Map;

/**
 * Generic representation of a graph.
 * https://docs.oracle.com/javase/tutorial/java/generics/index.html
 */
public interface Graph<N extends Node, E extends Edge<N>> extends Map<N, List<E>> {
    /**
     * @param n an node to add
     */
    void addNode(N n) throws Throwable;

    /**
     * @param n an node to remove
     */
    void removeNode(N n);

    /**
     * @param e an edge to add
     */
    void addEdge(E e) throws Throwable;

    /**
     * @param e an edge to remove
     */
    void removeEdge(E e);
}
