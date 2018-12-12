package graphUI.groovy;

import graphUI.groovy.DraggableGroovyIconFactory.DraggableGroovyIcon;
import groovy.api.Ports;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import utils.Functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

import static groovy.api.Ports.*;

/**
 * @author Inchan Hwang
 */
public class IconLoader {
    private static final int ICONS_IN_ROW = 9;

    private static List<Node> loadIcons(
            String category,
            Functions.Function3<Image, String, Boolean, DraggableGroovyIcon> draggableIcon
    ) {
        var ret = new ArrayList<Node>();
        ret.add(new Text(category));
        ret.add(new Separator());
        var reader = new BufferedReader(
                new InputStreamReader(
                        IconLoader.class.getClassLoader().getResourceAsStream(category + ".properties")
                )
        );
        var map = new LinkedHashMap<String, String>();
        for (var line : reader.lines().collect(Collectors.toList())) {
            if (line.contains("=")) {
                var s = line.split("=");
                map.put(s[0], s[1]);
            }
        }

        var lst = new ArrayList<Node>();
        for (var key : map.keySet()) {
            if (lst.size() == ICONS_IN_ROW) {
                var hbox = new HBox();
                lst.forEach(n -> hbox.getChildren().add(n));
                ret.add(hbox);
                lst.clear();
            }
            var val = Boolean.parseBoolean(map.get(key));
            var icon = draggableIcon.apply(
                    new Image(IconLoader.class.getClassLoader().getResourceAsStream(key + ".png")), key, val
            );
            var tooltip = new Tooltip(key);
            Tooltip.install(icon, tooltip);
            lst.add(icon);
        }

        if (lst.size() > 0) {
            var hbox = new HBox();
            lst.forEach(n -> hbox.getChildren().add(n));
            ret.add(hbox);
            lst.clear();
        }
        return ret;
    }

    public static List<Node> loadControls(
            Functions.Function3<Image, String, Boolean, DraggableGroovyIcon> draggableIcon
    ) {
        return loadIcons("control", draggableIcon);
    }

    public static List<Node> loadBinaries(
            Functions.Function3<Image, String, Boolean, DraggableGroovyIcon> draggableIcon
    ) {
        return loadIcons("binary", draggableIcon);
    }

    public static List<Node> loadLiterals(
            Functions.Function3<Image, String, Boolean, DraggableGroovyIcon> draggableIcon
    ) {
        return loadIcons("literal", draggableIcon);
    }

    public static List<Node> loadFunctions(
            Functions.Function3<Image, String, Boolean, DraggableGroovyIcon> draggableIcon
    ) {
        return loadIcons("function", draggableIcon);
    }

    public static List<Node> loadGameMethods(
            Functions.Function4<Image, String, Boolean, Map<Ports, String>, DraggableGroovyIcon> draggableIcon
    ) {
        try {
            var c = Class.forName("gameplay.GameMethods");
            var list = new ArrayList<Node>();
            list.add(new Text("GameMethods"));
            list.add(new Separator());
            for (var method : c.getDeclaredMethods()) {
                if (method.getName().contains("lambda")) continue; // no lambdas!

                var icon = draggableIcon.apply(
                        new Image(IconLoader.class.getClassLoader().getResourceAsStream("AutoGen.png")),
                        "GameMethods." + method.getName(),
                        false,
                        assignParameters(method.getParameters())
                );

                var label = new Label(
                        method.getReturnType().getSimpleName() + " " +
                                method.getName() +
                                formatParameters(method.getParameters())
                );
                list.add(new HBox(icon, label));
            }
            return list;
        } catch (ClassNotFoundException ignored) {
            return List.of();
        }
    }

    private static Map<Ports, String> assignParameters(Parameter[] params) {
        var lst = List.of(A, B, C, D, E);
        var map = new HashMap<Ports, String>();
        for (int i = 0; i < params.length; i++) {
            map.put(lst.get(i), params[i].getType().getSimpleName() + " " + params[i].getName());
        }
        return map;
    }

    private static String formatParameters(Parameter[] params) {
        var paramTypes = Arrays.stream(params)
                .map(p -> p.getType().getSimpleName() + " " + p.getName())
                .collect(Collectors.toList());
        return "(" + String.join(",", paramTypes) + ")";
    }
}
