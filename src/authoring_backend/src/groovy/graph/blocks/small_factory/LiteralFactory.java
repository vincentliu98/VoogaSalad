package groovy.graph.blocks.small_factory;

import authoringUtils.frontendUtils.Try;
import gameObjects.crud.GameObjectsCRUDInterface;
import groovy.graph.blocks.core.GroovyBlock;
import groovy.graph.blocks.core.LiteralBlock;

import java.util.Arrays;
import java.util.List;

/**
 * Name explains everything!
 */
public class LiteralFactory {
    public static Try<GroovyBlock<?>> booleanBlock(double x, double y, String value) {
        return Try.apply(() -> Boolean.parseBoolean(value.trim()))
                .map(i -> new LiteralBlock(x, y, i.toString(), "Boolean"));
    }

    public static Try<GroovyBlock<?>> integerBlock(double x, double y, String value) {
        return Try.apply(() -> Integer.parseInt(value.trim()))
                .map(i -> new LiteralBlock(x, y, i.toString(), "Integer"));
    }

    public static Try<GroovyBlock<?>> keyBlock(double x, double y, String value) {
        return Try.apply(() -> Integer.parseInt(value.trim()))
                .map(i -> new LiteralBlock(x, y, i.toString(), "KeyCode"));
    }

    public static Try<GroovyBlock<?>> doubleBlock(double x, double y, String value) {
        return Try.apply(() -> Double.parseDouble(value.trim()))
                .map(i -> new LiteralBlock(x, y, i.toString(), "Double"));
    }

    public static GroovyBlock<?> stringBlock(double x, double y, String value) {
        return new LiteralBlock(x, y, "\"" + value + "\"", "String");
    }


    public static Try<GroovyBlock<?>> listBlock(double x, double y, String value) {
        return Try.apply(() -> parseList(value.trim()))
                .map(i -> new LiteralBlock(x, y, i.toString(), "List"));
    }

    private static List<String> parseList(String lst) throws ListParseException {
        if (lst.startsWith("[") && lst.endsWith("]")) {
            var elms = lst.substring(1, lst.length() - 1);
            return Arrays.asList(elms.trim().split("\\s*,\\s*")); // TODO: Make this less naive
        } else throw new ListParseException(lst);
    }

    public static Try<GroovyBlock<?>> refBlock(double x, double y, String value, GameObjectsCRUDInterface entityDB) {
        return ValidationUtil.validateReference(value.trim(), entityDB)
                .map(ref -> new LiteralBlock(x, y, ref, "Ref"));
    }

    public static Try<GroovyBlock<?>> mapBlock(double x, double y, String value) {
        return Try.apply(() -> checkMap(value.trim())).map(m -> new LiteralBlock(x, y, m, "Map"));
    }

    /**
     * Map in Groovy is just... a list with tuples
     * ex) [ 1:"ha", 2:"ho" ]
     */
    private static String checkMap(String mapWannabe) throws MapParseException {
        try {
            var list = parseList(mapWannabe);
            for (var e : list) {
                var p = e.split(":");
                if (p.length != 2) throw new MapParseException(mapWannabe);
            }
            return mapWannabe;
        } catch (ListParseException e) {
            throw new MapParseException(mapWannabe);
        }
    }
}

