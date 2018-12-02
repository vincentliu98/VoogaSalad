package groovy.graph.blocks.core;

public class ArgNumberMismatchException extends Exception {
    public ArgNumberMismatchException(int required, int actual) {
        super("Number of arguments don't match; required: " + required + " actual: " + actual);
    }
}
