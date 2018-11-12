package graph;

import javafx.beans.property.SimpleStringProperty;

/**
 *  Represents a Node within the graph.
 */
public interface Node {
    SimpleStringProperty name();
    SimpleStringProperty description();
}
