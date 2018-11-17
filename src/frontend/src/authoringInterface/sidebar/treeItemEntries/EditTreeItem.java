package authoringInterface.sidebar.treeItemEntries;

import javafx.scene.Node;

/**
 * This interface abstracts the meaning of draggable components of the graphical interface. Dragable simply means the user can press down the mouse key on an element and drag the element along the program which displays a defined preview of the elements being dragged.
 *
 * @author Haotian Wang
 */
public interface EditTreeItem<T extends Node> {
    /**
     * @return Return a preview of the elements being dragged.
     */
    T getPreview();

    /**
     * @return The type of the element being dragged.
     */
    TreeItemType getType();

    /**
     * @return The String name of this edit item entry.
     */
    String getName();

    /**
     * Set the name of this item entry.
     *
     * @param name: A new String name for this entry.
     */
    void setName(String name);
}
