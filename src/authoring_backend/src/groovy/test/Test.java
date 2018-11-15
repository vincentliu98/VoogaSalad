package groovy.test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;
import essentials.GameData;
import groovy.api.BlockGraph;
import groovy.api.BlockGraphConverter;
import groovy.api.GroovyFactory;
import groovy.graph.BlockGraphImpl;

import static groovy.api.Ports.*;

public class Test {
    public static void main(String[] args) throws Throwable {
        /**
         *  The goal
         *  ------------
         *  $clicked.hp = $clicked.hp - selected.attackPower
         * 	if($clicked.hp <= 0) $entities.$instances.remove($clicked)
         * 	$global.turn = 1 - $global.turn
         * 	$goto("A")
         * 	------------
         */

        var data = new GameData();

        // System creates a graph for the author
        var graph = GroovyFactory.emptyGraph();
        var source = graph.source();

        // The author creates an assign block
        var assign = GroovyFactory.assignBlock();
        graph.addNode(assign);

        // The author creates an ref block
        var hpRef = GroovyFactory.refBlock("$clicked.hp", data).get();
        graph.addNode(hpRef);

        // The author connects these things
        var e1 = GroovyFactory.makeEdge(source, FLOW_OUT, assign);
        graph.addEdge(e1);
        var e2 = GroovyFactory.makeEdge(assign, ASSIGN_LHS, hpRef);
        graph.addEdge(e2);

        // The author, since he already has the reference to hp,
        // goes on to make - block and attackPower block
        var minus = GroovyFactory.binaryBlock("-");
        graph.addNode(minus);
        var attackRef = GroovyFactory.refBlock("selected.attackPower", data).get();
        graph.addNode(attackRef);

        // Then he connects everything.
        var e3 = GroovyFactory.makeEdge(assign, ASSIGN_RHS, minus);
        graph.addEdge(e3);
        var e4 = GroovyFactory.makeEdge(minus, A, hpRef);
        graph.addEdge(e4);
        var e5 = GroovyFactory.makeEdge(minus, B, attackRef);
        graph.addEdge(e5);

        // Now the author makes the second line
        // if($clicked.hp <= 0) $entities.$instances.remove($clicked)

        var ifBlock = GroovyFactory.ifBlock();
        graph.addNode(ifBlock);
        var zero = GroovyFactory.integerBlock("0").get();
        graph.addNode(zero);
        var leq = GroovyFactory.leq();
        graph.addNode(leq);

        var e6 = GroovyFactory.makeEdge(ifBlock, IF_PREDICATE, leq);
        graph.addEdge(e6);
        var e7 = GroovyFactory.makeEdge(leq, A, hpRef);
        graph.addEdge(e7);
        var e8 = GroovyFactory.makeEdge(leq, B, zero);
        graph.addEdge(e8);

        // we should make this a separate thing for convenience
        var removeInstance = GroovyFactory.$remove();
        graph.addNode(removeInstance);
        var clicked = GroovyFactory.$clicked(data).get();
        graph.addNode(clicked);
        var e9 = GroovyFactory.makeEdge(removeInstance, A, clicked);
        graph.addEdge(e9);
        var e10 = GroovyFactory.makeEdge(ifBlock, IF_BODY, removeInstance);
        graph.addEdge(e10);
        var e11 = GroovyFactory.makeEdge(assign, FLOW_OUT, ifBlock);
        graph.addEdge(e11);

        // $global.turn = 1 - $global.turn

        var assign2 = GroovyFactory.assignBlock();
        graph.addNode(assign2);
        var turnRef = GroovyFactory.refBlock("$global.turn", data).get();
        graph.addNode(turnRef);
        var minus2 = GroovyFactory.minus();
        graph.addNode(minus2);
        var one = GroovyFactory.integerBlock("1").get();
        graph.addNode(one);

        var e12 = GroovyFactory.makeEdge(assign2, ASSIGN_LHS, turnRef);
        graph.addEdge(e12);
        var e13 = GroovyFactory.makeEdge(assign2, ASSIGN_RHS, minus2);
        graph.addEdge(e13);
        var e14 = GroovyFactory.makeEdge(minus2, A, one);
        graph.addEdge(e14);
        var e15 = GroovyFactory.makeEdge(minus2, B, turnRef);
        graph.addEdge(e15);

        var e16 = GroovyFactory.makeEdge(ifBlock, FLOW_OUT, assign2);
        graph.addEdge(e16);

        // $goto("A")

        // should make this a separate thing as well
        var go2 = GroovyFactory.$goto();
        graph.addNode(go2);
        var to = GroovyFactory.stringBlock("A");
        graph.addNode(to);

        var e17 = GroovyFactory.makeEdge(go2, A, to);
        graph.addEdge(e17);

        var e18 = GroovyFactory.makeEdge(assign2, FLOW_OUT, go2);
        graph.addEdge(e18);

//        System.out.println(graph.transformToGroovy().get());


        // foreach test
        BlockGraph graph2 = new BlockGraphImpl();
        var range = GroovyFactory.range(1, 10).get();
        graph2.addNode(range);
        var foreach = GroovyFactory.forEachBlock("i");
        graph2.addNode(foreach);
        graph2.addEdge(GroovyFactory.makeEdge(foreach, FOREACH_LIST, range));
        graph2.addEdge(GroovyFactory.makeEdge(graph2.source(), FLOW_OUT, foreach));

        var m = GroovyFactory.$clicked(data).get();
        var ass = GroovyFactory.assignBlock();
        var s = GroovyFactory.refBlock("selected.bar", data).get();
        graph2.addEdge(GroovyFactory.makeEdge(foreach, FOREACH_BODY, ass));
        graph2.addEdge(GroovyFactory.makeEdge(ass, ASSIGN_LHS, m));
        graph2.addEdge(GroovyFactory.makeEdge(ass, ASSIGN_RHS, s));
//        System.out.println(graph2.transformToGroovy().get());

//      xstream test
        XStream xstream = new XStream(new DomDriver());
        xstream.registerConverter(new BlockGraphConverter());
        xstream.alias("groovy", BlockGraphImpl.class);
        var res = xstream.toXML(graph);
        System.out.println(res);
        System.out.println(xstream.fromXML(res));
    }
}
