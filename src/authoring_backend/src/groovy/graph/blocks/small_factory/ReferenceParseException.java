package groovy.graph.blocks.small_factory;

public class ReferenceParseException extends Exception {
    public ReferenceParseException(String original) {
        super("Cannot parse " + original + " into a list.");
    }
}
