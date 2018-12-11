package graph;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents a Node within the graph.
 */
public interface Node {
    double x();

    double y();

    void setXY(double x, double y);

    SimpleStringProperty description();
}
