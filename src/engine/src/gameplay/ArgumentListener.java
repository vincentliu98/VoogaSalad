package gameplay;

import javafx.event.Event;

public interface ArgumentListener {
    int DONT_PASS = -1;
    /**
     *  Whenever an event occurs, the listeners(Edges)
     *  check the validity of the transition;
     *  If it IS valid, the method returns the id of the node that we're transitioning into
     *  If it's not, it returns -1
     */
    int trigger(Event event);
}