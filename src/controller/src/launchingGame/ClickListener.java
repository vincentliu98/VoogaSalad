package launchingGame;

public interface ClickListener {

    /**
     * This method receives the object that was clicked on and the title of the event
     *
     * @param name The title of the event
     * @param obj  The object that was clicked on
     */
    void receiveArgument(String name, Object obj);
}
