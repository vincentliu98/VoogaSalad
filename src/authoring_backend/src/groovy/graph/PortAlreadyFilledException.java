package groovy.graph;

import groovy.graph.blocks.GroovyBlock;

public class PortAlreadyFilledException extends Exception {
    public PortAlreadyFilledException(GroovyBlock from, Ports port) {
        super("There's already a block connected to port "+port.name() + " of " + from);
    }
}
