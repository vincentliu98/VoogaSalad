package authoring_backend.graph;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Minimal implementation of a Node, just to allow
 * potential subclasses implementing Node interface to have basic operations set.
 */
public class SimpleNode implements Node {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty description;

    public SimpleNode(int id, String name) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty("");
    }

    public SimpleNode(int id) { this(id, Node.class.getName()+id); }

    @Override
    public SimpleIntegerProperty id() { return id; }

    @Override
    public SimpleStringProperty name() { return name; }

    @Override
    public SimpleStringProperty description() { return description; }
}
