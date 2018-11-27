import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.xml.DomDriver;
import grids.Point;
import grids.PointImpl;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import phase.api.GameEvent;

import java.util.*;

public class XStreamTest {
    public static void main(String[] args) {
        XStream xstream = new XStream(new DomDriver());
        var e = (Entity) xstream.fromXML(XStreamTest.class.getClassLoader().getResourceAsStream("test_data.xml"));

        var binding = new Binding();
        binding.setVariable("entity", e);
        GroovyShell shell = new GroovyShell(binding);

        e.printList();
        e.properties().put("ha", "he");
        e.properties().put("he", new Entity());
        System.out.println(e.view());
        System.out.println(e.properties());

        System.out.println(xstream.toXML(e));

        shell.evaluate("entity.properties().put('hi', 'ho')");
        shell.evaluate("$ret = entity.properties().get('he').properties()");
        System.out.println(((Entity) shell.getVariable("entity")).properties());
        System.out.println(shell.getVariable("$ret"));
        shell.evaluate("$ret.put('hello', 1)");
        System.out.println(((Entity) e.properties().get("he")).properties().get("hello"));
        System.out.println(shell.evaluate("entity.properties().get('he').properties().get('hello')-2"));
        Point p = new PointImpl(1,2);
        System.out.println(xstream.toXML(p));
    }

    public static abstract class Property {
        protected Map<String, Object> properties;
    }

    public static class Entity extends Property {
        private List<String> images;
        private Set<Integer> ids;
        private GameEvent trigger;

        public Entity() {
            properties = new HashMap<>();
            ids = new HashSet<>();
            properties.put("ha", 1);
            ids.add(1);
            trigger = GameEvent.mouseClick();
        }

        @XStreamOmitField
        private transient ImageView view;

        public void printList() {
            System.out.println(images);
        }

        public ImageView view() {
            return view;
        }

        public Map<String, Object> properties() { return properties; }
    }
}
