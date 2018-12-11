package graph;

import javafx.beans.property.SimpleStringProperty;

/**
 * Minimal implementation of a Node, just to allow
 * potential subclasses implementing Node interface to have basic operations set.
 */
public class SimpleNode implements Node {
    private SimpleStringProperty description;
    private double x, y;

    public SimpleNode(double x, double y) {
        this.description = new SimpleStringProperty("");
        this.setXY(x, y);
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public void setXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public SimpleStringProperty description() {
        return description;
    }
}
