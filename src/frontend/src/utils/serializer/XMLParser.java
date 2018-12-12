package utils.serializer;

import grids.Point;
import grids.PointImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.exception.XMLParsingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Parser reads in an XML file and reconstructs the GameObjectClasses and GameObjectInstances in the authoring engine.
 *
 * @author Haotian Wang
 */
public class XMLParser {
    private static final Set<String> allClasses = Set.of("entityClass", "categoryClass", "soundClass", "playerClass", "tileClass");
    private static final Set<String> allInstances = Set.of("entityInstance", "soundInstance", "tileInstance");
    private Set<RawClass> classesFromXML;
    private Set<RawInstance> instancesFromXML;
    private Point dimension;

    /**
     * This method reads in the XML file and saves the information in the class.
     *
     * @param file: A File in the desired XML format.
     */
    public void loadXML(File file) throws SAXException, XMLParsingException, CRUDLoadException {
        DocumentBuilder docBuilder = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ignored) {
        }
        Document myDocument;
        try {
            assert docBuilder != null;
            myDocument = docBuilder.parse(file);
        } catch (IOException e) {
            throw new XMLParsingException("The XML Parser encountered an error upon reading" + file.getAbsolutePath(), e);
        }
        myDocument.getDocumentElement().normalize();
        Element root = myDocument.getDocumentElement();
        dimension = new PointImpl(
                Integer.parseInt(root.getElementsByTagName("gridWidth").item(0).getTextContent()),
                Integer.parseInt(root.getElementsByTagName("gridHeight").item(0).getTextContent()));
        classesFromXML = new TreeSet<>();
        instancesFromXML = new TreeSet<>();
        for (String classType : allClasses) {
            NodeList classesOfType = root.getElementsByTagName(classType);
            if (classesOfType.getLength() != 0) {
                for (int i = 0; i < classesOfType.getLength(); i++) {
                    classesFromXML.add(new RawClass((Element) classesOfType.item(i)));
                }
            }
        }
        for (String instanceType : allInstances) {
            NodeList instancesOfType = root.getElementsByTagName(instanceType);
            if (instancesOfType.getLength() != 0) {
                for (int i = 0; i < instancesOfType.getLength(); i++) {
                    instancesFromXML.add(new RawInstance((Element) instancesOfType.item(i)));
                }
            }
        }
    }

    /**
     * This method returns the width of the grid.
     *
     * @return An int representing width.
     */
    public int getGridWidth() {
        return dimension.getX();
    }

    /**
     * This method returns the height of the grid.
     *
     * @return An int representing height.
     */
    public int getGridHeight() {
        return dimension.getY();
    }

    /**
     * This method returns the dimension of the Grid specified by the XML.
     *
     * @return A Point representing height and width of the grid.
     */
    public Point getGridDimension() {
        return dimension;
    }

    /**
     * This method returns an iterable of GameObjectClasses in the most raw String format from the document.
     *
     * @return An Iterable<String>
     */
    public Iterable<RawClass> getGameObjectClasses() {
        return classesFromXML;
    }

    /**
     * This method returns an iterable of GameObjectInstances in the most raw String format from the document.
     *
     * @return An Iterable<String>.
     */
    public Iterable<RawInstance> getGameObjectInstances() {
        return instancesFromXML;
    }
}
