package conversion.engine;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import phase.PhaseGraphImpl;
import phase.api.GameEvent;

import java.util.Collection;
import java.util.stream.Collectors;

public class PhaseGraphConverter implements Converter {
    /**
     * We convert all node names to hashCode, and we replace goTo('A') to goTo('A'.hashCode());
     * It's a dirty hack that might fail if hashCodes collide but ... it's not likely
     */
    @Override
    public void marshal(Object o, HierarchicalStreamWriter writer, MarshallingContext ctx) {
        var graph = (PhaseGraphImpl) o;
        var nodes = graph.keySet();
        var edges = graph.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        for (var node : nodes) {
            writer.startNode("gameplay.Node");

            writer.startNode("myPhaseName");
            writer.setValue(graph.name());
            writer.endNode();

            writer.startNode("myName");
            writer.setValue(node.name());
            writer.endNode();

            writer.startNode("myExecution");
            writer.setValue(node.exec().transformToGroovy().get(""));
            System.out.println("toGroovyRes" + node.exec().transformToGroovy());
            System.out.println("marshalling" + node.exec().hashCode());
            writer.endNode();

            writer.endNode();
        }

        for (var edge : edges) {
            writer.startNode("gameplay.Edge");

            writer.startNode("myPhaseName");
            writer.setValue(graph.name());
            writer.endNode();

            writer.startNode("myStartNodeName");
            writer.setValue(edge.from().name());
            writer.endNode();

            writer.startNode("myEndNodeName");
            writer.setValue(edge.to().name());
            writer.endNode();

            writer.startNode("myTrigger");
            writer.addAttribute("class", edge.trigger().getClass().getName());
            if (edge.trigger() instanceof GameEvent.KeyPress) {
                writer.startNode("code");
                writer.setValue(((GameEvent.KeyPress) edge.trigger()).getCode().name());
                writer.endNode();
            }
            writer.endNode();

            writer.startNode("myGuard");
            writer.setValue(edge.guard().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        }

        writer.startNode("gameplay.Phase");

        writer.startNode("myStartNodeName");
        writer.setValue(graph.source().name());
        writer.endNode();


        writer.startNode("myCurrentNodeName");
        writer.setValue(graph.source().name());
        writer.endNode();

        writer.startNode("myNodeNames");
        for (var node : nodes) {
            writer.startNode("string");
            writer.setValue(node.name());
            writer.endNode();
        }
        writer.endNode();

        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext ctx) {
        //TODO: ugh
        return null;
    }

    @Override
    public boolean canConvert(Class aClass) {
        return aClass.equals(PhaseGraphImpl.class);
    }
}

