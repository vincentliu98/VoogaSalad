package groovy.graph.blocks.small_factory;

public class NoSuchPropertyException extends Exception {
    public NoSuchPropertyException(String propertyName) {
        super("There's no property " + propertyName + " defined on ANY game object");
    }
}
