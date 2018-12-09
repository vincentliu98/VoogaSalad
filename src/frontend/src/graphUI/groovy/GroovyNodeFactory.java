package graphUI.groovy;

import graphUI.groovy.DraggableGroovyIconFactory.DraggableGroovyIcon;
import authoringUtils.frontendUtils.Try;
import groovy.api.GroovyFactory;
import groovy.api.Ports;
import groovy.graph.blocks.core.FunctionBlock;
import groovy.graph.blocks.core.GroovyBlock;
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
    public GroovyNodeFactory(GroovyFactory factory) { this.factory = factory; }

    public GroovyNode source(GroovyBlock sourceBlock, double xPos, double yPos) {
        return new GroovyNode(sourceBlock, xPos, yPos, 50, 50, FONT_NORMAL, Color.INDIGO, SOURCE_PORT, null);
    }

    /**
     *  Model -----> View
     */


    /**
     *   Dragged Icon -----> Model
     */
    public Try<GroovyNode> toModel(
        String blockType, Map<Ports, String> portInfo, double xPos, double yPos, String arg
    ) {
        if(blockType.equals("each")) return Try.success(forEachBlock(xPos, yPos, arg));
        if(blockType.equals("if")) return Try.success(ifBlock(xPos, yPos));
        if(blockType.equals("elseIf")) return Try.success(ifElseBlock(xPos, yPos));
        if(blockType.equals("else")) return Try.success(elseBlock(xPos, yPos));
        if(blockType.equals("assign")) return Try.success(assignBlock(xPos, yPos));

        if(blockType.equals("add")) return Try.success(binaryBlock(xPos, yPos, "+"));
        if(blockType.equals("minus")) return Try.success(binaryBlock(xPos, yPos, "-"));
        if(blockType.equals("mult")) return Try.success(binaryBlock(xPos, yPos, "*"));
        if(blockType.equals("div")) return Try.success(binaryBlock(xPos, yPos, "/"));
        if(blockType.equals("eq")) return Try.success(binaryBlock(xPos, yPos, "=="));
        if(blockType.equals("neq")) return Try.success(binaryBlock(xPos, yPos, "!="));
        if(blockType.equals("lt")) return Try.success(binaryBlock(xPos, yPos, "<"));
        if(blockType.equals("gt")) return Try.success(binaryBlock(xPos, yPos, ">"));
        if(blockType.equals("leq")) return Try.success(binaryBlock(xPos, yPos, "<="));
        if(blockType.equals("geq")) return Try.success(binaryBlock(xPos, yPos, ">="));
        if(blockType.equals("and")) return Try.success(binaryBlock(xPos, yPos, "&&"));
        if(blockType.equals("or")) return Try.success(binaryBlock(xPos, yPos, "||"));
        if(blockType.equals("contains")) return Try.success(binaryBlock(xPos, yPos, ".contains"));
        if(blockType.equals("range")) return Try.success(binaryBlock(xPos, yPos, ".."));
        if(blockType.equals("binary")) return Try.success(binaryBlock(xPos, yPos, arg));

        if(blockType.equals("true")) return booleanBlock(xPos, yPos, "true");
        if(blockType.equals("false")) return booleanBlock(xPos, yPos, "false");
        if(blockType.equals("int")) return integerBlock(xPos, yPos, arg);
        if(blockType.equals("double")) return doubleBlock(xPos, yPos, arg);
        if(blockType.equals("list")) return listBlock(xPos, yPos, arg);
        if(blockType.equals("map")) return mapBlock(xPos, yPos, arg);
        if(blockType.equals("str")) return Try.success(stringBlock(xPos, yPos, arg));
        if(blockType.equals("ref")) return refBlock(xPos, yPos, arg);
        if(blockType.equals("$clicked")) return refBlock(xPos, yPos, "$clicked");
        if(blockType.equals("$pressed")) return refBlock(xPos, yPos, "$pressed");
        if(blockType.equals("A")) return keyBlock(xPos, yPos, "65");
        if(blockType.equals("S")) return keyBlock(xPos, yPos, "83");
        if(blockType.equals("D")) return keyBlock(xPos, yPos, "68");
        if(blockType.equals("W")) return keyBlock(xPos, yPos, "87");
        if(blockType.equals("key1")) return keyBlock(xPos, yPos, "49");
        if(blockType.equals("key2")) return keyBlock(xPos, yPos, "50");
        if(blockType.equals("key3")) return keyBlock(xPos, yPos, "51");
        if(blockType.equals("enter")) return keyBlock(xPos, yPos, "10");
        if(blockType.equals("ESC")) return keyBlock(xPos, yPos, "27");
        if(blockType.equals("space")) return keyBlock(xPos, yPos, "32");

        if(blockType.equals("function")) return Try.success(functionBlock(xPos, yPos, arg, null));

        return Try.success(functionBlock(xPos, yPos, blockType, portInfo));
    }

    public GroovyNode forEachBlock(double xPos, double yPos, String loopvar) {
        var block = factory.forEachBlock(loopvar);
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.LIGHTBLUE, FOREACH_PORT);
    }

    public GroovyNode ifBlock(double xPos, double yPos) {
        var block = factory.ifBlock();
        return new GroovyNode(block, xPos, yPos, 50, 50, FONT_BIG, Color.GOLD, IF_PORT);
    }

    public GroovyNode ifElseBlock(double xPos, double yPos) {
        var block = factory.ifElseBlock();
        return new GroovyNode(block, xPos, yPos, 50, 50, FONT_BIG, Color.GOLD, IF_PORT);
    }

    public GroovyNode elseBlock(double xPos, double yPos) {
        var block = factory.elseBlock();
        return new GroovyNode(block, xPos, yPos, 50, 50, FONT_BIG, Color.GOLD, ELSE_PORT);
    }

    public GroovyNode assignBlock(double xPos, double yPos) {
        var block = factory.assignBlock();
        return new GroovyNode(block, xPos, yPos, 50, 50, FONT_BIG, Color.LIGHTSKYBLUE, ASSIGN_PORT);
    }

    public Try<GroovyNode> booleanBlock(double xPos, double yPos, String value) {
        var block = factory.booleanBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    public Try<GroovyNode> integerBlock(double xPos, double yPos, String value) {
        var block = factory.integerBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    public Try<GroovyNode> keyBlock(double xPos, double yPos, String value) {
        var block = factory.keyBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    public Try<GroovyNode> doubleBlock(double xPos, double yPos, String value) {
        var block = factory.doubleBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    public GroovyNode stringBlock(double xPos, double yPos, String value) {
        var block = factory.stringBlock(value);
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of());
    }

    public Try<GroovyNode> listBlock(double xPos, double yPos, String value) {
        var block = factory.listBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    public Try<GroovyNode> mapBlock(double xPos, double yPos, String value) {
        var block = factory.mapBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    public Try<GroovyNode> refBlock(double xPos, double yPos, String value) {
        var block = factory.refBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 100, 50, FONT_NORMAL, Color.DARKGREEN, List.of()));
    }

    /**
     *  GROSS!
     */
    public GroovyNode functionBlock(double xPos, double yPos, String op, Map<Ports, String> portInfo) {
        var split = op.split("\\.");
        var last = split[split.length-1];
        var idx = last.indexOf("(");
        FunctionBlock block;
        try {
            var argN = Integer.parseInt(last.substring(idx+1, last.length()-1));
            block = factory.functionBlock(op.substring(0, op.length()-last.length()+idx), argN);
            return new GroovyNode(block, xPos, yPos, 120, 50, FONT_BIG, Color.PERU, functionPorts(argN), portInfo);
        } catch (Exception e) {
            block = factory.functionBlock(op, -1); // don't know
            return new GroovyNode(block, xPos, yPos, 120, 50, FONT_BIG, Color.PERU, functionPorts(5), portInfo);
        }
    }

    private static List<Pair<Pos, Ports>> FUNCTION_PORT = List.of(
        new Pair<>(Pos.TOP_RIGHT, Ports.A),
        new Pair<>(Pos.CENTER_RIGHT, Ports.B),
        new Pair<>(Pos.BOTTOM_RIGHT, Ports.C),
        new Pair<>(Pos.TOP_LEFT, Ports.D),
        new Pair<>(Pos.TOP_CENTER, Ports.E)
    );

    private List<Pair<Pos, Ports>> functionPorts(int argN) {
        var portList = new ArrayList<Pair<Pos, Ports>>();
        for(int i = 0 ; i < argN ; i ++) portList.add(FUNCTION_PORT.get(i));
        portList.add(new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT));
        return portList;
    }

    public GroovyNode binaryBlock(double xPos, double yPos, String op) {
        var block = factory.binaryBlock(op);
        return new GroovyNode(block, xPos, yPos, 50, 50, FONT_BIG, Color.DARKRED, BINARY_PORT);
    }

    public ShrunkGroovyNode shrunkBlock(Set<GroovyNode> inner, String description) {
        var sumX = inner.stream().map(GroovyNode::getCenterX).reduce((a, b) -> a+b);
        var sumY = inner.stream().map(GroovyNode::getCenterY).reduce((a, b) -> a+b);
        return
            new ShrunkGroovyNode(
                description,
                sumX.get()/inner.size(), sumY.get()/inner.size(),
                130, 130, FONT_BIG, Color.BLACK, inner
            );
    }
}
