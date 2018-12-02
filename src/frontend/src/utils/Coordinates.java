package utils;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * This class provides convenient methods to set the coordinates of some JavaFx Nodes.
 *
 * @author Haotian Wang
 */
public class Coordinates {
    /**
     * This method sets the coordinates for an unknown type of JavaFx Node.
     *
     * @param node: A Node whose coordinates will be set.
     * @param x: A double value for the x coordinate.
     * @param y: A double value for the y coordinate.
     * @throws UnhandledCoordinatesClassException
     */
    public static void setXAndY(Node node, double x, double y) throws UnhandledCoordinatesClassException {
        if (node instanceof ImageView) {
            ((ImageView) node).setX(x);
            ((ImageView) node).setY(y);
        } else if (node instanceof Text) {
            ((Text) node).setX(x);
            ((Text) node).setY(y);
        } else {
            throw new UnhandledCoordinatesClassException("The setXAndY method is not defined for this particular JavaFx Node class.");
        }
    }
}
