package gameplay;


public class Node {
    private int myPhaseID;
    private int myID;
    private String myExecution; // Groovy code

    public int getID(){ return myID; }

    public void execute(){
        GameData.clearArgumentListeners(); // clear previous listeners
        GameData.getEdges()
                .stream()
                .filter(e -> e.getMyStartNodeID() == myID)
                .forEach(GameData::addArgumentListener); // add new ones

        if(myExecution.isEmpty()) return;
        try{
            GameData.shell().evaluate(myExecution);
            GameData.updateViews();
        } catch (Exception e){ e.printStackTrace(); }
    }
}