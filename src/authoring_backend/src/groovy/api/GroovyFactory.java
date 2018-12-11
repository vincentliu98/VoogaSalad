package groovy.api;

import authoringUtils.frontendUtils.Try;
import gameObjects.crud.GameObjectsCRUDInterface;
import groovy.graph.BlockEdgeImpl;
import groovy.graph.BlockGraphImpl;
import groovy.graph.blocks.core.*;
import groovy.graph.blocks.small_factory.LiteralFactory;

import java.util.Map;

/**
 * A Factory that can generate Graph/Node/Edge that represents Groovy code.
 */
public class GroovyFactory {
    private GameObjectsCRUDInterface entityDB;

    public GroovyFactory(GameObjectsCRUDInterface entityDB) {
        this.entityDB = entityDB;
    }

    /**
     * Makes an createPhaseGraph BlockGraph with one source node
     */
    public BlockGraph createEmptyGraph() {
        return new BlockGraphImpl();
    }

    public BlockGraph createDefaultGuard() {
        try {
            var graph = new BlockGraphImpl();
            var returnBlock = functionBlock(1000, 1200, "GameMethods.$return", Map.of(Ports.A, "Object retVal"));
            var trueBlock = booleanBlock(1200, 1200, "true").get();
            graph.addNode(returnBlock);
            graph.addNode(trueBlock);
            graph.addEdge(createEdge(returnBlock, Ports.A, trueBlock));
            graph.addEdge(createEdge(graph.source(), Ports.FLOW_OUT, returnBlock));
            return graph;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } // can't fail!
        return createEmptyGraph();
    }

    public BlockGraph createDefaultImageSelector() {
        try {
            var graph = new BlockGraphImpl();
            var returnBlock = functionBlock(1000, 1200, "GameMethods.$return", Map.of(Ports.A, "Object retVal"));
            var zeroBlock = integerBlock(1200, 1200, "0").get();
            graph.addNode(returnBlock);
            graph.addNode(zeroBlock);
            graph.addEdge(createEdge(returnBlock, Ports.A, zeroBlock));
            graph.addEdge(createEdge(graph.source(), Ports.FLOW_OUT, returnBlock));
            return graph;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } // can't fail!
        return createEmptyGraph();
    }

    /**
     * Makes an edge
     */
    public BlockEdge createEdge(GroovyBlock from, Ports fromPort, GroovyBlock to) {
        return new BlockEdgeImpl(from, fromPort, to);
    }

    /**
     * Core Blocks
     */
    public GroovyBlock<?> ifBlock(double x, double y) {
        return new IfBlock(x, y, false);
    }

    public GroovyBlock<?> ifElseBlock(double x, double y) {
        return new IfBlock(x, y, true);
    }

    public GroovyBlock<?> elseBlock(double x, double y) {
        return new ElseBlock(x, y);
    }

    public GroovyBlock<?> forEachBlock(double x, double y, String loopvar) {
        return new ForEachBlock(x, y, loopvar);
    }

    public GroovyBlock<?> assignBlock(double x, double y) {
        return new AssignBlock(x, y);
    }

    public Try<GroovyBlock<?>> booleanBlock(double x, double y, String value) {
        return LiteralFactory.booleanBlock(x, y, value);
    }

    public Try<GroovyBlock<?>> integerBlock(double x, double y, String value) {
        return LiteralFactory.integerBlock(x, y, value);
    }

    public Try<GroovyBlock<?>> keyBlock(double x, double y, String value) {
        return LiteralFactory.keyBlock(x, y, value);
    }

    public Try<GroovyBlock<?>> doubleBlock(double x, double y, String value) {
        return LiteralFactory.doubleBlock(x, y, value);
    }

    public Try<GroovyBlock<?>> listBlock(double x, double y, String value) {
        return LiteralFactory.listBlock(x, y, value);
    }

    public Try<GroovyBlock<?>> mapBlock(double x, double y, String value) {
        return LiteralFactory.mapBlock(x, y, value);
    }

    public GroovyBlock<?> stringBlock(double x, double y, String value) {
        return LiteralFactory.stringBlock(x, y, value);
    }

    public Try<GroovyBlock<?>> refBlock(double x, double y, String value) {
        return LiteralFactory.refBlock(x, y, value, entityDB);
    }

    public GroovyBlock<?> functionBlock(double x, double y, String op, Map<Ports, String> portInfo) {
        return new FunctionBlock(x, y, op, portInfo);
    }

    public GroovyBlock<?> binaryBlock(double x, double y, String op) {
        return new InfixBinaryBlock(x, y, op);
    }

    public GroovyBlock<?> rawBlock(double x, double y, String code) {
        return new RawGroovyBlock(x, y, code);
    }
}

