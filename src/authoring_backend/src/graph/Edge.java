package authoring_backend.src.graph;

/**
 *  Represents an Edge of within the graph.
 */
public interface Edge<N extends Node> {
    N from();
    N to();
}
