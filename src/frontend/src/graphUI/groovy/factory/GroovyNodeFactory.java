package graphUI.groovy.factory;

import groovy.api.GroovyFactory;
import groovy.api.Ports;
import groovy.graph.blocks.core.GroovyBlock;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import utils.Try;

import java.util.List;

public class GroovyNodeFactory {
    private static final int FONT_BIG = 18;
    private static final int FONT_NORMAL = 12;

    private static List<Pair<Pos, Ports>> SOURCE_PORT = List.of(
        new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );

    private static List<Pair<Pos, Ports>> FOREACH_PORT = List.of(
        new Pair<>(Pos.CENTER_LEFT, Ports.FOREACH_LIST),
        new Pair<>(Pos.CENTER_RIGHT, Ports.FOREACH_BODY),
        new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );

    private static List<Pair<Pos, Ports>> IF_PORT = List.of(
        new Pair<>(Pos.CENTER_LEFT, Ports.IF_PREDICATE),
        new Pair<>(Pos.CENTER_RIGHT, Ports.IF_BODY),
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

    private static List<Pair<Pos, Ports>> UNARY_PORT = List.of(
        new Pair<>(Pos.CENTER_RIGHT, Ports.A),
        new Pair<>(Pos.BOTTOM_CENTER, Ports.FLOW_OUT)
    );

    private static List<Pair<Pos, Ports>> BINARY_PORT = List.of(
        new Pair<>(Pos.TOP_RIGHT, Ports.A),
        new Pair<>(Pos.BOTTOM_RIGHT, Ports.B)
    );

    private GroovyFactory factory;
    public GroovyNodeFactory(GroovyFactory factory) { this.factory = factory; }

    public GroovyNode source(GroovyBlock sourceBlock, double xPos, double yPos) {
        return new GroovyNode(sourceBlock, xPos, yPos, 50, 50, FONT_NORMAL, Color.MAGENTA, SOURCE_PORT);
    }

    public Try<GroovyNode> toModel(String type, double xPos, double yPos, String arg) {
        if(type.equals("each")) return Try.success(forEachBlock(xPos, yPos, arg));
        if(type.equals("if")) return Try.success(ifBlock(xPos, yPos));
        if(type.equals("elseIf")) return Try.success(ifElseBlock(xPos, yPos));
        if(type.equals("else")) return Try.success(elseBlock(xPos, yPos));
        if(type.equals("assign")) return Try.success(assignBlock(xPos, yPos));

        if(type.equals("add")) return Try.success(binaryBlock(xPos, yPos, "+"));
        if(type.equals("minus")) return Try.success(binaryBlock(xPos, yPos, "-"));
        if(type.equals("mult")) return Try.success(binaryBlock(xPos, yPos, "*"));
        if(type.equals("div")) return Try.success(binaryBlock(xPos, yPos, "/"));
        if(type.equals("eq")) return Try.success(binaryBlock(xPos, yPos, "=="));
        if(type.equals("neq")) return Try.success(binaryBlock(xPos, yPos, "!="));
        if(type.equals("lt")) return Try.success(binaryBlock(xPos, yPos, "<"));
        if(type.equals("gt")) return Try.success(binaryBlock(xPos, yPos, ">"));
        if(type.equals("leq")) return Try.success(binaryBlock(xPos, yPos, "<="));
        if(type.equals("geq")) return Try.success(binaryBlock(xPos, yPos, ">="));
        if(type.equals("and")) return Try.success(binaryBlock(xPos, yPos, "&&"));
        if(type.equals("or")) return Try.success(binaryBlock(xPos, yPos, "||"));
        if(type.equals("contains")) return Try.success(binaryBlock(xPos, yPos, ".contains"));
        if(type.equals("range")) return Try.success(binaryBlock(xPos, yPos, ".."));
        if(type.equals("binary")) return Try.success(binaryBlock(xPos, yPos, arg));

        if(type.equals("true")) return booleanBlock(xPos, yPos, "true");
        if(type.equals("false")) return booleanBlock(xPos, yPos, "false");
        if(type.equals("int")) return integerBlock(xPos, yPos, arg);
        if(type.equals("double")) return doubleBlock(xPos, yPos, arg);
        if(type.equals("list")) return listBlock(xPos, yPos, arg);
        if(type.equals("map")) return mapBlock(xPos, yPos, arg);
        if(type.equals("str")) return Try.success(stringBlock(xPos, yPos, arg));
        if(type.equals("ref")) return refBlock(xPos, yPos, arg);
        if(type.equals("$clicked")) return Try.success(stringBlock(xPos, yPos, "$clicked"));
        if(type.equals("$pressed")) return Try.success(stringBlock(xPos, yPos, "$pressed"));
        if(type.equals("A")) return keyBlock(xPos, yPos, "65");
        if(type.equals("S")) return keyBlock(xPos, yPos, "83");
        if(type.equals("D")) return keyBlock(xPos, yPos, "68");
        if(type.equals("W")) return keyBlock(xPos, yPos, "87");
        if(type.equals("key1")) return keyBlock(xPos, yPos, "49");
        if(type.equals("key2")) return keyBlock(xPos, yPos, "50");
        if(type.equals("key3")) return keyBlock(xPos, yPos, "51");
        if(type.equals("enter")) return keyBlock(xPos, yPos, "10");
        if(type.equals("ESC")) return keyBlock(xPos, yPos, "27");
        if(type.equals("space")) return keyBlock(xPos, yPos, "32");

        if(type.equals("del")) return Try.success(unaryBlock(xPos, yPos, "$remove"));
        if(type.equals("unary")) return Try.success(unaryBlock(xPos, yPos, arg));
        return Try.failure(new Exception("There is no such type;")); // it can't happen from the user side
    }

    public GroovyNode forEachBlock(double xPos, double yPos, String loopvar) {
        var block = factory.forEachBlock(loopvar);
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.LIGHTBLUE, FOREACH_PORT);
    }

    public GroovyNode ifBlock(double xPos, double yPos) {
        var block = factory.ifBlock();
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.GOLD, IF_PORT);
    }

    public GroovyNode ifElseBlock(double xPos, double yPos) {
        var block = factory.ifElseBlock();
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.GOLD, IF_PORT);
    }

    public GroovyNode elseBlock(double xPos, double yPos) {
        var block = factory.elseBlock();
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.GOLD, ELSE_PORT);
    }

    public GroovyNode assignBlock(double xPos, double yPos) {
        var block = factory.assignBlock();
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.LIGHTSKYBLUE, ASSIGN_PORT);
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

    public GroovyNode unaryBlock(double xPos, double yPos, String op) {
        var block = factory.unaryBlock(op);
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.PERU, UNARY_PORT);
    }

    public GroovyNode binaryBlock(double xPos, double yPos, String op) {
        var block = factory.binaryBlock(op);
        return new GroovyNode(block, xPos, yPos, 100, 50, FONT_BIG, Color.DARKRED, BINARY_PORT);
    }
}
