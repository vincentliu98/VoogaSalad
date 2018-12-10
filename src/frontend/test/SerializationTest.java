import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import utils.exception.XMLParsingException;
import utils.serializer.SerializerTestCRUD;
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
        File file = new File("starter.xml");
        try {
            parser.loadXML(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (XMLParsingException e) {
            e.printStackTrace();
        }
    }
}
