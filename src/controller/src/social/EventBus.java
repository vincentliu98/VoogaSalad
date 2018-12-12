package social;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBus { // pub-sub
    private static EventBus single_instance = null;
    private Map<EngineEvent, Set<Subscriber>> myEventMap;

    private EventBus() {
        myEventMap = new HashMap<>();
    }

    public static EventBus getInstance() {
        if (single_instance == null) {
            single_instance = new EventBus();
        }
        return single_instance;
    }

    public void addEvent(EngineEvent engineEvent) {
        if (myEventMap.containsKey(engineEvent)) return;
        myEventMap.put(engineEvent, new HashSet<>());
    }

    public void register(EngineEvent engineEvent, Subscriber subscriber) {
        addEvent(engineEvent); // does nothing if event already exists in map
        myEventMap.get(engineEvent).add(subscriber);
    }

    public void sendMessage(EngineEvent engineEvent, Object... args) {
        if (!myEventMap.containsKey(engineEvent)) return;
        for (Subscriber s : myEventMap.get(engineEvent)) {
            s.update(args);
        }
    }


}
