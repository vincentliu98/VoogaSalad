package groovy.graph.blocks.literal;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

public class StringBlock extends SimpleNode implements GroovyBlock<StringBlock> {
    private SimpleStringProperty model;

    public StringBlock(String name) {
        super(name);
        model = new SimpleStringProperty("");
    }

    public StringBlock(String name, String value) {
        super(name);
        model = new SimpleStringProperty(value);
    }

    public SimpleStringProperty model() { return model; }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(model.get()); }

    @Override
    public StringBlock replicate() { return new StringBlock(name().get(), model.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
