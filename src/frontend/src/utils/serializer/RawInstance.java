package utils.serializer;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.*;

/**
 * This is a class for representing the raw GameObjectInstance parsed from XML.
 *
 * @author Haotian Wang
 */
public class RawInstance implements Comparable<RawInstance> {
    private int instanceID;
    private String type;
    private String className;
    private int height;
    private int width;
    private Map<String, String> props;
    private List<String> imagePaths;
    private String imagePath;
    private String mediaFilePath;
    private String imageSelector;
    private Set<Integer> gameObjectInstanceIDs;
    private String instanceName;

    public RawInstance(Element entry) {
        type = entry.getTagName();
        instanceID = Integer.parseInt(entry.getElementsByTagName("instanceID").item(0).getNodeValue());
        className = entry.getElementsByTagName("className").item(0).getNodeValue();
        instanceName = entry.getElementsByTagName("instanceName").item(0).getNodeName();
        if (entry.getElementsByTagName("height").item(0).getNodeValue() != null) {
            height = Integer.parseInt(entry.getElementsByTagName("height").item(0).getNodeValue());
        }
        if (entry.getElementsByTagName("width").item(0).getNodeValue() != null) {
            width = Integer.parseInt(entry.getElementsByTagName("width").item(0).getNodeValue());
        }
        if (entry.getElementsByTagName("props").item(0).getChildNodes() != null) {
            props = new HashMap<>();
            Node propNode = entry.getElementsByTagName("props").item(0);
            for (int i = 0; i < propNode.getChildNodes().getLength(); i++) {
                Node current = propNode.getChildNodes().item(i);
                String key = current.getChildNodes().item(0).getNodeValue();
                String value = current.getChildNodes().item(1).getNodeValue();
                props.put(key, value);
            }
        }
        if (entry.getElementsByTagName("imagePaths").item(0).getChildNodes() != null) {
            imagePaths = new ArrayList<>();
            for (int i = 0; i < entry.getElementsByTagName("imagePaths").item(0).getChildNodes().getLength(); i++) {
                imagePaths.add(entry.getElementsByTagName("imagePaths").item(0).getChildNodes().item(i).getNodeValue());
            }
        }
        if (entry.getElementsByTagName("imagePath").item(0).getNodeValue() != null) {
            imagePath = entry.getElementsByTagName("imagePath").item(0).getNodeValue();
        }
        if (entry.getElementsByTagName("imageSelector").item(0).getNodeValue() != null) {
            imageSelector = entry.getElementsByTagName("imageSelector").item(0).getNodeValue();
        }
        if (entry.getElementsByTagName("mediaFilePath").item(0).getNodeValue() != null) {
            mediaFilePath = entry.getElementsByTagName("mediaFilePath").item(0).getNodeValue();
        }
        if (entry.getElementsByTagName("gameObjectInstanceIDs").item(0).getChildNodes() != null) {
            gameObjectInstanceIDs = new HashSet<>();
            for (int i = 0; i < entry.getElementsByTagName("gameObjectInstanceIDs").item(0).getChildNodes().getLength(); i++) {
                gameObjectInstanceIDs.add(Integer.parseInt(entry.getElementsByTagName("gameObjectInstanceIDs").item(0).getChildNodes().item(i).getNodeValue()));
            }
        }
    }

    public String getClassName() {
        return className;
    }

    public String getType() {
        return type;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageSelector() {
        return imageSelector;
    }

    public String getMediaFilePath() {
        return mediaFilePath;
    }

    public List<String> getImagePaths() {
        return imagePaths;
    }

    public Set<Integer> getGameObjectInstanceIDs() {
        return gameObjectInstanceIDs;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public int getInstanceID() {
        return instanceID;
    }

    @Override
    public int hashCode() {
        return instanceID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RawInstance)) {
            return false;
        }
        return instanceID == ((RawInstance) o).getInstanceID();
    }

    @Override
    public int compareTo(RawInstance o) {
        return instanceID - o.getInstanceID();
    }

    @Override
    public String toString() {
        return instanceName + "," + instanceID;
    }
}
