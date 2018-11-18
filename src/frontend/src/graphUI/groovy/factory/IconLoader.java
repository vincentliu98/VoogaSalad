package graphUI.groovy.factory;

import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class IconLoader {
    private static final int ICONS_IN_ROW = 5;

    private static List<Node> loadIcons(
        String category,
        Function<Image, Function<String, Function<Boolean, ImageView>>> draggableIcon
    ) {
        try {
            var ret = new ArrayList<Node>();
            ret.add(new Text(category));
            ret.add(new Separator());
            var props = new Properties();
            props.load(IconLoader.class.getClassLoader().getResourceAsStream(category+".properties"));
            var it = props.keys();
            var lst = new ArrayList<Node>();
            while(it.hasMoreElements()) {
                if(lst.size() < ICONS_IN_ROW) {
                    var key = it.nextElement().toString();
                    var val = Boolean.parseBoolean(props.get(key).toString());
                    System.out.println(key);
                    lst.add(draggableIcon.apply(new Image(
                        IconLoader.class.getClassLoader().getResourceAsStream(key + ".png"))).apply(key).apply(val));
                } else {
                    var hbox = new HBox();
                    lst.forEach(n -> hbox.getChildren().add(n));
                    ret.add(hbox);
                    lst.clear();
                }
            }
            if(lst.size() > 0) {
                var hbox = new HBox();
                lst.forEach(n -> hbox.getChildren().add(n));
                ret.add(hbox);
                lst.clear();
            }
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
            return List.of();
        }
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
}
