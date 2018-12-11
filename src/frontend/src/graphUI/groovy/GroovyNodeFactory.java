package graphUI.groovy;

import authoringUtils.frontendUtils.Try;
import groovy.api.GroovyFactory;
import groovy.api.Ports;
import groovy.graph.blocks.core.*;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A factory that reproduces a node
 *
 * @author Inchan Hwang
 */
public class GroovyNodeFactory {
    private static final int FONT_BIG = 18;
    private static final int FONT_NORMAL = 12;
    private static final List<Pair<Pos, Ports>> FUNCTION_PORT = List.of(
            new Pair<>(Pos.TOP_RIGHT, Ports.A),
            new Pair<>(Pos.CENTER_RIGHT, Ports.B),
            new Pair<>(Pos.BOTTOM_RIGHT, Ports.C),
            new Pair<>(Pos.TOP_LEFT, Ports.D),
            new Pair<>(Pos.TOP_CENTER, Ports.E)
    );
    private static List<Pair<Pos, Ports>> SOURCE_PORT = List.of(
            new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );
    private static List<Pair<Pos, Ports>> FOREACH_PORT = List.of(
            new Pair<>(Pos.TOP_RIGHT, Ports.FOREACH_LIST),
            new Pair<>(Pos.BOTTOM_RIGHT, Ports.FOREACH_BODY),
            new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );
    private static List<Pair<Pos, Ports>> IF_PORT = List.of(
            new Pair<>(Pos.TOP_RIGHT, Ports.IF_PREDICATE),
            new Pair<>(Pos.BOTTOM_RIGHT, Ports.IF_BODY),
            new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );
    private static List<Pair<Pos, Ports>> ELSE_PORT = List.of(
            new Pair<>(Pos.CENTER_RIGHT, Ports.IF_BODY),
            new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );
    private static List<Pair<Pos, Ports>> ASSIGN_PORT = List.of(
            new Pair<>(Pos.TOP_RIGHT, Ports.ASSIGN_LHS),
            new Pair<>(Pos.BOTTOM_RIGHT, Ports.ASSIGN_RHS),
            new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );
    private static List<Pair<Pos, Ports>> BINARY_PORT = List.of(
            new Pair<>(Pos.TOP_RIGHT, Ports.A),
            new Pair<>(Pos.BOTTOM_RIGHT, Ports.B)
    );
    private GroovyFactory factory;

    public GroovyNodeFactory(GroovyFactory factory) {
        this.factory = factory;
    }


    /**
     * Dragged Icon -----> View
     */
    public Try<GroovyNode> makeNode(
            String blockType, Map<Ports, String> portInfo, double xPos, double yPos, String arg
    ) {
        return toModel(blockType, portInfo, xPos, yPos, arg).map(this::toView);
    }

