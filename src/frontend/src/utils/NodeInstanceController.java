package utils;

import gameObjects.gameObject.GameObjectInstance;
import javafx.scene.Node;

/**
 * This interface controls the relationship between a Node on the editing panels and a GameObjectInstance it potentially refers to.
 *
 * @author Haotian Wang
 */
public interface NodeInstanceController {
    /**
     * Establishes a one-to-one correspondence between a JavaFx Node and a GameObjectInstance.
     *
     * @param node: A JavaFx Node
     * @param gameObjectInstance: A GameObjectInstance.
     */
    void addLink(Node node, GameObjectInstance gameObjectInstance);

    /**
     * This method changes the link between an old Node and a GameObjectInstance to the link between the same GameObjectInstance and a new Node. This method also switches out the Old Node in its parent if there is one.
     *
     * @param oldNode: The old Node whose link will be broken.
     * @param newNode: The new Node to which a link will be reattached.
     */
    void changeNode(Node oldNode, Node newNode);

    /**
     * Get the corresponding GameObjectInstance for a JavaFx Node.
     *
     * @param node: The Node whose corresponding GameObjectInstance will be queried.
     */
    void getGameObjectInstance(Node node);

    /**
     * Get the corresponding Node for a GameObjectInstance.
     *
     * @param gameObjectInstance: The GameObjectInstance whose corresponding Node will be queried.
     */
    void getNode(GameObjectInstance gameObjectInstance);
}
