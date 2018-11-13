package groovy.graph.blocks.factory;

import groovy.api.Try;
import groovy.graph.blocks.core.LiteralBlock;

import java.util.Arrays;
import java.util.List;

/**
 *  Name explains everything!
 */
public class LiteralFactory {
    public static Try<LiteralBlock> integerBlock(String value) {
        return Try.apply(() -> Integer.parseInt(value))
                  .map(i -> new LiteralBlock(i.toString()));
    }

    public static Try<LiteralBlock> doubleBlock(String value) {
        return Try.apply(() -> Double.parseDouble(value))
                  .map(i -> new LiteralBlock(i.toString()));
    }

    public static Try<LiteralBlock> stringBlock(String value) {
        return Try.success(new LiteralBlock(value));
    }


    public static Try<LiteralBlock> listBlock(String value) {
        return Try.apply(() -> parseList(value))
                  .map(i -> new LiteralBlock(i.toString()));
    }

    private static List<String> parseList(String listWannabe) throws ListParseException {
        var trimmed = listWannabe.trim();
        if(trimmed.startsWith("[") && trimmed.endsWith("]")) {
            var elms = trimmed.substring(1, trimmed.length()-1);
            return Arrays.asList(elms.trim().split("\\s*,\\s*")); // TODO: Make this less naive
        } else throw new ListParseException(listWannabe);
    }

    public static Try<LiteralBlock> mapBlock(String value) {
        return Try.apply(() -> checkMap(value)).map(LiteralBlock::new);
    }

    /**
     *  Map in Groovy is just... a list with tuples
     *  ex) [ 1:"ha", 2:"ho" ]
     */
    private static String checkMap(String mapWannabe) throws MapParseException {
        try {
            var list = parseList(mapWannabe.trim());
            for(var e : list) {
                var p = e.split(":");
                if(p.length != 2) throw new MapParseException(mapWannabe);
            } return mapWannabe;
        } catch(ListParseException e) { throw new MapParseException(mapWannabe); }
    }
}
