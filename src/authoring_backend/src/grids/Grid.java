package grids;

import java.util.Set;
import java.util.function.Function;

public interface Grid {

    int getNumRows();

    int getNumColumns();

    boolean outOfBounds(Point p);

    Function<Set<Point>, Boolean> verifyPointsFunc();

    Set<Point> getNeighborsOfPoints(Set<Point> points);
}
