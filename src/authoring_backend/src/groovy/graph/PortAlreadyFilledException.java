package groovy.graph;

import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;

public class PortAlreadyFilledException extends Exception {
    public PortAlreadyFilledException(GroovyBlock from, Ports port) {
        super("There's already a block connected to port " + port.name() + " of " + from);
    }
}
