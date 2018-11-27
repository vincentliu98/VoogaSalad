package grids;

import gameObjects.TriFunction;

import java.util.HashSet;
import java.util.Set;

/**
 *
 *
 * @author Jason Zhou
 */
public interface Point {

    static TriFunction<Point, Integer, Integer, Boolean> verifyPointsFunc() {
        return Point::outOfBounds;
    }

    /**
     *
     * @param points
     * @param numRow
     * @param numCol
     * @return
     */
    static Set<Point> getNeighborsOfPoints(Set<Point> points, int numRow, int numCol) {
        Set<Point> neighbors = new HashSet<>();
        for (Point p : points) {
            Set<Point> neighborsOfAPoint = getNeighborsOfAPoint(p, numRow, numCol);
            for (Point tentativeP : neighborsOfAPoint) {
                if (!points.contains(tentativeP)) {
                    neighbors.add(tentativeP);
                }
            }
        }
        return neighbors;
    }

    /**
     *
     * @param p
     * @param numRow
     * @param numCol
     * @return
     */
    static Set<Point> getNeighborsOfAPoint(Point p, int numRow, int numCol) {
        Set<Point> neighbors = new HashSet<>();
            for (Directions.EightDirections d : Directions.EightDirections.values()) {
                Point newPoint = p.add(d.getDirection());
                if (!outOfBounds(newPoint, numRow, numCol)) {
                    neighbors.add(newPoint);
                }
            }
        return neighbors;
    }


    /**
     *
     * @param x
     * @param numColumns
     * @return
     */
    static boolean outOfXBounds(int x, int numColumns) {
        return x < 0 || x >= numColumns;
    }

    static boolean outOfYBounds(int y, int numRows) {
        return y < 0 || y >= numRows;
    }

    static boolean outOfBounds(Point position, int numRow, int numCol) {
        return outOfXBounds(position.getX(), numCol) || outOfYBounds(position.getY(), numRow);
    }

    /**
     *
     * @return
     */
    int getX();

    /**
     *
     * @return
     */
    int getY();

    /**
     *
     * @param x
     * @param y
     * @return
     */
    Point add(int x, int y);

    /**
     *
     * @param p
     * @return
     */
    Point add(Point p);

    /**
     *
     * @param p
     * @return
     */
    boolean equals(Object p);

    /**
     *
     * @return
     */
    int hashCode();

    /**
     *
     * @return
     */
    String toString();
}
