package authoring_backend.src.groovy.graph;

import authoring_backend.src.groovy.graph.blocks.GroovyBlock;

public class TypeCheckException extends Exception {
    public TypeCheckException(GroovyBlock from, Ports port, GroovyBlock to, String cause) {
        super("Type check failed "+port.name() + " of " + from);
    }
}
