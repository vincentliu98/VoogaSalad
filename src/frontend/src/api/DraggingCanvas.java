package api;

import javafx.scene.Node;

/**
 * This interface handles the dragging motion of a JavaFx node.
 *
 * @author Haotian Wang
 */
@FunctionalInterface
public interface DraggingCanvas {
    /**
     * This method sets up event handlers for mouse dragging events.
     *
     * @param preview
     */
    void setTempPreview(Node preview);
}
