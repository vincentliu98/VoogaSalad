package authoring_backend.src.graph;

import javafx.beans.property.SimpleStringProperty;

/**
 * Minimal implementation of a Node, just to allow
 * potential subclasses implementing Node interface to have basic operations set.
 */
public class SimpleNode implements Node {
    private SimpleStringProperty description;

    public SimpleNode() {
        this.description = new SimpleStringProperty("");
    }

    @Override
    public SimpleStringProperty description() { return description; }
}
