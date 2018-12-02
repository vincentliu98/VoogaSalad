package graphUI.groovy;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IconLoader {
    private static final int ICONS_IN_ROW = 9;

    private static List<Node> loadIcons(
        String category,
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
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
            var icon = draggableIcon.apply(new Image(
                IconLoader.class.getClassLoader().getResourceAsStream(key + ".png"))).apply(key).apply(val);
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
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        return loadIcons("control", draggableIcon);
    }

    public static List<Node> loadBinaries(
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        return loadIcons("binary", draggableIcon);
    }

    public static List<Node> loadLiterals(
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        return loadIcons("literal", draggableIcon);
    }

    public static List<Node> loadFunctions(
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        return loadIcons("function", draggableIcon);
    }

    public static List<Node> loadGameMethods(
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        try {
            var c = Class.forName("gameplay.GameMethods");
            var list = new ArrayList<Node>();
            list.add(new Text("GameMethods"));
            list.add(new Separator());
            for(var method : c.getDeclaredMethods()) {
                if(method.getName().contains("lambda")) continue; // no lambdas!

                var icon = draggableIcon.apply(new Image(
                    IconLoader.class.getClassLoader().getResourceAsStream("AutoGen.png")))
                                        .apply("GameMethods."+method.getName()+"("+method.getParameters().length+")")
                                        .apply(false);
                var label = new Label(
                    method.getReturnType().getSimpleName() + " " +
                    method.getName() +
                    formatParameters(method.getParameters())
                );
                list.add(new HBox(icon, label));
            } return list;
        } catch (ClassNotFoundException ignored) { return List.of(); }
    }

    private static String formatParameters(Parameter[] params) {
        var paramTypes =  Arrays.stream(params).map(p -> p.getType().getSimpleName()).collect(Collectors.toList());
        return "(" + String.join(",", paramTypes) + ")";
    }
}
