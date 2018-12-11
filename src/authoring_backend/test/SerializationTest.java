import authoring.AuthoringTools;
import gameObjects.gameObject.GameObjectInstance;
import grids.PointImpl;
import groovy.api.Ports;
import javafx.scene.input.KeyCode;
import phase.api.GameEvent;

import java.util.ArrayList;

public class SerializationTest {
    public static void main(String[] args) throws Throwable {
        var authTools = new AuthoringTools(5, 5);

        // --------------- ENTITY/TILE ------------- //

        var db = authTools.entityDB();

        var playerClassA = db.createPlayerClass("classA");
        var playerClassB = db.createPlayerClass("classB");

        var box = db.createTileClass("box");
        box.getImagePathList().add("square.png");
        var boxes = new ArrayList<GameObjectInstance>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boxes.add(box.createInstance(new PointImpl(i, j)));
            }
        }

        var swordmanClass = db.createEntityClass("swordman");
        swordmanClass.setWidth(1);
        swordmanClass.setHeight(1);
        swordmanClass.getPropertiesMap().put("hp", "5");
        swordmanClass.getPropertiesMap().put("attackRange", "1");
        swordmanClass.getPropertiesMap().put("dmg", "3");
        swordmanClass.addImagePath("swordman1.png");
        swordmanClass.addImagePath("swordman2.png");
        swordmanClass.addImagePath("swordman3.png");
        swordmanClass.addImagePath("swordman4.png");
        swordmanClass.addImagePath("swordman5.png");

        var bowmanClass = db.createEntityClass("bowman");
        bowmanClass.setWidth(2);
        bowmanClass.setHeight(2);
        bowmanClass.getPropertiesMap().put("hp", "5");
        bowmanClass.getPropertiesMap().put("attackRange", "3");
        bowmanClass.getPropertiesMap().put("dmg", "1");
        bowmanClass.addImagePath("bowman1.png");
        bowmanClass.addImagePath("bowman2.png");
        bowmanClass.addImagePath("bowman3.png");
        bowmanClass.addImagePath("bowman4.png");
        bowmanClass.addImagePath("bowman5.png");

        playerClassA.addGameObjectInstances(swordmanClass.createInstance(new PointImpl(0, 0)));
        playerClassA.addGameObjectInstances(swordmanClass.createInstance(new PointImpl(0, 1)));
        playerClassB.addGameObjectInstances(bowmanClass.createInstance(new PointImpl(1, 3)));
        playerClassB.addGameObjectInstances(bowmanClass.createInstance(new PointImpl(3, 3)));


        // -------------- PHASE ------------- //

        var phaseDB = authTools.phaseDB();
        var factory = authTools.factory();

        var hbSource = phaseDB.winCondition().source();
        var hbScript = factory.rawBlock(0, 0,
                "if(GameMethods.hasNoEntities(GameMethods.getCurrentPlayerName())) {" +
                        "   GameMethods.endGame(GameMethods.getCurrentPlayerName() + ' LOST!')" +
                        "}"
        );
        phaseDB.winCondition().addEdge(factory.createEdge(hbSource, Ports.FLOW_OUT, hbScript));


        var graph = phaseDB.createPhaseGraph("A").get(null);
        var node2 = phaseDB.createPhase(0, 0, "b").get(null);
        var node3 = phaseDB.createPhase(0, 0, "c").get(null);
        var node4 = phaseDB.createPhase(0, 0, "d").get(null);

        var edge21 = phaseDB.createTransition(node2, GameEvent.keyPress(KeyCode.ESCAPE), graph.source());
        var edge12 = phaseDB.createTransition(graph.source(), GameEvent.mouseClick(), node2);
        var edge23 = phaseDB.createTransition(node2, GameEvent.mouseClick(), node3);
        var edge24 = phaseDB.createTransition(node2, GameEvent.mouseClick(), node4);

        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);

        graph.addEdge(edge21);
        graph.addEdge(edge12);
        graph.addEdge(edge23);
        graph.addEdge(edge24);

        var edge21graph = edge21.guard();
        var noGuard = factory.rawBlock(0, 0, "GameMethods.$return(true)");
        edge21graph.addNode(noGuard);
        edge21graph.addEdge(factory.createEdge(edge21graph.source(), Ports.FLOW_OUT, noGuard));

        var edge12graph = edge12.guard();

        // this is properly done, but I'm gonna cheat afterwards
        var n1 = factory.ifBlock(0, 0);
        var n2 = factory.functionBlock(0, 0, "GameMethods.isEntity", null);
        var n3 = factory.refBlock(0, 0, "$clicked").get();
        var n4 = factory.binaryBlock(0, 0, "&&");
        var n5 = factory.functionBlock(0, 0, "GameMethods.getCurrentPlayer().isMyEntity", null);
        var n6 = factory.assignBlock(0, 0);
        var n7 = factory.refBlock(0, 0, "$return").get();
        var n8 = factory.booleanBlock(0, 0, "true").get();
        var n9 = factory.elseBlock(0, 0);
        var n10 = factory.assignBlock(0, 0);
        var n11 = factory.booleanBlock(0, 0, "false").get();
        edge12graph.addNode(n1);
        edge12graph.addNode(n2);
        edge12graph.addNode(n3);
        edge12graph.addNode(n4);
        edge12graph.addNode(n5);
        edge12graph.addNode(n6);
        edge12graph.addNode(n7);
        edge12graph.addNode(n8);
        edge12graph.addNode(n9);
        edge12graph.addNode(n10);
        edge12graph.addNode(n11);

        edge12graph.addEdge(factory.createEdge(edge12graph.source(), Ports.FLOW_OUT, n1));
        edge12graph.addEdge(factory.createEdge(n1, Ports.IF_PREDICATE, n4));
        edge12graph.addEdge(factory.createEdge(n4, Ports.A, n2));
        edge12graph.addEdge(factory.createEdge(n4, Ports.B, n5));
        edge12graph.addEdge(factory.createEdge(n2, Ports.A, n3));
        edge12graph.addEdge(factory.createEdge(n5, Ports.A, n3));
        edge12graph.addEdge(factory.createEdge(n1, Ports.IF_BODY, n6));
        edge12graph.addEdge(factory.createEdge(n6, Ports.ASSIGN_LHS, n7));
        edge12graph.addEdge(factory.createEdge(n6, Ports.ASSIGN_RHS, n8));
        edge12graph.addEdge(factory.createEdge(n1, Ports.FLOW_OUT, n9));
        edge12graph.addEdge(factory.createEdge(n9, Ports.IF_BODY, n10));
        edge12graph.addEdge(factory.createEdge(n10, Ports.ASSIGN_LHS, n7));
        edge12graph.addEdge(factory.createEdge(n10, Ports.ASSIGN_RHS, n11));

        var edge23graph = edge23.guard();
        var n23 = factory.rawBlock(0, 0, "GameMethods.$return(GameMethods.isTile($clicked) && GameMethods.distance($clicked, selected) <= 1)");
        edge23graph.addNode(n23);
        edge23graph.addEdge(factory.createEdge(edge23graph.source(), Ports.FLOW_OUT, n23));

        var edge24graph = edge24.guard();
        var n24 = factory.rawBlock(0, 0, "GameMethods.$return(GameMethods.isEntity($clicked) && !GameMethods.getCurrentPlayer().isMyEntity($clicked) && GameMethods.distance($clicked, selected) <= selected.props.attackRange)");
        edge24graph.addNode(n24);
        edge24graph.addEdge(factory.createEdge(edge24graph.source(), Ports.FLOW_OUT, n24));

        var exec2 = factory.rawBlock(0, 0, "selected = $clicked");
        node2.exec().addNode(exec2);
        node2.exec().addEdge(factory.createEdge(node2.exec().source(), Ports.FLOW_OUT, exec2));

        var exec3 = factory.rawBlock(0, 0,
                "GameMethods.moveEntity(selected, $clicked)\n" +
                        "GameMethods.toNextPlayer()\n" +
                        "GameMethods.$goto('A')"
        );
        node3.exec().addNode(exec3);
        node3.exec().addEdge(factory.createEdge(node3.exec().source(), Ports.FLOW_OUT, exec3));


        var exec4 = factory.rawBlock(0, 0,
                "$clicked.props.hp = $clicked.props.hp - selected.props.dmg\n" +
                        "if($clicked.props.hp <= 0) { GameMethods.removeEntity($clicked) }\n" +
                        "GameMethods.toNextPlayer()\n" +
                        "GameMethods.$goto('A')"
        );
        node4.exec().addNode(exec4);
        node4.exec().addEdge(factory.createEdge(node4.exec().source(), Ports.FLOW_OUT, exec4));

        System.out.println(authTools.toEngineXML());
    }
}