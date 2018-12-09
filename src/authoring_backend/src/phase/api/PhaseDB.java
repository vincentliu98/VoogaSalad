package phase.api;

import authoringUtils.frontendUtils.Try;
import groovy.api.BlockGraph;
import groovy.api.GroovyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import phase.NamespaceException;
import phase.PhaseGraphImpl;
import phase.PhaseImpl;
import phase.TransitionImpl;
import utility.ObservableUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  PhaseDB keeps track of all the PhaseGraphs that are "out" there,
 *  and also works as a factory for graph/phase/createTransition.
 */
public class PhaseDB {
    private Set<String> namespace;
    private Set<String> phaseNamespace;
    private ObservableList<PhaseGraph> phaseGraphs;
    private ObservableList<String> phaseName;
    private GroovyFactory factory;
    private String startingPhase;
    private BlockGraph winningCondition;

    public PhaseDB(GroovyFactory factory) {
        this.namespace = new HashSet<>();
        this.phaseNamespace = new HashSet<>();
        phaseGraphs = FXCollections.observableArrayList();
        phaseName = FXCollections.observableArrayList();
        ObservableUtils.bindList(phaseGraphs, phaseName, PhaseGraph::name);
        this.factory = factory;
        winningCondition = factory.createGroovyGraph();
    }

    public Try<PhaseGraph> createPhaseGraph(String name) {
        var trySource = createPhase(name);
        if(namespace.add(name)) {
            Try<PhaseGraph> graph = trySource.map(s -> new PhaseGraphImpl(name, s, namespace::add));
            graph.forEach(phaseGraphs::add);
            if(namespace.size() == 1) startingPhase = name; // if there's only one phase, that's the starting one
            return graph;
        } else return Try.failure(new NamespaceException(name));
    }

    public void removeGraph(PhaseGraph graph) {
        namespace.remove(graph.name());
        phaseGraphs.remove(graph);
    }

    public Try<Phase> createPhase(String name) {
        if (phaseNamespace.add(name)) {
            return Try.success(new PhaseImpl(factory.createGroovyGraph(), name));
        } else return Try.failure(new NamespaceException(name));
    }

    public Transition createTransition(Phase from, GameEvent trigger, Phase to) {
        return new TransitionImpl(from, trigger, to, factory.createGroovyGraph());
    }

    public List<PhaseGraph> phases() { return phaseGraphs; }

    public void setStartingPhase(String phaseName) { startingPhase = phaseName; }
    public String getStartingPhase() { return startingPhase; }

    public BlockGraph heartbeat() { return winningCondition; }
    public ObservableList<String> phaseNames() { return phaseName; }
}
