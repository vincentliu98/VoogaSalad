package graph;

import javafx.beans.property.SimpleStringProperty;

/**
 * Minimal implementation of a Node, just to allow
 * potential subclasses implementing Node interface to have basic operations set.
 */
public class SimpleNode implements Node {
    private SimpleStringProperty name;
    private SimpleStringProperty description;

    public SimpleNode(String name) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty("");
    }

    @Override
    public SimpleStringProperty name() { return name; }

    @Override
    public SimpleStringProperty description() { return description; }

    @Override
    public String toString() {
        return "{\n\tname: "+name.get()+"\n}";
    }
}
