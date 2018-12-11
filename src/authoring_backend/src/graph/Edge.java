package graph;

/**
 * Represents an Edge of within the graph.
 */
public interface Edge<N extends Node> {
    N from();

    N to();
}
