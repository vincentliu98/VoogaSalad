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
        new Pair<>(Pos.BOTTOM_RIGHT, Ports.ASSIGN_RHS)
    );

    private static List<Pair<Pos, Ports>> UNARY_PORT = List.of(
        new Pair<>(Pos.CENTER_RIGHT, Ports.A)
    );

    private static List<Pair<Pos, Ports>> BINARY_PORT = List.of(
        new Pair<>(Pos.CENTER_RIGHT, Ports.A),
        new Pair<>(Pos.BOTTOM_CENTER, Ports.B)
    );

    private GroovyFactory factory;
    public GroovyNodeFactory(GroovyFactory factory) { this.factory = factory; }

    public GroovyNode source(GroovyBlock sourceBlock, double xPos, double yPos) {
        return new GroovyNode(sourceBlock, xPos, yPos, 50, 50, Color.MAGENTA, SOURCE_PORT);
    }

    public Try<GroovyNode> fromType(String type, double xPos, double yPos, String arg) {
        if(type.equals("ForEach")) return Try.success(forEachBlock(xPos, yPos));
        if(type.equals("If")) return Try.success(ifBlock(xPos, yPos));
        if(type.equals("IfElse")) return Try.success(ifElseBlock(xPos, yPos));
        if(type.equals("Else")) return Try.success(elseBlock(xPos, yPos));
        if(type.equals("Assign")) return Try.success(assignBlock(xPos, yPos));
        if(type.equals("Boolean"))
            return booleanBlock(xPos, yPos, arg);
        if(type.equals("Integer")) return integerBlock(xPos, yPos, arg);
        if(type.equals("Double")) return doubleBlock(xPos, yPos, arg);
        if(type.equals("String")) return Try.success(stringBlock(xPos, yPos, arg));
        if(type.equals("List")) return listBlock(xPos, yPos, arg);
        if(type.equals("Map")) return mapBlock(xPos, yPos, arg);
        if(type.equals("Ref")) return refBlock(xPos, yPos, arg);
        if(type.equals("Unary")) return Try.success(unaryBlock(xPos, yPos, arg));
        if(type.equals("Binary")) return Try.success(binaryBlock(xPos, yPos, arg));
        return Try.failure(new Exception("There is no such type;")); // it can't happen from the user side
    }

    public GroovyNode forEachBlock(double xPos, double yPos) {
        var block = factory.forEachBlock("i");
        return new GroovyNode(block, xPos, yPos, 200, 100, Color.LIGHTBLUE, FOREACH_PORT);
    }

    public GroovyNode ifBlock(double xPos, double yPos) {
        var block = factory.ifBlock();
        return new GroovyNode(block, xPos, yPos, 200, 100, Color.YELLOW, IF_PORT);
    }

    public GroovyNode ifElseBlock(double xPos, double yPos) {
        var block = factory.ifElseBlock();
        return new GroovyNode(block, xPos, yPos, 200, 100, Color.YELLOW, IF_PORT);
    }

    public GroovyNode elseBlock(double xPos, double yPos) {
        var block = factory.elseBlock();
        return new GroovyNode(block, xPos, yPos, 200, 100, Color.YELLOW, ELSE_PORT);
    }

    public GroovyNode assignBlock(double xPos, double yPos) {
        var block = factory.assignBlock();
        return new GroovyNode(block, xPos, yPos, 200, 100, Color.LIGHTPINK, ASSIGN_PORT);
    }

    public Try<GroovyNode> booleanBlock(double xPos, double yPos, String value) {
        var block = factory.booleanBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 200, 100, Color.GREEN, List.of()));
    }

    public Try<GroovyNode> integerBlock(double xPos, double yPos, String value) {
        var block = factory.integerBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 200, 100, Color.GREEN, List.of()));
    }

    public Try<GroovyNode> doubleBlock(double xPos, double yPos, String value) {
        var block = factory.doubleBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 200, 100, Color.GREEN, List.of()));
    }

    public GroovyNode stringBlock(double xPos, double yPos, String value) {
        var block = factory.stringBlock(value);
        return new GroovyNode(block, xPos, yPos, 200, 100, Color.GREEN, List.of());
    }

    public Try<GroovyNode> listBlock(double xPos, double yPos, String value) {
        var block = factory.listBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 200, 100, Color.GREEN, List.of()));
    }

    public Try<GroovyNode> mapBlock(double xPos, double yPos, String value) {
        var block = factory.mapBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 200, 100, Color.GREEN, List.of()));
    }

    public Try<GroovyNode> refBlock(double xPos, double yPos, String value) {
        var block = factory.refBlock(value);
        return block.map(e -> new GroovyNode(e, xPos, yPos, 200, 100, Color.GREEN, List.of()));
    }

    public GroovyNode unaryBlock(double xPos, double yPos, String op) {
        var block = factory.unaryBlock(op);
        return new GroovyNode(block, xPos, yPos, 100, 100, Color.MAGENTA, UNARY_PORT);
    }

    public GroovyNode binaryBlock(double xPos, double yPos, String op) {
        var block = factory.unaryBlock(op);
        return new GroovyNode(block, xPos, yPos, 100, 100, Color.MAGENTA, BINARY_PORT);
    }
}
