package grids;

/**
 * Abstract representation of directions. Includes method that returns the direction vector.
 * This Class is used to get the position of the neighbours by defining all the possible locations of neighbors in an enum.
 *
 * @author jz192
 */


public class Directions {

    public enum EightDirections {
        NW(-1, -1),
        N(0, -1),
        NE(1, -1),
        W(-1, 0),
        E(1, 0),
        SW(-1, 1),
        S(0, 1),
        SE(1, 1);

        // declaring private variable for getting values
        private Point vector;

        EightDirections(int x, int y) {
            this.vector = new PointImpl(x, y);
        }

        // getter method
        public Point getDirection() {
            return this.vector;
        }
    }

    public enum SixDirections {
        NW(-1, -1),
        N(0, -1),
        NE(1, -1),
        W(-1, 0),
        E(1, 0),
        S(0, 1);


        // declaring private variable for getting values
        private Point vector;

        SixDirections(int x, int y) {
            this.vector = new PointImpl(x, y);
        }

        // getter method
        public Point getDirection() {
            return this.vector;
        }
    }

}
