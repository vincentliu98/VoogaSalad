package grids;

import java.util.ArrayList;

/**
 * Abstract representation of directions. Includes method that returns the direction vector.
 * This Class is used to get the position of the neighbours by defining all the possible locations of neighbors in an enum.
 * @author jz192
 */


public class Directions {

    /**
     * This method returns a list of points of neighbor cells based on the enum passed in.
     * @param n
     * @return ArrayList of Points
     */
    public static final ArrayList<Point> getShape(NoOfNeighbors n) {
        ArrayList<Point> points = new ArrayList<>();
        switch (n) {
            case THREE:
                for (Directions.ThreeDirections directions : ThreeDirections.values()) {
                    points.add(directions.getDirection());
                }
                break;
            case FOUR:
                for (Directions.FourDirections directions : FourDirections.values()) {
                    points.add(directions.getDirection());
                }
                break;
            case SIX:
                for (Directions.SixDirections directions : SixDirections.values()) {
                    points.add(directions.getDirection());
                }
                break;
            case EIGHT:
                for (Directions.EightDirections directions : EightDirections.values()) {
                    points.add(directions.getDirection());
                }
                break;
            case TWELVE:
                for (Directions.TwelveDirections directions : TwelveDirections.values()) {
                    points.add(directions.getDirection());
                }
                break;

        }
        return points;
    }

    public enum NoOfNeighbors {
        THREE,
        FOUR,
        SIX,
        EIGHT,
        TWELVE;
    }

    public enum EightDirections {
        NW (-1, -1),
        N (0, -1),
        NE (1, -1),
        W (-1, 0),
        E (1, 0),
        SW (-1, 1),
        S (0, 1),
        SE (1, 1);

        // declaring private variable for getting values
        private Point vector;

        // getter method
        public Point getDirection()
        {
            return this.vector;
        }

        EightDirections(int x, int y)
        {
            this.vector = new PointImpl(x, y);
        }
    }

    public enum FourDirections {
        N (0, -1),
        W (-1, 0),
        E (1, 0),
        S (0, 1);

        // declaring private variable for getting values
        private Point vector;

        // getter method
        public Point getDirection()
        {
            return this.vector;
        }

        FourDirections(int x, int y)
        {
            this.vector = new PointImpl(x, y);
        }
    }

    public enum SixDirections {
        NW (-1, -1),
        N (0, -1),
        NE (1, -1),
        W (-1, 0),
        E (1, 0),
        S (0, 1);


        // declaring private variable for getting values
        private Point vector;

        // getter method
        public Point getDirection()
        {
            return this.vector;
        }

        SixDirections(int x, int y)
        {
            this.vector = new PointImpl(x, y);
        }
    }

    public enum TwelveDirections {
        N (0, -1),
        NE (1, -1),
        NEE (2, -1),
        WW (-2, 0),
        W (-1, 0),
        E (1, 0),
        EE (2, 0),
        SWWW (-3, 1),
        SWW (-2, 1),
        SW (-1, 1),
        S (0, 1),
        SE (1, 1);

        // declaring private variable for getting values
        private Point vector;

        // getter method
        public Point getDirection()
        {
            return this.vector;
        }

        TwelveDirections(int x, int y)
        {
            this.vector = new PointImpl(x, y);
        }
    }

    public enum ThreeDirections {

        W (-1, 0),
        E (1, 0),
        SW (-1, 1);

        // declaring private variable for getting values
        private Point vector;

        // getter method
        public Point getDirection()
        {
            return this.vector;
        }

        ThreeDirections(int x, int y)
        {
            this.vector = new PointImpl(x, y);
        }
    }


}
