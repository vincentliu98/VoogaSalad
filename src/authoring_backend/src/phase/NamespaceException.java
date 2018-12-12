package phase;

public class NamespaceException extends Exception {
    public NamespaceException(String name) {
        super(name + " is already in use");
    }
}
