package phase.api;

import phase.NamespaceException;
import phase.PhaseGraphImpl;
import phase.TransitionImpl;
import utils.Try;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  PhaseManager manages all the list that's "out" there, and works as a factory for graph/phase/transition.
 */
public class PhaseManager {
    private Set<String> namespace;
    private List<PhaseGraph> phases;

    public PhaseManager() {
        this.namespace = new HashSet<>();
        phases = new ArrayList<>();
    }

    public Try<PhaseGraph> empty(String name) {
        if(namespace.add(name)) {
            var graph = new PhaseGraphImpl(name, namespace::add);
            phases.add(graph);
            return Try.success(graph);
        } else return Try.failure(new NamespaceException(name));
    }

    public void removeGraph(PhaseGraph graph) {
        namespace.remove(graph.name());
        phases.remove(graph);
    }

    public Phase phase() { return new Phase(); }

    public Transition transition(Phase from, GameEvent trigger, Phase to) {
        return new TransitionImpl(from, trigger, to);
    }
}
