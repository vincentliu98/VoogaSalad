package test;

import authoring.AuthoringTools;
import entities.EntitiesCRUDInterface;
import entities.SimpleEntitiesCRUD;
import entities.TileClass;
import grids.Point;
import grids.PointImpl;
import javafx.scene.input.KeyCode;
import phase.api.GameEvent;
import phase.api.Phase;

import static groovy.api.Ports.*;

public class EntityClassTest {
    public static void main (String args[]) throws Throwable {
        var tools = new AuthoringTools();
        var phaseDB = tools.phaseDB();
        var factory = tools.factory();
        var eci = tools.entityDB();

        var phaseGraph = phaseDB.createGraph("A").get();

        var a = phaseDB.createPhase();
        var b = phaseDB.createPhase();
        var c = phaseDB.createPhase();

        phaseGraph.addNode(a);
        phaseGraph.addNode(b);
        phaseGraph.addNode(c);

        var e = phaseDB.createTransition(a, GameEvent.mouseClick(), b);
        phaseGraph.addEdge(e);
        phaseGraph.addEdge(phaseDB.createTransition(b, GameEvent.mouseDrag(), c));
        phaseGraph.addEdge(phaseDB.createTransition(b, GameEvent.keyPress(KeyCode.ESCAPE), a));

        var graph = b.exec();
        // sum from 1 to 9
        var s = factory.refBlock("sum").get();
        var init = factory.assignBlock();
        var zero2 = factory.integerBlock("0").get();
        graph.addNode(s);
        graph.addNode(init);
        graph.addNode(zero2);
        graph.addEdge(factory.createEdge(init, ASSIGN_LHS, s));
        graph.addEdge(factory.createEdge(init, ASSIGN_RHS, zero2));
        graph.addEdge(factory.createEdge(graph.source(), FLOW_OUT, init));

        var range = factory.rawBlock("(1 .. 10)");
        var foreach = factory.forEachBlock("i");
        graph.addNode(range);
        graph.addNode(foreach);
        graph.addEdge(factory.createEdge(foreach, FOREACH_LIST, range));
        graph.addEdge(factory.createEdge(init, FLOW_OUT, foreach));

        var ass = factory.assignBlock();
        var add = factory.binaryBlock("+");
        var i = factory.refBlock("i").get();
        graph.addNode(ass);
        graph.addNode(add);
        graph.addNode(i);
        graph.addEdge(factory.createEdge(foreach, FOREACH_BODY, ass));
        graph.addEdge(factory.createEdge(ass, ASSIGN_LHS, s));
        graph.addEdge(factory.createEdge(ass, ASSIGN_RHS, add));
        graph.addEdge(factory.createEdge(add, A, s));
        graph.addEdge(factory.createEdge(add, B, i));




        // Testing TileClass CRUD Interface
        eci.createTileClass("demoClass");

        TileClass demo = eci.getTileClass("demoClass");
//        demo.setDefaultHeightWidth(1, 1);
        demo.addImagePath("hellotester");



        Point p = new PointImpl(1, 2);

        System.out.println(tools.toEngineXML());
    }
}
