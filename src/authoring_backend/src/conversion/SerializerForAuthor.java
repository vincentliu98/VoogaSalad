package conversion;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import groovy.api.BlockGraph;
import groovy.graph.BlockGraphImpl;
import phase.PhaseGraphImpl;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 *  The SerializerForAuthor, when generated, only has backend Converters registered. However,
 *  the frontend can add more Converters as much as they need to!
 */
public class SerializerForAuthor {
    public static XStream gen() { return new XStream(new DomDriver()); }
}
