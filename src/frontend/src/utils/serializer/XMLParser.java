package utils.serializer;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * The Parser reads in an XML file and reconstructs the GameObjectClasses and GameObjectInstances in the authoring engine.
 *
 * @author Haotian Wang
 */
public class XMLParser {
    private Document myDocument;

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
}
