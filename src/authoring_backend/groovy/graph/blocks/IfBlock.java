package authoring_backend.groovy.graph.blocks;

import authoring_backend.essentials.GameData;
import authoring_backend.graph.SimpleNode;
import authoring_backend.groovy.graph.Ports;

import java.util.List;
import java.util.Set;

import static authoring_backend.groovy.graph.Ports.*;

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
