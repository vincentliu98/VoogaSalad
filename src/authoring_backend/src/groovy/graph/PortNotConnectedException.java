package authoring_backend.src.groovy.graph;

import authoring_backend.src.groovy.graph.blocks.GroovyBlock;

public class PortNotConnectedException extends Exception {
    public PortNotConnectedException(GroovyBlock from, Ports port) {
        super("Can't find a block connected to port "+port.name() + " of " + from);
    }
}
