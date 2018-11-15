package groovy.graph.blocks.small_factory;

import essentials.GameData;
import groovy.api.Try;
import groovy.graph.blocks.core.LiteralBlock;

import java.util.Arrays;
import java.util.List;

/**
 *  Name explains everything!
 */
public class LiteralFactory {
    public static Try<LiteralBlock> booleanBlock(String value) {
        return Try.apply(() -> Boolean.parseBoolean(value.trim()))
                  .map(i -> new LiteralBlock(i.toString(), "Boolean"));
    }

    public static Try<LiteralBlock> integerBlock(String value) {
        return Try.apply(() -> Integer.parseInt(value.trim()))
                  .map(i -> new LiteralBlock(i.toString(), "Integer"));
    }

    public static Try<LiteralBlock> doubleBlock(String value) {
        return Try.apply(() -> Double.parseDouble(value.trim()))
                  .map(i -> new LiteralBlock(i.toString(), "Double"));
    }

    public static LiteralBlock stringBlock(String value) {
        return new LiteralBlock("\""+value+"\"", "String");
    }


    public static Try<LiteralBlock> listBlock(String value) {
        return Try.apply(() -> parseList(value.trim()))
                  .map(i -> new LiteralBlock(i.toString(), "List"));
    }

    private static List<String> parseList(String lst) throws ListParseException {
        if(lst.startsWith("[") && lst.endsWith("]")) {
            var elms = lst.substring(1, lst.length()-1);
            return Arrays.asList(elms.trim().split("\\s*,\\s*")); // TODO: Make this less naive
        } else throw new ListParseException(lst);
    }

    public static Try<LiteralBlock> refBlock(String value) {
        return Try.apply(() -> validateReference(value.trim()))
                  .map(ref -> new LiteralBlock(ref, "Ref"));
    }

    private static String validateReference(String ref) throws ReferenceParseException {
        return ref; // TODO: need to validate... but what?
    }


    public static Try<LiteralBlock> mapBlock(String value) {
        return Try.apply(() -> checkMap(value.trim())).map(m -> new LiteralBlock(m, "Map"));
    }

    /**
     *  Map in Groovy is just... a list with tuples
     *  ex) [ 1:"ha", 2:"ho" ]
     */
    private static String checkMap(String mapWannabe) throws MapParseException {
        try {
            var list = parseList(mapWannabe);
            for(var e : list) {
                var p = e.split(":");
                if(p.length != 2) throw new MapParseException(mapWannabe);
            } return mapWannabe;
        } catch(ListParseException e) { throw new MapParseException(mapWannabe); }
    }
}

