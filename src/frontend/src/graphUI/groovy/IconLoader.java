package graphUI.groovy;

import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IconLoader {
    private static final int ICONS_IN_ROW = 5;

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
            lst.add(draggableIcon.apply(new Image(
                IconLoader.class.getClassLoader().getResourceAsStream(key + ".png"))).apply(key).apply(val));
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

    public static List<Node> loadUnaries(
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        return loadIcons("unary", draggableIcon);
    }
}
