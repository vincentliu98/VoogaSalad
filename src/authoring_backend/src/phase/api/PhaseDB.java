package phase.api;

import groovy.api.GroovyFactory;
import phase.NamespaceException;
import phase.PhaseGraphImpl;
import phase.TransitionImpl;
import utils.Try;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  PhaseDB keeps track of all the PhaseGraphs that are "out" there,
 *  and also works as a factory for graph/phase/createTransition.
 */
public class PhaseDB {
    private Set<String> namespace;
    private List<PhaseGraph> phases;
    private GroovyFactory factory;

    public PhaseDB(GroovyFactory factory) {
        this.namespace = new HashSet<>();
        phases = new ArrayList<>();
        this.factory = factory;
    }

    public Try<PhaseGraph> createGraph(String name) {
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

    public Phase createPhase() { return new Phase(); }

    public Transition createTransition(Phase from, GameEvent trigger, Phase to) {
        return new TransitionImpl(from, trigger, to, factory.createGuard(), factory.createGraph());
    }

    public List<PhaseGraph> phases() { return phases; }
}
