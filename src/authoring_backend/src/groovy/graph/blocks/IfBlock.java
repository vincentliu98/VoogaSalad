package groovy.graph.blocks;

import essentials.GameData;
import graph.SimpleNode;
import groovy.graph.Ports;

import java.util.List;
import java.util.Set;

import static groovy.graph.Ports.*;

public class IfBlock extends SimpleNode implements GroovyBlock<IfBlock> {
    public IfBlock(int id, String name) { super(id, name); }
    public IfBlock(int id) { super(id); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_IN, FLOW_OUT, IF_PREDICATE, IF_BODY); }

    @Override
    public String toGroovy() { return ""; }

    @Override
    public IfBlock replicate() { return null; }

    @Override
    public List<?> suggestions(GameData data) { return List.of(); }
}
