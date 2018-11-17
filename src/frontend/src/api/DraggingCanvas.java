package api;

/**
 * This interface symbolizes a dragging canvas where the dragging of objects occurred.
 *
 * @author Haotian Wang
 */
@FunctionalInterface
public interface DraggingCanvas {
    /**
     * Setup the dragging canvas event filters.
     */
    void setupDraggingCanvas();
}
