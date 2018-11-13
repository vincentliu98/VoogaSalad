package groovy.graph.blocks.factory;

public class ListParseException extends Exception {
    public ListParseException(String original) {
        super("Cannot parse "+ original + " into a list.");
    }
}
