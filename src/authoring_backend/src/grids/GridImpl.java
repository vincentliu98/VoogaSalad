package grids;

import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.*;
import java.util.function.Predicate;


/**
 * Abstract representation of a Grid.
 * Grid is a class that contains a map from a cartesian coordinate to a Cell.
 * It has additional methods to support various kinds of grids.
 * @author jz192
 */


public abstract class GridImpl {
    private ObservableMap<Point, Integer> matrix;
    private int numRows;
    private int numColumns;
    private GridShape gridConfig;


    /**
     * @param numRows    number of rows
     * @param numColumns number of columns
     */
    protected GridImpl(int numRows, int numColumns, GridShape gridConfig) {
        this.matrix = FXCollections.observableHashMap();
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.gridConfig = gridConfig;
    }

    /**
     * @return matrix
     */
    public ObservableMap getMatrix() {
        return matrix;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    /**
     * calculates the actual row with wrap around
     */


    /**
     * @return true if x, y is out of bounds
     */
    public boolean outOfXBounds(int x) {
        return x < 0 || x >= numColumns;
    }

    public boolean outOfYBounds(int y) {
        return y < 0 || y >= numRows;
    }

    public boolean outOfBounds(Point position) {
        return outOfXBounds(position.getX()) || outOfYBounds(position.getY());
    }


    public Set<Point> getNeighborsOfPoints(Set<Point> points) {

        Set<Point> neighbors = new HashSet<>();
        for (Point p : points) {
            Set<Point> neighborsOfAPoint = getNeighborsOfAPoint(p);
            for (Point tentativeP : neighborsOfAPoint) {
                if (!points.contains(tentativeP)) {
                    neighbors.add(tentativeP);
                }
            }
        }
        return neighbors;
    }


    private Set<Point> getNeighborsOfAPoint(Point p) {
        Set<Point> neighbors = new HashSet<>();
        switch (gridConfig) {
            case Square:
                for (Directions.EightDirections d : Directions.EightDirections.values()) {
                    neighbors.add(p.add(d.getDirection()));
                }
            case Hexagon:
                for (Directions.SixDirections d : Directions.SixDirections.values()) {
                    neighbors.add(p.add(d.getDirection()));
                }
        }
        return neighbors;
    }

}