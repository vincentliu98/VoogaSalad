package test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import groovy.api.BlockGraphConverter;
import groovy.api.GroovyFactory;
import groovy.graph.BlockGraphImpl;
import javafx.scene.input.KeyCode;
import phase.PhaseGraphImpl;
import phase.TransitionImpl;
import phase.api.GameEvent;
import phase.api.Phase;
import phase.api.PhaseGraphConverter;
import phase.api.PhaseManager;

import static groovy.api.Ports.*;

public class PhaseGraphTest {
    public static void main(String args[]) throws Throwable {
        // set up xstream
        var xstream = new XStream(new DomDriver());
        xstream.registerConverter(new BlockGraphConverter());
        xstream.registerConverter(new PhaseGraphConverter());
        xstream.alias("groovy", BlockGraphImpl.class);
        xstream.alias("phase-graph", PhaseGraphImpl.class);
        var manager = new PhaseManager();

        var phaseGraph = manager.empty("A").get();

        var a = new Phase();
        var b = new Phase();
        var c = new Phase();

        phaseGraph.addNode(a);
        phaseGraph.addNode(b);
        phaseGraph.addNode(c);

        var e = new TransitionImpl(a, GameEvent.mouseClick(), b);
        phaseGraph.addEdge(e);
        phaseGraph.addEdge(new TransitionImpl(b, GameEvent.mouseDrag(), c));
        phaseGraph.addEdge(new TransitionImpl(b, GameEvent.keyPress(KeyCode.ESCAPE), a));

        var graph = e.exec();
        // sum from 1 to 9
        var s = GroovyFactory.refBlock("sum").get();
        var init = GroovyFactory.assignBlock();
        var zero2 = GroovyFactory.integerBlock("0").get();
        graph.addNode(s);
        graph.addNode(init);
        graph.addNode(zero2);
        graph.addEdge(GroovyFactory.makeEdge(init, ASSIGN_LHS, s));
        graph.addEdge(GroovyFactory.makeEdge(init, ASSIGN_RHS, zero2));
        graph.addEdge(GroovyFactory.makeEdge(graph.source(), FLOW_OUT, init));

        var range = GroovyFactory.range(1, 10).get();
        var foreach = GroovyFactory.forEachBlock("i");
        graph.addNode(range);
        graph.addNode(foreach);
        graph.addEdge(GroovyFactory.makeEdge(foreach, FOREACH_LIST, range));
        graph.addEdge(GroovyFactory.makeEdge(init, FLOW_OUT, foreach));

        var ass = GroovyFactory.assignBlock();
        var add = GroovyFactory.add();
        var i = GroovyFactory.refBlock("i").get();
        graph.addNode(ass);
        graph.addNode(add);
        graph.addNode(i);
        graph.addEdge(GroovyFactory.makeEdge(foreach, FOREACH_BODY, ass));
        graph.addEdge(GroovyFactory.makeEdge(ass, ASSIGN_LHS, s));
        graph.addEdge(GroovyFactory.makeEdge(ass, ASSIGN_RHS, add));
        graph.addEdge(GroovyFactory.makeEdge(add, A, s));
        graph.addEdge(GroovyFactory.makeEdge(add, B, i));

        System.out.println(xstream.toXML(phaseGraph));
    }
}
