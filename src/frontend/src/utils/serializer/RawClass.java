package utils.serializer;

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
        Element classIDElement = getChildElement("classID");
        if (!hasValue(classIDElement)) {
            throw new CRUDLoadException(type + " does not have a valid classID");
        }
        classID = Integer.parseInt(classIDElement.getTextContent());
        Element classNameElement = getChildElement("className");
        if (!hasValue(classNameElement)) {
            throw new CRUDLoadException(type + " does not have a valid class name");
        }
        className = classNameElement.getTextContent();
        if (containsChildElement("height")) {
            Element heightElement = getChildElement("height");
            if (hasValue(heightElement)) {
                height = Integer.parseInt(heightElement.getTextContent());
                if (height <= 0) {
                    throw new CRUDLoadException("Height must be a positive integer");
                }
            }
        }
        if (containsChildElement("width")) {
            Element widthElement = getChildElement("width");
            if (hasValue(widthElement)) {
                width = Integer.parseInt(widthElement.getTextContent());
                if (width <= 0) {
                    throw new CRUDLoadException("Width must be a positive integer");
                }
            }
        }
        if (containsChildElement("props")) {
            Element propNode = getChildElement("props");
            if (hasChild(propNode)) {
                props = new HashMap<>();
                for (int i = 0; i < propNode.getChildNodes().getLength(); i++) {
                    Node current = propNode.getChildNodes().item(i);
                    String key = current.getChildNodes().item(0).getTextContent();
                    String value = current.getChildNodes().item(1).getTextContent();
                    props.put(key, value);
                }
            }
        }
        if (containsChildElement("imagePaths")) {
            Element imagePathsNode = getChildElement("imagePaths");
            if (hasValue(imagePathsNode)) {
                imagePaths = new ArrayList<>();
                for (int i = 0; i < imagePathsNode.getChildNodes().getLength(); i++) {
                    imagePaths.add(imagePathsNode.getChildNodes().item(i).getTextContent());
                }
            }
        }
        if (containsChildElement("imagePath")) {
            Element imagePathElement = getChildElement("imagePath");
            if (hasValue(imagePathElement)) {
                imagePath = imagePathElement.getTextContent();
            }
        }
        if (containsChildElement("imageSelector")) {
            Element imageSelectorElement = getChildElement("imageSelector");
            if (hasValue(imageSelectorElement)) {
                imageSelector = imageSelectorElement.getTextContent();
            }
        }
        if (containsChildElement("mediaFilePath")) {
            Element mediaFilePathElement = getChildElement("mediaFilePath");
            if (hasValue(mediaFilePathElement)) {
                mediaFilePath = mediaFilePathElement.getTextContent();
            }
        }
        if (containsChildElement("gameObjectInstanceIDs")) {
            Element gameObjectInstanceIDsElement = getChildElement("gameObjectInstanceIDs");
            if (hasChild(gameObjectInstanceIDsElement)) {
                gameObjectInstanceIDs = new HashSet<>();
                for (int i = 0; i < gameObjectInstanceIDsElement.getChildNodes().getLength(); i++) {
                    gameObjectInstanceIDs.add(Integer.parseInt(gameObjectInstanceIDsElement.getChildNodes().item(i).getTextContent()));
                }
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
            throw new CRUDLoadException(type + " does not have " + name);
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
