package phase.api;

import graph.SimpleNode;

import java.util.UUID;

public class Phase extends SimpleNode {
    private UUID uuid;
    public Phase() {
        super();
        uuid = UUID.randomUUID();
    }
    public UUID id() { return uuid; }
}
