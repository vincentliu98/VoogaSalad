package graph;

/**
 * Minimal implementation of an Edge, just to allow
 * potential subclasses implementing Edge interface to have basic operations set.
 *
 * @param <N> Some subclass of Node
 */
public class SimpleEdge<N extends Node> implements Edge<N> {
    private N from, to;

    public SimpleEdge(N from, N to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public N from() {
        return from;
    }

    @Override
    public N to() {
        return to;
    }
}
