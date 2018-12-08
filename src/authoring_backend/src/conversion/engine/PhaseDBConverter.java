package conversion.engine;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import phase.api.PhaseDB;

public class PhaseDBConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        var db = (PhaseDB) o;
        var graphConverter = new PhaseGraphConverter();
        db.phases().forEach(graph -> graphConverter.marshal(graph, writer, ctx));
        var blockGraphConverter = new BlockGraphConverter();
        writer.startNode("end-handler");
        blockGraphConverter.marshal(db.endHandler(), writer, ctx);
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
        return reader.getValue();
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(PhaseDB.class);
    }
}

