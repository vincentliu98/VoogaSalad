import authoring.AuthoringTools;
import gameObjects.GameObjectInstance;
import grids.PointImpl;
import phase.api.GameEvent;

import java.util.ArrayList;

public class SerializationTest {
    public static void main(String[] args) throws Throwable {
        var authTools = new AuthoringTools();

        // --------------- ENTITY/TILE ------------- //

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

        // -------------- PHASE ------------- //

        var phaseDB = authTools.phaseDB();

        var graph = phaseDB.createGraph("A").get(null);

        var node2 = phaseDB.createPhase("b").get(null);
        var node3 = phaseDB.createPhase("c").get(null);
        var node4 = phaseDB.createPhase("d").get(null);

        var edge12 = phaseDB.createTransition(graph.source(), GameEvent.mouseClick(), node2);
        var edge23 = phaseDB.createTransition(node2, GameEvent.mouseClick(), node3);
        var edge24 = phaseDB.createTransition(node2, GameEvent.mouseClick(), node4);

        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);

        graph.addEdge(edge12);
        graph.addEdge(edge23);
        graph.addEdge(edge24);

        var edge12graph = edge12.guard();
        var edge23graph = edge23.guard();
        var edge24graph = edge24.guard();

        System.out.println(authTools.toEngineXML());
    }
}
