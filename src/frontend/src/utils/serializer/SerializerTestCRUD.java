package utils.serializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import conversion.engine.GameObjectsCRUDConverter;
import gameObjects.crud.SimpleGameObjectsCRUD;

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
}
