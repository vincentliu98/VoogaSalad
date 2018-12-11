import authoring.AuthoringTools;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import groovy.api.BlockGraph;
import groovy.api.Ports;

public class XStreamTest {
    public static void main(String[] args) throws Throwable {
        XStream xstream = new XStream(new DomDriver());

        var authTools = new AuthoringTools(1, 1);
        var groovyFactory = authTools.factory();
        var graph = groovyFactory.createEmptyGraph();

        var source = graph.source();
        var doBlock = groovyFactory.functionBlock(0, 0, "doSomething", null);
        graph.addNode(doBlock);
        graph.addEdge(groovyFactory.createEdge(source, Ports.FLOW_OUT, doBlock));
        var trueBlock = groovyFactory.booleanBlock(0, 0, "true").get();
        graph.addNode(trueBlock);
        graph.addEdge(groovyFactory.createEdge(doBlock, Ports.A, trueBlock));
        var ifBlock = groovyFactory.ifBlock(0, 0);
        graph.addNode(ifBlock);
        graph.addEdge(groovyFactory.createEdge(doBlock, Ports.FLOW_OUT, ifBlock));
        graph.addEdge(groovyFactory.createEdge(ifBlock, Ports.IF_PREDICATE, trueBlock));

        System.out.println("--------------------BEFORE SERIALIZATION----------------");
        System.out.println(graph.transformToGroovy().get());

        var newGraph = (BlockGraph) xstream.fromXML(xstream.toXML(graph));

        System.out.println("--------------------AFTER  SERIALIZATION----------------");
        System.out.println(newGraph.transformToGroovy().get());


        var phaseDB = authTools.phaseDB();
        System.out.println(phaseDB.toXML());
    }
}
