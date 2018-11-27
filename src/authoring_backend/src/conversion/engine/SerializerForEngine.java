package conversion.engine;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gameObjects.SimpleGameObjectsCRUD;

import java.util.LinkedHashMap;

/**
 *  This class generates a serializer for the Game Engine.
 */
public class SerializerForEngine {
    public static XStream gen() {
        var serializer = new XStream(new DomDriver());
        serializer.alias("game-objects", SimpleGameObjectsCRUD.class);
        serializer.alias("props", LinkedHashMap.class);
        serializer.registerConverter(new PhaseDBConverter());
        serializer.registerConverter(new PhaseGraphConverter());
        serializer.registerConverter(new BlockGraphConverter());
        serializer.registerConverter(
            new GameObjectsCRUDConverter(serializer.getMapper(), serializer.getReflectionProvider())
        );
        return serializer;
    }
}
