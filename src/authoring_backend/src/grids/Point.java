package grids;

public interface Point {
    int getX();
    int getY();
    Point add(int x, int y);
    Point add(Point p);
    boolean equals(Object p);
    int hashCode();
    String toString();
}
