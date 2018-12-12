/*
package authoringInterface.sidebar.subEditors;

import api.SubView;
import authoringInterface.sidebar.treeItemEntries.EditTreeItem;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;

import java.util.Map;

*/
/**
 * This interface represents the common characteristics shared across editors for different user-defined objects such as Entity, Sound and Tile.
 *
 * @author Haotian Wang
 * <p>
 * This method brings up an editor that contains the data of an existing object that is already created.
 * @param userObject
 * <p>
 * Return the object after edits in this ObjectEditor.
 * @return A specific user object.
 * <p>
 * Register the editor with an existing TreeItem in order to update or edit existing entries.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the object map.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the node to Object map.
 * @param node: The node that is to be altered.
 * @param map: The node to user object map.
 * <p>
 * This method brings up an editor that contains the data of an existing object that is already created.
 * @param userObject
 * <p>
 * Return the object after edits in this ObjectEditor.
 * @return A specific user object.
 * <p>
 * Register the editor with an existing TreeItem in order to update or edit existing entries.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the object map.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the node to Object map.
 * @param node: The node that is to be altered.
 * @param map: The node to user object map.
 * <p>
 * This method brings up an editor that contains the data of an existing object that is already created.
 * @param userObject
 * <p>
 * Return the object after edits in this ObjectEditor.
 * @return A specific user object.
 * <p>
 * Register the editor with an existing TreeItem in order to update or edit existing entries.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the object map.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the node to Object map.
 * @param node: The node that is to be altered.
 * @param map: The node to user object map.
 * <p>
 * This method brings up an editor that contains the data of an existing object that is already created.
 * @param userObject
 * <p>
 * Return the object after edits in this ObjectEditor.
 * @return A specific user object.
 * <p>
 * Register the editor with an existing TreeItem in order to update or edit existing entries.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the object map.
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 * <p>
 * Register the node to Object map.
 * @param node: The node that is to be altered.
 * @param map: The node to user object map.
 *//*

public interface ObjectEditor<T extends EditTreeItem> {
    */
/**
 * This method brings up an editor that contains the data of an existing object that is already created.
 *
 * @param userObject
 *//*

    void readObject(T userObject);

    */
/**
 * Return the object after edits in this ObjectEditor.
 *
 * @return A specific user object.
 *//*

    T getObject();

    */
/**
 * Register the editor with an existing TreeItem in order to update or edit existing entries.
 *
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 *//*

    void editTreeItem(TreeItem<String> treeItem, Map<String, EditTreeItem> map);

    */
/**
 * Register the object map.
 *
 * @param treeItem: An existing TreeItem.
 * @param map: The map from String name to Entity.
 *//*

    void addTreeItem(TreeItem<String> treeItem, Map<String, EditTreeItem> map);

    */
/**
 * Register the node to Object map.
 *
 * @param node: The node that is to be altered.
 * @param map: The node to user object map.
 *//*

    void editNode(Node node, Map<Node, EditTreeItem> map);
}
*/
