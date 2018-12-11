package conversion.engine;

import authoring.AuthoringTools;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gameObjects.crud.SimpleGameObjectsCRUD;
import javafx.util.Pair;
import phase.api.PhaseDB;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class generates a serializer for the Game Engine.
 */
public class SerializerForEngine {
    private static final String RANDOM_STRING = "IHOPETHISISNTINSOMEBODYSCODE";
    public static final List<Pair<String, String>> mapping =
            List.of(
                    new Pair<>("&lt;", "<" + RANDOM_STRING),
                    new Pair<>("&gt;", ">" + RANDOM_STRING),
                    new Pair<>("&apos;", "\'" + RANDOM_STRING),
                    new Pair<>("&amp;", "&" + RANDOM_STRING),
                    new Pair<>("&quot;", "\"" + RANDOM_STRING)
            );

    public static XStream gen() {
        var serializer = new XStream(new DomDriver());
        serializer.alias("game", AuthoringTools.class);
        serializer.registerConverter(new AuthoringToolsConverter(genAux(), serializer.getMapper()));

        return serializer;
    }

    private static XStream genAux() {
        var serializer = new XStream(new DomDriver());
        serializer.alias("game-objects", SimpleGameObjectsCRUD.class);
        serializer.alias("phase-info", PhaseDB.class);
        serializer.alias("props", LinkedHashMap.class);

        serializer.registerConverter(new PhaseDBConverter());
        serializer.registerConverter(new PhaseGraphConverter());
        serializer.registerConverter(new BlockGraphConverter());
        serializer.registerConverter(
                new GameObjectsCRUDConverter(serializer.getMapper())
        );
        return serializer;
    }
}
