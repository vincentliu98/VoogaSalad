package authoring_backend.groovy.graph.blocks;

import authoring_backend.essentials.GameData;
import authoring_backend.graph.SimpleNode;
import authoring_backend.groovy.graph.Ports;

import java.util.List;
import java.util.Set;

import static authoring_backend.groovy.graph.Ports.FLOW_OUT;

public class SourceBlock extends SimpleNode implements GroovyBlock<SourceBlock> {
    public SourceBlock(int id, String name) { super(id, name); }
    public SourceBlock(int id) { super(id); }

    @Override
    public Set<Ports> ports() { return Set.of(FLOW_OUT); }

    @Override
    public String toGroovy() {
        return null;
    }

    @Override
    public SourceBlock replicate() {
        return null;
    }

    @Override
    public List<?> suggestions(GameData data) {
        return null;
    }

}
