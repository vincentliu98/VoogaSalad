package grids;

import gameObjects.TriFunction;

import java.util.HashSet;
import java.util.Set;

public interface Point {


    static TriFunction<Point, Integer, Integer, Boolean> verifyPointsFunc() {
        return Point::outOfBounds;
    }

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


    static boolean outOfXBounds(int x, int numColumns) {
        return x < 0 || x >= numColumns;
    }

    static boolean outOfYBounds(int y, int numRows) {
        return y < 0 || y >= numRows;
    }

    static boolean outOfBounds(Point position, int numRow, int numCol) {
        return outOfXBounds(position.getX(), numCol) || outOfYBounds(position.getY(), numRow);
    }


    int getX();
    int getY();
    Point add(int x, int y);
    Point add(Point p);
    boolean equals(Object p);
    int hashCode();
    String toString();
}
