package authoringInterface.sidebar;

import api.SubView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * This interface abstracts the meaning of draggable components of the graphical interface. Dragable simply means the user can press down the mouse key on an element and drag the element along the program which displays a defined preview of the elements being dragged.
 *
 * @author Haotian Wang
 */
public interface DraggableTreeItem<T extends Node> {
    /**
     * @return Return a preview of the elements being dragged.
     */
    T getPreview();

    /**
     * @return The type of the element being dragged.
     */
    TreeItemType getType();

    /**
     * Handle the start of dragging action on a DraggableTreeItem.
     */
    void handleDrag(Pane canvas);
}
