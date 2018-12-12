package conversion.authoring;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gameObjects.crud.SimpleGameObjectsCRUD;

import java.util.HashSet;

/**
 * This class tests the serialization ability of the front end authoring interface.
 *
 * @author Haotian Wang
 */
public class SerializerCRUD {
    private XStream xstream;

    public SerializerCRUD() {
        xstream = new XStream(new DomDriver());
        xstream.alias("CRUD", SimpleGameObjectsCRUD.class);
    }

    /**
     * @return The Xstream object as the serializer.
     */
    public XStream getSerializer() {
        return xstream;
    }

    /**
     * This method returns the XML representation of the crud interface.
     *
     * @return An XML String.
     */
    public String getXMLString(SimpleGameObjectsCRUD entityDB) {
        return xstream.toXML(
                new SavedEntityDB(
                        entityDB.getWidth(),
                        entityDB.getHeight(),
                        entityDB.getBGMpath(),
                        new HashSet<>(entityDB.getAllClasses()),
                        new HashSet<>(entityDB.getAllInstances())
                )
        );
    }
}
