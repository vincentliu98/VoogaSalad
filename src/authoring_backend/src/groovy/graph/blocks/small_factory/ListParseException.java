package groovy.graph.blocks.small_factory;

public class ListParseException extends Exception {
    public ListParseException(String original) {
        super("Cannot parse " + original + " into a list.");
    }
}


