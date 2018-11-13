package authoringInterface.sidebar;

import api.SubView;
import javafx.scene.Node;

/**
 * This interface abstracts the meaning of draggable components of the graphical interface. Dragable simply means the user can press down the mouse key on an element and drag the element along the program which displays a defined preview of the elements being dragged.
 *
 * @author Haotian Wang
 */
public interface DraggableTreeItem<T extends Node, V extends TreeItemType> {
    /**
     * @return Return a preview of the elements being dragged.
     */
    T getPreview();

    /**
     * @return The type of the graphical element where the drag starts.
     */
    V getType();

    /**
     * Register with the parent "canvas" JavaFx node such that the preview can be shown there.
     */
    void notifyCanvas(Node canvas);
}
