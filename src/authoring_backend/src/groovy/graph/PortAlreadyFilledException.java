package authoring_backend.src.groovy.graph;

import authoring_backend.src.groovy.graph.blocks.GroovyBlock;

public class PortAlreadyFilledException extends Exception {
    public PortAlreadyFilledException(GroovyBlock from, Ports port) {
        super("There's already a block connected to port "+port.name() + " of " + from);
    }
}
