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
        var graphConverter = new PhaseGraphConverter();
        ((PhaseDB) o).phases().forEach(graph -> graphConverter.marshal(graph, writer, ctx));
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

