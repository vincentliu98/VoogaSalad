package phase.api;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import phase.PhaseGraphImpl;

import java.util.Collection;
import java.util.stream.Collectors;

public class PhaseGraphConverter implements Converter {
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        var graph = (PhaseGraphImpl) o;
        var nodes = graph.keySet();
        var edges = graph.values()
                         .stream()
                         .flatMap(Collection::stream)
                         .collect(Collectors.toSet());

        writer.startNode("phases");
        nodes.forEach(n -> {
            writer.startNode("phase");
            writer.setValue(n.id().toString());
            writer.endNode();
        });
        writer.endNode();

        writer.startNode("transitions");
        edges.forEach(e -> {
            writer.startNode("transition");

            writer.startNode("from");
            writer.setValue(e.from().id().toString());
            writer.endNode();

            writer.startNode("to");
            writer.setValue(e.to().id().toString());
            writer.endNode();

            writer.startNode("trigger");
            writer.setValue(e.trigger().eventType());
            writer.endNode();

            writer.startNode("guard");
            // the failure should have been covered by this time
            writer.setValue(e.guard().transformToGroovy().get(""));
            writer.endNode();

            writer.startNode("exec");
            // the failure should have been covered by this time
            writer.setValue(e.exec().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        });
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) { return aClass.equals(PhaseGraphImpl.class); }
}
