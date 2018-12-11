package phase.api;

import authoringUtils.frontendUtils.Try;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import groovy.api.BlockGraph;
import groovy.api.GroovyFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import phase.NamespaceException;
import phase.PhaseGraphImpl;
import phase.PhaseImpl;
import phase.TransitionImpl;
import utility.ObservableUtils;

import java.util.*;

/**
 * PhaseDB keeps track of all the PhaseGraphs that are "out" there,
 * and also works as a factory for graph/phase/createTransition.
 */
public class PhaseDB {
    private Set<String> namespace;
    private ObservableList<PhaseGraph> phaseGraphs;
    private ObservableList<String> phaseNames;
    private String startingPhase;
    private BlockGraph winningCondition;

    private GroovyFactory factory;

    public PhaseDB() {
        namespace = new HashSet<>();
        phaseGraphs = FXCollections.observableArrayList();
        phaseNames = FXCollections.observableArrayList();
        ObservableUtils.bindList(phaseGraphs, phaseNames, PhaseGraph::name);
    }

    public PhaseDB(GroovyFactory factory) {
        this();
        this.factory = factory;
        winningCondition = factory.createEmptyGraph();
    }

    /**
     * Initialize from XML
     */
    public PhaseDB(GroovyFactory factory, String xml) {
        this();
        this.factory = factory;

        var xstream = new XStream(new DomDriver());
        var savedDB = (SavedPhaseDB) xstream.fromXML(xml);
        this.namespace = savedDB.namespace();
        this.winningCondition = savedDB.winningCondition();
        this.phaseGraphs.addAll(savedDB.phaseGraphs()); // addAll to invoke listener
        this.startingPhase = savedDB.startingPhase();
    }

    public Try<PhaseGraph> createPhaseGraph(String name) {
        var trySource = createPhase(100, 50, name, true); // this dirty little secret should be fixed
        if (namespace.add(name)) {
            Try<PhaseGraph> graph = trySource.map(s -> new PhaseGraphImpl(name, s, namespace::add));
            graph.forEach(phaseGraphs::add);
            if (namespace.size() == 1) startingPhase = name; // if there's only one phase, that's the starting one
            return graph;
        } else return Try.failure(new NamespaceException(name));
    }

    public void removeGraph(PhaseGraph graph) {
        namespace.remove(graph.name());
        phaseGraphs.remove(graph);
    }

    public Try<Phase> createPhase(double x, double y, String name) {
        return createPhase(x, y, name, false);
    }

    public Try<Phase> createPhase(double x, double y, String name, boolean isSource) {
        if (!phaseNames.contains(name)) {
            return Try.success(new PhaseImpl(x, y, factory.createEmptyGraph(), name, isSource));
        } else return Try.failure(new NamespaceException(name));
    }

    public Transition createTransition(Phase from, GameEvent trigger, Phase to) {
        return new TransitionImpl(from, trigger, to, factory.createDefaultGuard());
    }

    public List<PhaseGraph> phaseGraphs() {
        return phaseGraphs;
    }

    public String getStartingPhase() {
        return startingPhase;
    }

    public void setStartingPhase(String phaseName) {
        startingPhase = phaseName;
    }

    public ObservableList<String> phaseNames() {
        return phaseNames;
    }

    public BlockGraph winCondition() {
        return winningCondition;
    }

    /**
     * Serialize to XML
     */
    public String toXML() {
        var xstream = new XStream(new DomDriver());
        return xstream.toXML(
                new SavedPhaseDB(
                        new TreeSet<>(namespace),
                        new ArrayList<>(phaseGraphs),
                        startingPhase,
                        winningCondition
                )
        );
    }
}
