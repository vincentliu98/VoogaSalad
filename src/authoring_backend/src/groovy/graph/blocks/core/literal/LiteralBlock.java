package groovy.graph.blocks.core.literal;

import graph.SimpleNode;
import groovy.Try;
import groovy.graph.BlockGraph;
import groovy.graph.Ports;
import groovy.graph.blocks.GroovyBlock;
import javafx.beans.property.SimpleStringProperty;

import java.util.Set;

/**
 *  This literal block should contain one of the following;
 *  numeric value   ex) 1234
 *  string          ex) "haha"
 *  list            ex) [ 1, 3, "4", 5 ]
 *  map             ex) [ 1:5, "from":5, "to":10 ]
 */
public class LiteralBlock extends SimpleNode implements GroovyBlock<LiteralBlock> {
    private SimpleStringProperty model;

    public LiteralBlock(String value) {
        super();
        model = new SimpleStringProperty(value);
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(model.get()); }

    @Override
    public LiteralBlock replicate() { return new LiteralBlock(model.get()); }

    @Override
    public Set<Ports> ports() { return Set.of(); }
}
