package gameplay;

import javafx.event.Event;
import phase.api.GameEvent;

public class Edge implements ArgumentListener {
    private String myPhaseName;
    private String myStartNodeName;
    private String myEndNodeName;
    private GameEvent myTrigger;
    private String myGuard; // Groovy code

    public Edge(String phaseName, String startNodeName, String endNodeName, String guard) {
        this.myPhaseName = phaseName;
        this.myStartNodeName = startNodeName;
        this.myEndNodeName = endNodeName;
        this.myGuard = guard;
    }

    private boolean checkValidity() {
        if (myGuard.isEmpty()) return false;
        try {
            System.out.printf("-------------trying guard %s -> %s ------------\n", myStartNodeName, myEndNodeName);
            System.out.println(myGuard);
            GameData.shell().evaluate(myGuard);
            System.out.println("result: " + GameData.shell().getVariable("$return"));
            return (boolean) GameData.shell().getVariable("$return");
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: throw an actual error
            return false;
        }
    }

    public String getMyStartNodeName() {
        return myStartNodeName;
    }

    @Override
    public String trigger(Event event) {
        if (myTrigger.matches(event) && checkValidity()) {
            return myEndNodeName;
        } else return DONT_PASS;
    }
}