package groovy.graph.blocks.literal;

import groovy.graph.BlockGraph;
import groovy.graph.blocks.GroovyBlock;
import graph.SimpleNode;
import groovy.Try;
import groovy.graph.Ports;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

public class StringBlock extends SimpleNode implements GroovyBlock<StringBlock> {
    private SimpleStringProperty model;

    public StringBlock() {
        model = new SimpleStringProperty("");
    }

    public StringBlock(String value) {
        model = new SimpleStringProperty(value);
    }

    public SimpleStringProperty model() { return model; }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(model.get()); }

    @Override
    public StringBlock replicate() { return new StringBlock(model.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
