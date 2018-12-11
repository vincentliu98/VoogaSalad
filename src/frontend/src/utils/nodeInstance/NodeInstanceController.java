package utils.nodeInstance;

import authoringUtils.exception.GameObjectInstanceNotFoundException;
import gameObjects.gameObject.GameObjectInstance;
import javafx.scene.Node;
import utils.exception.NodeNotFoundException;

/**
 * This interface controls the relationship between a Node on the editing panels and a GameObjectInstance it potentially refers to.
 *
 * @author Haotian Wang
 */
public interface NodeInstanceController {
    /**
     * Establishes a one-to-one correspondence between a JavaFx Node and a GameObjectInstance.
     *
     * @param node:               A JavaFx Node
     * @param gameObjectInstance: A GameObjectInstance.
     */
    void addLink(Node node, GameObjectInstance gameObjectInstance);

    /**
     * This method changes the link between an old Node and a GameObjectInstance to the link between the same GameObjectInstance and a new Node. This method also switches out the Old Node in its parent if there is one.
     *
     * @param oldNode: The old Node whose link will be broken.
     * @param newNode: The new Node to which a link will be reattached.
     * @throws NodeNotFoundException
     */
    void changeNode(Node oldNode, Node newNode) throws NodeNotFoundException;

    /**
     * Get the corresponding GameObjectInstance for a JavaFx Node.
     *
     * @param node: The Node whose corresponding GameObjectInstance will be queried.
     * @return The corresponding GameObjectInstance.
     * @throws NodeNotFoundException
     */
    <T extends GameObjectInstance> T getGameObjectInstance(Node node) throws NodeNotFoundException;


    /**
     * Get the corresponding Node for a GameObjectInstance.
     *
     * @param gameObjectInstance: The GameObjectInstance whose corresponding Node will be queried.
     * @return The corresponding Node.
     * @throws GameObjectInstanceNotFoundException
     */
    Node getNode(GameObjectInstance gameObjectInstance) throws GameObjectInstanceNotFoundException;

    /**
     * This method clears all the relationships between all Nodes and GameObjectInstances.
     */
    void clearAllLinks();

    /**
     * This method removes the GameObjectInstance and the associated Node from the controller.
     *
     * @param gameObjectInstance: A GameObjectInstance who and its Node will be removed.
     * @throws GameObjectInstanceNotFoundException
     */
    void removeGameObjectInstance(GameObjectInstance gameObjectInstance) throws GameObjectInstanceNotFoundException;

    /**
     * This method removes the Node and its associated GameObjectInstance from the controller.
     *
     * @param node: A Node who and its GameObjectInstance will be removed.
     * @throws NodeNotFoundException
     */
    void removeNode(Node node) throws NodeNotFoundException;
}
