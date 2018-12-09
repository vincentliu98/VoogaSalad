package utils.serializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import conversion.engine.GameObjectsCRUDConverter;
import gameObjects.crud.SimpleGameObjectsCRUD;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class tests the serialization ability of the front end authoring interface.
 *
 * @author Haotian Wang
 */
public class SerializerTestCRUD {
    private XStream xstream;

    public SerializerTestCRUD() {
        xstream = new XStream(new DomDriver());
        xstream.alias("CRUD", SimpleGameObjectsCRUD.class);
        xstream.registerConverter(new GameObjectsCRUDConverter(xstream.getMapper()));
    }

    /**
     * @return The Xstream object as the serializer.
     */
    public XStream getSerializer() {
        return xstream;
    }

    /**
     * This method returns the String representation of the object.
     *
     * @param object: An Object that is to be serialized to an XML String.
     * @return An XML String.
     */
    public String getXMLString(Object object) {
        return xstream.toXML(object);
    }

    /**
     * This method writes the content of the XML String to an output file.
     *
     * @param file: A File object where the XML String will be written.
     * @param object: An Object that is to be converted to an XML file.
     */
    public void writeToXMLFile(File file, Object object) {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(xstream.toXML(object));
            fw.flush();
            fw.close();
        } catch (IOException ignored) {}
    }
}
