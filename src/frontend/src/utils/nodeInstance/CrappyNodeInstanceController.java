package utils.nodeInstance;

import gameObjects.gameObject.GameObjectInstance;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.exception.GameObjectInstanceNotFoundException;
import utils.exception.NodeNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * This is a crappy implementation of NodeInstanceController interface. It will be used to manage the relationship between a Node and a GameObjectInstance.
 *
 * @author Haotian Wang
 */
public class CrappyNodeInstanceController implements NodeInstanceController {
    private Map<GameObjectInstance, Node> instanceNodeMap;
    private Map<Node, GameObjectInstance> nodeInstanceMap;
    private BiConsumer<MouseEvent, Node> mouseDoubleClickOpenEditor;

    public CrappyNodeInstanceController() {
        instanceNodeMap = new HashMap<>();
        nodeInstanceMap = new HashMap<>();
    }

    /**
     * Establishes a one-to-one correspondence between a JavaFx Node and a GameObjectInstance.
     *
     * @param node               : A JavaFx Node
     * @param gameObjectInstance : A GameObjectInstance.
     */
    @Override
    public void addLink(Node node, GameObjectInstance gameObjectInstance) {
        instanceNodeMap.put(gameObjectInstance, node);
        nodeInstanceMap.put(node, gameObjectInstance);
    }

    /**
     * This method changes the link between an old Node and a GameObjectInstance to the link between the same GameObjectInstance and a new Node. This method also switches out the Old Node in its parent if there is one.
     *
     * @param oldNode : The old Node whose link will be broken.
     * @param newNode : The new Node to which a link will be reattached.
     * @throws NodeNotFoundException
     */
    @Override
    public void changeNode(Node oldNode, Node newNode) throws NodeNotFoundException {
        if (!nodeInstanceMap.containsKey(oldNode)) {
            throw new NodeNotFoundException("The Node to be switched out is not defined in the NodeInstanceController");
        }
        Pane parent = (Pane) oldNode.getParent();
        if (parent != null) {
            parent.getChildren().add(newNode);
            parent.getChildren().remove(oldNode);
        }
        if (mouseDoubleClickOpenEditor != null) {
            newNode.setOnMouseClicked(e -> mouseDoubleClickOpenEditor.accept(e, newNode));
        }
        nodeInstanceMap.put(newNode, nodeInstanceMap.get(oldNode));
        nodeInstanceMap.remove(oldNode);
        instanceNodeMap.put(nodeInstanceMap.get(newNode), newNode);
    }

    /**
     * Get the corresponding GameObjectInstance for a JavaFx Node.
     *
     * @param node : The Node whose corresponding GameObjectInstance will be queried.
     * @return The corresponding GameObjectInstance.
     * @throws GameObjectInstanceNotFoundException
     */
    @Override
    public GameObjectInstance getGameObjectInstance(Node node) throws NodeNotFoundException {
        if (!nodeInstanceMap.containsKey(node)) {
            throw new NodeNotFoundException("The input Node is not defined in the NodeInstanceController");
        }
        return nodeInstanceMap.get(node);
    }

    /**
     * Get the corresponding Node for a GameObjectInstance.
     *
     * @param gameObjectInstance : The GameObjectInstance whose corresponding Node will be queried.
     * @return The corresponding Node.
     * @throws NodeNotFoundException
     */
    @Override
    public Node getNode(GameObjectInstance gameObjectInstance) throws GameObjectInstanceNotFoundException {
        if (!instanceNodeMap.containsKey(gameObjectInstance)) {
            throw new GameObjectInstanceNotFoundException("The input GameObjectInstance is not defined in the NodeInstanceController");
        }
        return instanceNodeMap.get(gameObjectInstance);
    }

    /**
     * This method clears all the relationships between all Nodes and GameObjectInstances.
     */
    @Override
    public void clearAllLinks() {
        instanceNodeMap.clear();
        nodeInstanceMap.clear();
    }

    /**
     * Set the private field biconsumer from an external place.
     *
     * @param biConsumer : A BiConsumer
     */
    @Override
    public void setBiConsumer(BiConsumer<MouseEvent, Node> biConsumer) {
        mouseDoubleClickOpenEditor = biConsumer;
    }
}
