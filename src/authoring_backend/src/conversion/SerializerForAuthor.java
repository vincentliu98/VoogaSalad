package conversion;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * The SerializerForAuthor, when generated, only has backend Converters registered. However,
 * the frontend can add more Converters as much as they need to!
 */
public class SerializerForAuthor {
    public static XStream gen() {
        return new XStream(new DomDriver());
    }
}
