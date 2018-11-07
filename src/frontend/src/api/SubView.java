package api;

import javafx.scene.Node;

/**
 * This interface represents a front end element that has some JavaFx Nodes and potentially logic in it. The only method getView() returns a JavaFx Node that can be added to some other JavaFx Nodes in a hierarchical structure.
 */
@FunctionalInterface
public interface SubView {
    Node getView();
}
