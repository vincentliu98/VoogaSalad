import authoring.AuthoringTools;
import gameObjects.GameObjectInstance;
import grids.PointImpl;
import groovy.api.Ports;
import phase.api.GameEvent;

import java.util.ArrayList;

public class SerializationTest {
    public static void main(String[] args) throws Throwable {
        var authTools = new AuthoringTools(5, 5);

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
        var factory = authTools.factory();

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

//      if(GameData.isEntity($clicked) &amp;&amp; GameData.getCurrentPlayer().isMyEntity($clicked)) { $return = true }
//          else { $return = false }
        var n1 = factory.ifBlock();
        var n2 = factory.functionBlock("GameData.isEntity");
        var n3 = factory.refBlock("$clicked").get();
        var n4 = factory.binaryBlock("&&");
        var n5 = factory.functionBlock("GameData.getCurrentPlayer().isMyEntity");

        var e14 = factory.createEdge(n1, Ports.IF_PREDICATE, n4);
        var e42 = factory.createEdge(n4, Ports.A, n2);
        var e45 = factory.createEdge(n4, Ports.B, n5);
        var e23 = factory.createEdge(n2, Ports.A, n3);
        var e53 = factory.createEdge(n5, Ports.A, n3);



        edge12graph.addNode();
        edge12graph.addNode();


//        if(GameData.isTile($clicked) &amp;&amp; GameData.distance($clicked, selected) &lt;= 1 &amp;&amp; GameData.hasNoEntities($clicked)) { $return = true }
//            else { $return = false }
//
//
//        if(GameData.isEntity($clicked) &amp;&amp; !GameData.getCurrentPlayer().isMyEntity($clicked) &amp;&amp; GameData.distance($clicked, selected) &lt;= selected.props.attackRange ) { $return = true }
//            else { $return = false }
//
//        selected = $clicked
//
//        GameData.moveEntity(selected, $clicked)
//        GameData.setCurrentPlayerID(1-GameData.getCurrentPlayerID())
//        GameData.goTo(1)
//
//        $clicked.props.hp = $clicked.props.hp - selected.props.dmg
//        if($clicked.props.hp &lt;= 0) {
//            GameData.removeEntity($clicked)
//        }
//        GameData.setCurrentPlayerID(1-GameData.getCurrentPlayerID())
//        GameData.goTo(1)
        System.out.println(authTools.toEngineXML());
    }
}
