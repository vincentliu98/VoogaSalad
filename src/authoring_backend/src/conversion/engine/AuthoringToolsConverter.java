package conversion.engine;

import authoring.AuthoringTools;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import gameObjects.crud.SimpleGameObjectsCRUD;
import gameObjects.gameObject.GameObjectClass;
import phase.api.PhaseDB;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AuthoringToolsConverter implements Converter {
    private Mapper mapper;
    private XStream componentSerializer;

    AuthoringToolsConverter(XStream componentSerializer, Mapper mapper) {
        this.componentSerializer = componentSerializer;
        this.mapper = mapper;
    }

    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        var authTools = (AuthoringTools) o;
        var converters = componentSerializer.getConverterLookup();

        converters.lookupConverterForType(PhaseDB.class).marshal(authTools.phaseDB(), writer, ctx);
        converters.lookupConverterForType(SimpleGameObjectsCRUD.class).marshal(authTools.entityDB(), writer, ctx);

        writer.startNode("gameplay.Turn");

        writer.startNode("myCurrentPhaseName");
        writer.setValue(authTools.phaseDB().getStartingPhase());
        writer.endNode();

        writer.startNode("playersOrder");
        var playerNames = StreamSupport.stream(authTools.entityDB().getPlayerClasses().spliterator(), true)
                .map(GameObjectClass::getClassName)
                .collect(Collectors.toList());
        new CollectionConverter(mapper).marshal(playerNames, writer, ctx);

        writer.endNode();

        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(AuthoringTools.class);
    }
}