    /**
     * Dragged Icon -----> Model
     */
    private Try<GroovyBlock<?>> toModel(
            String blockType, Map<Ports, String> portInfo, double xPos, double yPos, String arg
    ) {
        if (blockType.equals("each")) return Try.success(factory.forEachBlock(xPos, yPos, arg));
        if (blockType.equals("if")) return Try.success(factory.ifBlock(xPos, yPos));
        if (blockType.equals("elseIf")) return Try.success(factory.ifElseBlock(xPos, yPos));
        if (blockType.equals("else")) return Try.success(factory.elseBlock(xPos, yPos));
        if (blockType.equals("assign")) return Try.success(factory.assignBlock(xPos, yPos));

        if (blockType.equals("add")) return Try.success(factory.binaryBlock(xPos, yPos, "+"));
        if (blockType.equals("minus")) return Try.success(factory.binaryBlock(xPos, yPos, "-"));
        if (blockType.equals("mult")) return Try.success(factory.binaryBlock(xPos, yPos, "*"));
        if (blockType.equals("div")) return Try.success(factory.binaryBlock(xPos, yPos, "/"));
        if (blockType.equals("eq")) return Try.success(factory.binaryBlock(xPos, yPos, "=="));
        if (blockType.equals("neq")) return Try.success(factory.binaryBlock(xPos, yPos, "!="));
        if (blockType.equals("lt")) return Try.success(factory.binaryBlock(xPos, yPos, "<"));
        if (blockType.equals("gt")) return Try.success(factory.binaryBlock(xPos, yPos, ">"));
        if (blockType.equals("leq")) return Try.success(factory.binaryBlock(xPos, yPos, "<="));
        if (blockType.equals("geq")) return Try.success(factory.binaryBlock(xPos, yPos, ">="));
        if (blockType.equals("and")) return Try.success(factory.binaryBlock(xPos, yPos, "&&"));
        if (blockType.equals("or")) return Try.success(factory.binaryBlock(xPos, yPos, "||"));
        if (blockType.equals("contains")) return Try.success(factory.binaryBlock(xPos, yPos, ".contains"));
        if (blockType.equals("range")) return Try.success(factory.binaryBlock(xPos, yPos, ".."));
        if (blockType.equals("binary")) return Try.success(factory.binaryBlock(xPos, yPos, arg));

        if (blockType.equals("true")) return factory.booleanBlock(xPos, yPos, "true");
        if (blockType.equals("false")) return factory.booleanBlock(xPos, yPos, "false");
        if (blockType.equals("int")) return factory.integerBlock(xPos, yPos, arg);
        if (blockType.equals("double")) return factory.doubleBlock(xPos, yPos, arg);
        if (blockType.equals("list")) return factory.listBlock(xPos, yPos, arg);
        if (blockType.equals("map")) return factory.mapBlock(xPos, yPos, arg);
        if (blockType.equals("str")) return Try.success(factory.stringBlock(xPos, yPos, arg));
        if (blockType.equals("ref")) return factory.refBlock(xPos, yPos, arg);
        if (blockType.equals("$clicked")) return factory.refBlock(xPos, yPos, "$clicked");
        if (blockType.equals("$pressed")) return factory.refBlock(xPos, yPos, "$pressed");
        if (blockType.equals("A")) return factory.keyBlock(xPos, yPos, "65");
        if (blockType.equals("S")) return factory.keyBlock(xPos, yPos, "83");
        if (blockType.equals("D")) return factory.keyBlock(xPos, yPos, "68");
        if (blockType.equals("W")) return factory.keyBlock(xPos, yPos, "87");
        if (blockType.equals("key1")) return factory.keyBlock(xPos, yPos, "49");
        if (blockType.equals("key2")) return factory.keyBlock(xPos, yPos, "50");
        if (blockType.equals("key3")) return factory.keyBlock(xPos, yPos, "51");
        if (blockType.equals("enter")) return factory.keyBlock(xPos, yPos, "10");
        if (blockType.equals("ESC")) return factory.keyBlock(xPos, yPos, "27");
        if (blockType.equals("space")) return factory.keyBlock(xPos, yPos, "32");

        if (blockType.equals("function")) return Try.success(factory.functionBlock(xPos, yPos, arg, null));

        return Try.success(factory.functionBlock(xPos, yPos, blockType, portInfo));
    }

    /**
     * Model -----> View
     */
    public GroovyNode toView(GroovyBlock<?> model) {
        if (model instanceof SourceBlock) {
            return new GroovyNode(model, 50, 50, FONT_NORMAL, Color.INDIGO, SOURCE_PORT);
        } else if (model instanceof ForEachBlock) {
            return new GroovyNode(model, 100, 50, FONT_BIG, Color.LIGHTBLUE, FOREACH_PORT);
        } else if (model instanceof IfBlock) {
            return new GroovyNode(model, 50, 50, FONT_BIG, Color.GOLD, IF_PORT);
        } else if (model instanceof ElseBlock) {
            return new GroovyNode(model, 50, 50, FONT_BIG, Color.GOLD, ELSE_PORT);
        } else if (model instanceof AssignBlock) {
            return new GroovyNode(model, 50, 50, FONT_BIG, Color.LIGHTSKYBLUE, ASSIGN_PORT);
        } else if (model instanceof LiteralBlock) {
            return new GroovyNode(model, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of());
        } else if (model instanceof InfixBinaryBlock) {
            return new GroovyNode(model, 50, 50, FONT_BIG, Color.DARKRED, BINARY_PORT);
        } else {
            var portInfo = (Map<Ports, String>) model.params().get("portInfo");
            var argN = portInfo == null ? 5 : portInfo.size();
            return new GroovyNode(model, 120, 50, FONT_NORMAL, Color.PERU, functionPorts(argN), portInfo);
        }
    }

    private List<Pair<Pos, Ports>> functionPorts(int argN) {
        var portList = new ArrayList<Pair<Pos, Ports>>();
        for (int i = 0; i < argN; i++) portList.add(FUNCTION_PORT.get(i));
        portList.add(new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT));
        return portList;
    }

    public ShrunkGroovyNode shrunkBlock(Set<GroovyNode> inner, String description) {
        var sumX = inner.stream().map(GroovyNode::getCenterX).reduce((a, b) -> a + b);
        var sumY = inner.stream().map(GroovyNode::getCenterY).reduce((a, b) -> a + b);
        return
                new ShrunkGroovyNode(
                        description,
                        sumX.get() / inner.size(), sumY.get() / inner.size(),
                        130, 130, FONT_BIG, Color.BLACK, inner
                );
    }

}
