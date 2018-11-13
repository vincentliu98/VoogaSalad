package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Minimal implementation of a Graph, just to allow
 * potential subclasses implementing Graph interface to have a basic operations set.
 */
public class SimpleGraph<N extends Node, E extends Edge<N>>
        extends HashMap<N, List<E>> implements Graph<N, E> {
    @Override
    public void addNode(N n) throws Throwable {
        if(!containsKey(n)) put(n, new ArrayList<>());
    }

    @Override
    public void addEdge(E e) throws Throwable {
        addNode(e.from());
        addNode(e.to());
        var tmp = get(e.from());
        tmp.add(e);
        put(e.from(), tmp);
    }
}

