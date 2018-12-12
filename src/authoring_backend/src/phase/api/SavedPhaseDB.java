package phase.api;

import groovy.api.BlockGraph;

import java.util.ArrayList;
import java.util.Set;

public class SavedPhaseDB {
    private Set<String> namespace;
    private ArrayList<PhaseGraph> phaseGraphs;
    private String startingPhase;
    private BlockGraph winningCondition;

    public SavedPhaseDB(
            Set<String> namespace,
            ArrayList<PhaseGraph> phaseGraphs,
            String startingPhase,
            BlockGraph winningCondition
    ) {
        this.namespace = namespace;
        this.phaseGraphs = phaseGraphs;
        this.startingPhase = startingPhase;
        this.winningCondition = winningCondition;
    }

    public Set<String> namespace() {
        return namespace;
    }

    public ArrayList<PhaseGraph> phaseGraphs() {
        return phaseGraphs;
    }

    public String startingPhase() {
        return startingPhase;
    }

    public BlockGraph winningCondition() {
        return winningCondition;
    }
}

