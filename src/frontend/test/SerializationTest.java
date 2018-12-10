import org.xml.sax.SAXException;
import utils.exception.XMLParsingException;
import utils.serializer.CRUDLoadException;
import utils.serializer.XMLParser;

import java.io.File;

/**
 * This class tests the serialization of CRUD interface.
 *
 * @author Haotian Wang
 */
public class SerializationTest {
    public static void main(String[] args) {
        XMLParser parser = new XMLParser();
        String fileName = "test.xml";
        ClassLoader classLoader = SerializationTest.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getPath());
        System.out.println(file.getAbsolutePath());
        try {
            parser.loadXML(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XMLParsingException e) {
            e.printStackTrace();
        } catch (CRUDLoadException e) {
            e.printStackTrace();
        }
        System.out.println(parser.getGridDimension());
        System.out.println(parser.getGameObjectClasses());
        System.out.println(parser.getGameObjectInstances());
        parser.getGameObjectInstances().forEach(rawInstance -> System.out.println(rawInstance.getInstanceName() + "," + rawInstance.getxCoord() + "," + rawInstance.getyCoord()));
    }
}
