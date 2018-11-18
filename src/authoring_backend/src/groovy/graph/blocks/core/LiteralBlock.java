package groovy.graph.blocks.core;

import graph.SimpleNode;
import groovy.api.BlockGraph;
import groovy.api.Ports;
import utils.Try;

import java.util.Set;

/**
 *  This literal block should contain one of the following;
 *  boolean         ex) true, false
 *  numeric value   ex) 1234
 *  string          ex) "haha"
 *  list            ex) [ 1, 3, "4", 5 ]
 *  map             ex) [ 1:5, "from":5, "to":10 ]
 *  ref             ex) $clicked.hp
 */
public class LiteralBlock extends SimpleNode implements GroovyBlock<LiteralBlock> {
    private String value, type;
    public LiteralBlock(String value, String type) {
        super();
        this.value = value;
        this.type = type;
    }

    @Override
    public Try<String> toGroovy(BlockGraph graph) { return Try.success(value); }

    @Override
    public LiteralBlock replicate() { return new LiteralBlock(value, type); }

    @Override
    public Set<Ports> ports() { return Set.of(); }

    @Override
    public String name() { return type + ": " + value; }
}
