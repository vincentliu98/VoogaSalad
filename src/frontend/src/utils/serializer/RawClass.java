package utils.serializer;

import gameObjects.entity.EntityInstance;
import javafx.fxml.LoadException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * This class is a holder class that represents the information of a GameObjectClass in the XML.
 *
 * @author Haotian Wang
 */
public class RawClass implements Comparable<RawClass> {
    private Element rootElement;
    private int classID;
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

    public RawClass(Element entry) throws CRUDLoadException {
        rootElement = entry;
        type = entry.getTagName();
        classID = Integer.parseInt(getChildElement("classID").getTextContent());
        className = getChildElement("className").getTextContent();
        if (containsChildElement("height")) {
            height = Integer.parseInt(getChildElement("height").getTextContent());
            if (height <= 0) {
                throw new CRUDLoadException("Height must be a positive integer");
            }
        }
        if (containsChildElement("width")) {
            height = Integer.parseInt(getChildElement("width").getTextContent());
            if (height <= 0) {
                throw new CRUDLoadException("Width must be a positive integer");
            }
        }
        if (containsChildElement("props")) {
            props = new HashMap<>();
            Element propNode = getChildElement("props");
            for (int i = 0; i < propNode.getChildNodes().getLength(); i++) {
                Node current = propNode.getChildNodes().item(i);
                String key = current.getChildNodes().item(0).getTextContent();
                String value = current.getChildNodes().item(1).getTextContent();
                props.put(key, value);
            }
        }
        if (containsChildElement("imagePaths")) {
            Element imagePathsNode = getChildElement("imagePaths");
            imagePaths = new ArrayList<>();
            for (int i = 0; i < imagePathsNode.getChildNodes().getLength(); i++) {
                imagePaths.add(imagePathsNode.getChildNodes().item(i).getTextContent());
            }
        }
        if (containsChildElement("imagePath")) {
            imagePath = getChildElement("imagePath").getTextContent();
        }
        if (containsChildElement("imageSelector")) {
            imageSelector = entry.getElementsByTagName("imageSelector").item(0).getTextContent();
        }
        if (entry.getElementsByTagName("mediaFilePath").item(0).getTextContent() != null) {
            mediaFilePath = entry.getElementsByTagName("mediaFilePath").item(0).getTextContent();
        }
        if (entry.getElementsByTagName("gameObjectInstanceIDs").item(0).getChildNodes() != null) {
            gameObjectInstanceIDs = new HashSet<>();
            for (int i = 0; i < entry.getElementsByTagName("gameObjectInstanceIDs").item(0).getChildNodes().getLength(); i++) {
                gameObjectInstanceIDs.add(Integer.parseInt(entry.getElementsByTagName("gameObjectInstanceIDs").item(0).getChildNodes().item(i).getTextContent()));
            }
        }
    }

    private boolean containsChildElement(String name) {
        return rootElement.getElementsByTagName(name).getLength() != 0;
    }

    private boolean hasValue(Element element) {
        return element.getTextContent() != null && !element.getTextContent().isEmpty();
    }

    private boolean hasChild(Element element) {
        return element.getChildNodes().getLength() != 0;
    }

    private Element getChildElement(String name) throws CRUDLoadException {
        NodeList candidates = rootElement.getElementsByTagName(name);
        if (candidates.getLength() == 0) {
            throw new CRUDLoadException("GameObjectClass(es) do not have " + name);
        }
        return (Element) candidates.item(0);
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

    public int getClassID() {
        return classID;
    }

    @Override
    public int hashCode() {
        return classID;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RawClass)) {
            return false;
        }
        return classID == ((RawClass) o).getClassID();
    }

    @Override
    public int compareTo(RawClass o) {
        return classID - o.getClassID();
    }

    @Override
    public String toString() {
        return className + "," + classID;
    }
}
