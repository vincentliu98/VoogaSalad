package groovy.api;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import groovy.graph.BlockGraphImpl;

public class BlockGraphConverter implements Converter{
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        writer.setValue(((BlockGraph) o).transformToGroovy().getOrElse(""));
        // the failure should have been covered by the data storage
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
        return reader.getValue();
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(BlockGraphImpl.class);
    }
}
