package conversion.engine;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import phase.PhaseGraphImpl;

import java.util.Collection;
import java.util.HashMap;
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

        var graphID = String.valueOf(Math.abs(graph.hashCode()));
        var nodeIDs = new HashMap<String, String>();
        nodes.forEach(n -> nodeIDs.put(n.name(), String.valueOf(nodeIDs.size())));

        for(var node : nodes) {
            writer.startNode("gameplay.Node");

            writer.startNode("myPhaseID");
            writer.setValue(graphID);
            writer.endNode();

            writer.startNode("myID");
            writer.setValue(nodeIDs.get(node.name()));
            writer.endNode();

            writer.startNode("myExecution");
            writer.setValue(node.exec().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        }

        for(var edge : edges) {
            writer.startNode("gameplay.Edge");

            writer.startNode("myPhaseID");
            writer.setValue(graphID);
            writer.endNode();

            writer.startNode("myStartNodeID");
            writer.setValue(nodeIDs.get(edge.from().name()));
            writer.endNode();

            writer.startNode("myEndNodeID");
            writer.setValue(nodeIDs.get(edge.to().name()));
            writer.endNode();

            writer.startNode("myTrigger");
            writer.addAttribute("class", edge.trigger().getClass().getName());
            writer.endNode();

            writer.startNode("myGuard");
            writer.setValue(edge.guard().transformToGroovy().get(""));
            writer.endNode();

            writer.endNode();
        }

        writer.startNode("gameplay.Phase");

        writer.startNode("myPhaseID");
        writer.setValue(graphID);
        writer.endNode();

        writer.startNode("myStartNodeID");
        writer.setValue(nodeIDs.get(graph.source().name()));
        writer.endNode();


        writer.startNode("myCurrentNodeID");
        writer.setValue(nodeIDs.get(graph.source().name()));
        writer.endNode();

        writer.startNode("myNodeIDs");
        for(var node: nodes) {
            writer.startNode("int");
            writer.setValue(nodeIDs.get(node.name()));
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

