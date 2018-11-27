import authoring.AuthoringTools;
import com.thoughtworks.xstream.XStream;
import conversion.engine.SerializerForEngine;
import gameObjects.GameObjectInstance;
import grids.PointImpl;

import java.util.ArrayList;

public class GameObjectSerializationTest {
    public static void main(String[] args) {
        XStream serializer = SerializerForEngine.gen();

        var authTools = new AuthoringTools();
        var db = authTools.entityDB();

        var box = db.createTileClass("box");
        box.getImagePathList().add("square.png");
        var boxes = new ArrayList<GameObjectInstance>();
        for(int i = 0 ; i < 5 ; i ++) {
            for(int j = 0 ; j < 5 ; j ++) {
                boxes.add(box.createInstance(new PointImpl(i, j)));
            }
        }

        var swordmanClass = db.createEntityClass("swordman");
        swordmanClass.getPropertiesMap().put("hp", "5");
        swordmanClass.getPropertiesMap().put("attackRange", "1");
        swordmanClass.getPropertiesMap().put("dmg", "3");
        swordmanClass.addImagePath("swordman1.png");
        swordmanClass.addImagePath("swordman2.png");
        swordmanClass.addImagePath("swordman3.png");
        swordmanClass.addImagePath("swordman4.png");
        swordmanClass.addImagePath("swordman5.png");
        swordmanClass.setImageSelector("$return = $this.props.hp-1");

        var bowmanClass = db.createEntityClass("bowman");
        bowmanClass.getPropertiesMap().put("hp", "5");
        bowmanClass.getPropertiesMap().put("attackRange", "3");
        bowmanClass.getPropertiesMap().put("dmg", "1");
        bowmanClass.addImagePath("bowman1.png");
        bowmanClass.addImagePath("bowman2.png");
        bowmanClass.addImagePath("bowman3.png");
        bowmanClass.addImagePath("bowman4.png");
        bowmanClass.addImagePath("bowman5.png");
        bowmanClass.setImageSelector("$return = $this.props.hp-1");

        swordmanClass.createInstance(boxes.get(0).getInstanceId().get());
        swordmanClass.createInstance(boxes.get(1).getInstanceId().get());
        bowmanClass.createInstance(boxes.get(23).getInstanceId().get());
        bowmanClass.createInstance(boxes.get(24).getInstanceId().get());

        System.out.println(serializer.toXML(db).replaceAll("&lt;", "<").replaceAll("&gt;", ">"));
    }
}
