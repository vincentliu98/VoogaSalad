package groovy.graph.blocks.core;

import utils.Try;

/**
 *  Name explains everything!
 */
public class LiteralTypeChecker {
    public static String getType(String value) {
        if(isBoolean(value)) return "Boolean";
        else if(isInteger(value)) return "Integer";
        else if(isDouble(value)) return "Double";
        else if(isList(value)) return "List";
        else if(isMap(value)) return "Map";
        else return "String";
    }

    private static boolean isBoolean(String value) { return Try.apply(() -> Boolean.parseBoolean(value)).isSuccess(); }
    private static boolean isInteger(String value) { return Try.apply(() -> Integer.parseInt(value)).isSuccess(); }
    private static boolean isDouble(String value) { return Try.apply(() -> Double.parseDouble(value)).isSuccess(); }

    private static boolean isList(String value) {
        var trimmed = value.trim();
        return trimmed.startsWith("[") && trimmed.endsWith("]"); // TODO: make this less naive
    }

    /**
     *  Map in Groovy is just... a list with tuples
     *  ex) [ 1:"ha", 2:"ho" ]
     */
    private static boolean isMap(String value) {
        if(!isList(value)) return false;

        var list = value.substring(1, value.length()-1).split("\\s*,\\s*");
        for(var e : list) {
            var p = e.split(":");
            if(p.length != 2) return false;
        } return true;
    }
}
