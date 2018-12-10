package utils.serializer;

import grids.Point;
import grids.PointImpl;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import utils.exception.XMLParsingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The Parser reads in an XML file and reconstructs the GameObjectClasses and GameObjectInstances in the authoring engine.
 *
 * @author Haotian Wang
 */
public class XMLParser {
    private Document myDocument;
    private static final Set<String> allClasses = Set.of("entityClass", "categoryClass", "soundClass", "playerClass", "tileClass");
    private static final Set<String> allInstances = Set.of("entityInstance", "soundInstance", "tileInstance");

    /**
     * This method reads in the XML file and saves the information in the class.
     *
     * @param file: A File in the desired XML format.
     */
    public void loadXML(File file) throws SAXException, XMLParsingException {
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException ignored) {
        }
        try {
            myDocument = docBuilder.parse(file);
        } catch (IOException e) {
            throw new XMLParsingException("The XML Parser encountered an error upon reading" + file.getAbsolutePath(), e);
        }
        myDocument.getDocumentElement().normalize();
    }

    /**
     * This method returns the dimension of the Grid specified by the XML.
     *
     * @return A Point representing height and width of the grid.
     */
    public Point getGridDimension() {
        return new PointImpl(
                Integer.parseInt(myDocument.getElementsByTagName("grid-width").item(0).getNodeValue()),
                Integer.parseInt(myDocument.getElementsByTagName("grid-height").item(0).getNodeValue()));
    }

    /**
     * This method returns an iterable of GameObjectClasses in the most raw String format from the document.
     *
     * @return An Iterable<String>
     */
    public Iterable<RawClass> getGameObjectClasses() {
        return null;
    }
}
