package grids;

import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Abstract representation of a Grid.
 * Grid is a class that contains a map from a cartesian coordinate to a Cell.
 * It has additional methods to support various kinds of grids.
 * @author jz192
 */


public abstract class GridImpl {
    protected ObservableMap<Point, Integer> matrix;
    protected int numRows;
    protected int numColumns;
    protected Directions.NoOfNeighbors gridConfig;

    protected Random random;


    /**
     * @param numRows    number of rows
     * @param numColumns number of columns
     */
    protected GridImpl(int numRows, int numColumns, Directions.NoOfNeighbors gridConfig) {
        this.matrix = FXCollections.observableHashMap();
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.gridConfig = gridConfig;
        this.random = new Random();
    }

    /**
     * @return matrix
     */
    protected ObservableMap getMatrix() {
        return matrix;
    }

    protected int getNumRows() {
        return numRows;
    }

    protected int getNumColumns() {
        return numColumns;
    }

    /**
     * calculates the actual row with wrap around
     */


    /**
     * @return true if x, y is out of bounds
     */
    protected boolean outOfXBounds(int x) {
        return x < 0 || x >= numColumns;
    }

    protected boolean outOfYBounds(int y) {
        return y < 0 || y >= numRows;
    }

    protected boolean outOfBounds(Point position) {
        return outOfXBounds(position.getX()) && outOfYBounds(position.getY());
    }

    /**
     * get Tile Id with coordinate
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    protected Point getPosition(int x, int y) {
        return new Point(gridColumnWrap(x), gridRowWrap(y));
    }

    protected Point getPosition(Point position) {
        return getPosition(position.getX(), position.getY());
    }
}