package grids;

import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Abstract representation of a Grid.
 * Grid is a class that contains a map from a cartesian coordinate to a Cell.
 * It has additional methods to support various kinds of grids.
 * @author jz192
 */


public class GridImpl implements Grid {
    private int numRows;
    private int numColumns;
    private GridShape gridConfig;


    /**
     * @param numRows    number of rows
     * @param numColumns number of columns
     */
    public GridImpl(int numRows, int numColumns, GridShape gridConfig) {
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.gridConfig = gridConfig;
    }

    @Override
    public int getNumRows() {
        return numRows;
    }

    @Override
    public int getNumColumns() {
        return numColumns;
    }


    /**
     * @return true if x, y is out of bounds
     */
    public boolean outOfXBounds(int x) {
        return x < 0 || x >= numColumns;
    }

    public boolean outOfYBounds(int y) {
        return y < 0 || y >= numRows;
    }

    @Override
    public boolean outOfBounds(Point position) {
        return outOfXBounds(position.getX()) || outOfYBounds(position.getY());
    }

    @Override
    public Function<Set<Point>, Boolean> verifyPointsFunc() {
        return points -> {
            boolean outOfBounds = false;
            for (Point p : points) {
                if (outOfBounds(p)) {
                    outOfBounds = true;
                }
            }
            return outOfBounds;
        };
    }

    @Override
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
                    Point newPoint = p.add(d.getDirection());
                    if (!outOfBounds(newPoint)) {
                        neighbors.add(newPoint);
                    }
                }
            case Hexagon:
                for (Directions.SixDirections d : Directions.SixDirections.values()) {
                    Point newPoint = p.add(d.getDirection());
                    if (!outOfBounds(newPoint)) {
                        neighbors.add(newPoint);
                    }
                }
        }
        return neighbors;
    }

}