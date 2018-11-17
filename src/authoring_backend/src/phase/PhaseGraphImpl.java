package phase;

import graph.SimpleGraph;
import phase.api.Phase;
import phase.api.PhaseGraph;
import phase.api.Transition;

import java.util.function.Function;

public class PhaseGraphImpl extends SimpleGraph<Phase, Transition> implements PhaseGraph {
    private Phase source;
    private String name;
    private Function<String, Boolean> registerName;

    public PhaseGraphImpl(String name, Phase source, Function<String, Boolean> registerName) {
        super();
        this.source = source;
        this.source.description().setValue("source");
        this.name = name;
        this.registerName = registerName;
    }

    @Override
    public String name() { return name; }

    @Override
    public boolean setName(String another) {
        var res = registerName.apply(another);
        if(res) name = another;
        return res;
    }

    @Override
    public Phase source() { return source; }
}
