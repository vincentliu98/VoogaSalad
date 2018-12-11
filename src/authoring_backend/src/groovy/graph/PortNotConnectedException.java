package groovy.graph;

import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;

public class PortNotConnectedException extends Exception {
    public PortNotConnectedException(GroovyBlock from, Ports port) {
        super("Can't find a block connected to port " + port.name() + " of " + from);
    }
}
