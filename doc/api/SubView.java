package api;

import javafx.scene.Node;

/**
 * This interface represents a front end element that has some JavaFx Nodes and potentially logic in it. The only method getView() returns a JavaFx Node that can be added to some other JavaFx Nodes in a hierarchical structure.
 *
 * @author Haotian Wang
 */
@FunctionalInterface
public interface SubView {
    /**
     * This method returns the responsible JavaFx Node responsible to be added or deleted from other graphical elements.
     *
     * @return A "root" JavaFx Node representative of this object.
     */
    Node getView();
}
