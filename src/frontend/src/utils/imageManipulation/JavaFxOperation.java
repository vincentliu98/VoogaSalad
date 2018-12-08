package utils.imageManipulation;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import utils.exception.GridIndexOutOfBoundsException;
import utils.exception.UnhandledCoordinatesClassException;
import utils.exception.UnremovableNodeException;

import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class provides convenient methods to set the coordinates of some JavaFx Nodes.
 *
 * @author Haotian Wang
 */
public class JavaFxOperation {
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

    /**
     * This method sets the width and height of an ImageView.
     *
     * @param imageView: The ImageView object whose height and width to be changed.
     * @param width: The width to be set.
     * @param height: The height to be set.
     */
    public static void setWidthAndHeight(ImageView imageView, double width, double height) {
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    /**
     * This method removes a child Node from its Parent in JavaFx.
     *
     * @param node: The Node which is to be removed from its Parent.
     * @throws UnremovableNodeException
     */
    public static void removeFromParent(Node node) throws UnremovableNodeException {
        if (!(node.getParent() instanceof Pane)) { throw new UnremovableNodeException("The node could not be removed from its parent because its Parent is not Pane, and therefore it does not have a modifiable ObservableList of children Nodes"); }
        ((Pane) node.getParent()).getChildren().remove(node);
    }

    /**
     *
     * @param parentGrid
     * @param row
     * @param col
     * @param <E>
     * @return
     * @throws GridIndexOutOfBoundsException
     */
    @SuppressWarnings("unchecked")
    public static <E extends Node> E getNodeFromGridPaneByIndices(GridPane parentGrid, int row, int col) throws GridIndexOutOfBoundsException {
        if (row < 0 || row >= parentGrid.getRowCount() || col < 0 || col >= parentGrid.getColumnCount()) {
            throw new GridIndexOutOfBoundsException(String.format("Either row %d or colum %d is out of the bounds of the GridPane", row, col));
        }
        for (Node node : parentGrid.getChildrenUnmodifiable()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return (E) node;
            }
        }
        return null;
    }
}
