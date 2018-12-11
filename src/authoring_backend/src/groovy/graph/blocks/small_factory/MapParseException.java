package groovy.graph.blocks.small_factory;

public class MapParseException extends Exception {
    public MapParseException(String original) {
        super("Cannot parse " + original + " into a map.");
    }
}

