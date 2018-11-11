package graph;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *  Represents a Node within the graph.
 */
public interface Node {
    SimpleIntegerProperty id();
    SimpleStringProperty name();
    SimpleStringProperty description();
}
