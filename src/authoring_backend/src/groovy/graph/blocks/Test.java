package authoring_backend.src.groovy.graph.blocks;

import authoring_backend.src.groovy.graph.BlockGraph;
import authoring_backend.src.essentials.GameData;
import authoring_backend.src.groovy.graph.blocks.binary_ops.AddBlock;
import authoring_backend.src.groovy.graph.blocks.control.AssignBlock;
import authoring_backend.src.groovy.graph.blocks.control.ForBlock;
import authoring_backend.src.groovy.graph.blocks.literal.IntegerBlock;

import static authoring_backend.src.groovy.graph.Ports.*;

public class Test {
    public static void main(String[] args) throws Throwable {
        var gamedata = new GameData();

        var graph = new BlockGraph();

        var sourceBlock = new SourceBlock();
        var forBlock = new ForBlock();
        var checkBlock = new RawGroovyBlock("x < 10");
        var mutateBlock = new RawGroovyBlock("x ++");
        var addBlock = new AddBlock();
        var assignBlock = new AssignBlock(gamedata);
        assignBlock.prefix().set("int");
        assignBlock.varName().set("i");
        var i = new IntegerBlock();
        var assignBlock2 = new AssignBlock(gamedata);
        assignBlock2.prefix().set("int");
        assignBlock2.varName().set("sum");
        var a = new IntegerBlock();
        var b = new IntegerBlock();

        a.model().setValue(10);
        b.model().setValue(20);

        graph.addNode(sourceBlock);
        graph.addNode(forBlock);
        graph.addNode(checkBlock);
        graph.addNode(mutateBlock);
        graph.addNode(addBlock);
        graph.addNode(assignBlock);
        graph.addNode(assignBlock2);
        graph.addNode(a);
        graph.addNode(b);

        graph.addEdge(sourceBlock, FLOW_OUT, forBlock);
        graph.addEdge(forBlock, FOR_INIT, assignBlock);
        graph.addEdge(assignBlock, ASSIGN_RHS, i);
        graph.addEdge(forBlock, FOR_CHECK, checkBlock);
        graph.addEdge(forBlock, FOR_MUTATE, mutateBlock);
        graph.addEdge(forBlock, FOR_BODY, assignBlock2);
        graph.addEdge(assignBlock2, ASSIGN_RHS, addBlock);
        graph.addEdge(addBlock, A, a);
        graph.addEdge(addBlock, B, b);

        System.out.println(sourceBlock.toGroovy(graph).get());

        a.model().setValue(20);
        assignBlock2.varName().set("hp");
        assignBlock2.prefix().set("");

        System.out.println(sourceBlock.toGroovy(graph).get());
    }
}
