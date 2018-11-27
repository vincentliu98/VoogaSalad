import authoring.AuthoringTools;
import groovy.api.GroovyFactory;
import groovy.lang.GroovyShell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static groovy.api.Ports.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GroovyBlockTest {
    private AuthoringTools tools;
    private GroovyFactory factory;
    private GroovyShell shell;

    @BeforeEach
    public void setupTestData() {
        tools = new AuthoringTools();
        factory = tools.factory();
        shell = new GroovyShell();
    }

    @Test
    public void testBasicTransformation() throws Throwable {
        var graph = factory.createGraph();
        var source = graph.source();

        // The author creates an assign block
        var assign = factory.assignBlock();
        graph.addNode(assign);

        // The author creates an ref block
        var hpRef = factory.refBlock("$clicked.hp").get();
        graph.addNode(hpRef);

        // The author connects these things
        var e1 = factory.createEdge(source, FLOW_OUT, assign);
        graph.addEdge(e1);
        var e2 = factory.createEdge(assign, ASSIGN_LHS, hpRef);
        graph.addEdge(e2);

        // The author, since he already has the reference to hp,
        // goes on to make - block and attackPower block
        var minus = factory.binaryBlock("-");
        graph.addNode(minus);
        var attackRef = factory.refBlock("selected.attackPower").get();
        graph.addNode(attackRef);

        // Then he connects everything.
        var e3 = factory.createEdge(assign, ASSIGN_RHS, minus);
        graph.addEdge(e3);
        var e4 = factory.createEdge(minus, A, hpRef);
        graph.addEdge(e4);
        var e5 = factory.createEdge(minus, B, attackRef);
        graph.addEdge(e5);

        // Now the author makes the second line
        // if($clicked.hp <= 0) $gameObjects.$instances.remove($clicked)

        var ifBlock = factory.ifBlock();
        graph.addNode(ifBlock);
        var zero = factory.integerBlock("0").get();
        graph.addNode(zero);
        var leq = factory.binaryBlock("<=");
        graph.addNode(leq);

        var e6 = factory.createEdge(ifBlock, IF_PREDICATE, leq);
        graph.addEdge(e6);
        var e7 = factory.createEdge(leq, A, hpRef);
        graph.addEdge(e7);
        var e8 = factory.createEdge(leq, B, zero);
        graph.addEdge(e8);

        // we should make this a separate thing for convenience
        var removeInstance = factory.unaryBlock("remove");
        graph.addNode(removeInstance);
        var clicked = factory.rawBlock("$clicked");
        graph.addNode(clicked);
        var e9 = factory.createEdge(removeInstance, A, clicked);
        graph.addEdge(e9);
        var e10 = factory.createEdge(ifBlock, IF_BODY, removeInstance);
        graph.addEdge(e10);
        var e11 = factory.createEdge(assign, FLOW_OUT, ifBlock);
        graph.addEdge(e11);

        // $global.turn = 1 - $global.turn

        var assign2 = factory.assignBlock();
        graph.addNode(assign2);
        var turnRef = factory.refBlock("$global.turn").get();
        graph.addNode(turnRef);
        var minus2 = factory.binaryBlock("-");
        graph.addNode(minus2);
        var one = factory.integerBlock("1").get();
        graph.addNode(one);

        var e12 = factory.createEdge(assign2, ASSIGN_LHS, turnRef);
        graph.addEdge(e12);
        var e13 = factory.createEdge(assign2, ASSIGN_RHS, minus2);
        graph.addEdge(e13);
        var e14 = factory.createEdge(minus2, A, one);
        graph.addEdge(e14);
        var e15 = factory.createEdge(minus2, B, turnRef);
        graph.addEdge(e15);

        var e16 = factory.createEdge(ifBlock, FLOW_OUT, assign2);
        graph.addEdge(e16);

        // $goto("A")

        // should make this a separate thing as well
        var go2 = factory.unaryBlock("$goto");
        graph.addNode(go2);
        var to = factory.stringBlock("A");
        graph.addNode(to);

        var e17 = factory.createEdge(go2, A, to);
        graph.addEdge(e17);

        var e18 = factory.createEdge(assign2, FLOW_OUT, go2);
        graph.addEdge(e18);
        assertTrue(graph.transformToGroovy().isSuccess());
    }

    @Test
    public void testSingleLine() throws Throwable {
        // Testing on the actual shell
        var singleLineScript = factory.createGuard().transformToGroovy().get();
        shell.evaluate(singleLineScript);
        assertTrue((boolean) shell.getVariable("$guardRet"));
    }

    @Test
    public void testMultiLine() throws Throwable {
        var graph2 = factory.createGraph();

        var s = factory.refBlock("sum").get();
        var init = factory.assignBlock();
        var zero2 = factory.integerBlock("0").get();
        graph2.addNode(s);
        graph2.addNode(init);
        graph2.addNode(zero2);
        graph2.addEdge(factory.createEdge(init, ASSIGN_LHS, s));
        graph2.addEdge(factory.createEdge(init, ASSIGN_RHS, zero2));
        graph2.addEdge(factory.createEdge(graph2.source(), FLOW_OUT, init));

        var range = factory.rawBlock("1 .. 10");
        var foreach = factory.forEachBlock("i");
        graph2.addNode(range);
        graph2.addNode(foreach);
        graph2.addEdge(factory.createEdge(foreach, FOREACH_LIST, range));
        graph2.addEdge(factory.createEdge(init, FLOW_OUT, foreach));


        var ass = factory.assignBlock();
        var add = factory.binaryBlock("+");
        var b = factory.refBlock("i").get();
        graph2.addNode(ass);
        graph2.addNode(add);
        graph2.addNode(b);
        graph2.addEdge(factory.createEdge(foreach, FOREACH_BODY, ass));
        graph2.addEdge(factory.createEdge(ass, ASSIGN_LHS, s));
        graph2.addEdge(factory.createEdge(ass, ASSIGN_RHS, add));
        graph2.addEdge(factory.createEdge(add, A, s));
        graph2.addEdge(factory.createEdge(add, B, b));

        // Multi-line code evaluation
        var multiLineScript = graph2.transformToGroovy().get();
        shell.evaluate(multiLineScript);
        assertEquals((int) shell.getVariable("sum"), 45);
    }
}
