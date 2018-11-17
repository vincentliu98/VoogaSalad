package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;

/**
 * This interface represents the common characteristics shared across editors for different user-defined objects such as Entity, Sound and Tile.
 *
 * @author Haotian Wang
 */
public interface ObjectEditor<T extends EditTreeItem> {
    /**
     * This method brings up an editor that contains the data of an existing object that is already created.
     *
     * @param userObject
     */
    void readObject(T userObject);

    /**
     * Return the object after edits in this ObjectEditor.
     *
     * @return A specific user object.
     */
    T getObject();

    /**
     * Return a boolean indicating whether the changes are successfully applied.
     *
     * @return
     */
    boolean applied();
}
