package authoring;

import com.thoughtworks.xstream.XStream;
import conversion.SerializerForAuthor;
import conversion.engine.SerializerForEngine;

/**
 * These serializers generate XStream instances that are initialized with
 * appropriate Converters/Aliases
 */
public class Serializers {
    public static XStream forAuthor() {
        return SerializerForAuthor.gen();
    }

    public static XStream forEngine() {
        return SerializerForEngine.gen();
    }
}
