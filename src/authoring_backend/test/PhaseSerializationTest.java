import authoring.AuthoringTools;
import conversion.engine.SerializerForEngine;
import phase.api.GameEvent;

public class PhaseSerializationTest {
    public static void main(String[] args) throws Throwable {
        var authTools = new AuthoringTools(5, 5);
        var phaseDB = authTools.phaseDB();

        var graph = phaseDB.createPhaseGraph("A").get(null);

        var node2 = phaseDB.createPhase(0, 0, "b").get(null);
        var node3 = phaseDB.createPhase(0, 0, "c").get(null);
        var node4 = phaseDB.createPhase(0, 0, "d").get(null);

        var edge12 = phaseDB.createTransition(graph.source(), GameEvent.mouseClick(), node2);
        var edge23 = phaseDB.createTransition(node2, GameEvent.mouseClick(), node3);
        var edge24 = phaseDB.createTransition(node2, GameEvent.mouseClick(), node4);

        graph.addNode(node2);
        graph.addNode(node3);
        graph.addNode(node4);

        graph.addEdge(edge12);
        graph.addEdge(edge23);
        graph.addEdge(edge24);

        System.out.println(SerializerForEngine.gen().toXML(phaseDB));
    }
}
