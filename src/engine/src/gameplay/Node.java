package gameplay;


public class Node {
    private String myPhaseName;
    private String myName;
    private String myExecution; // Groovy code

    public String getName() {
        return myName;
    }

    public void execute() {
        System.out.printf("On Node %s\n", myName);
        GameData.clearArgumentListeners(); // clear previous listeners
        GameData.getEdges()
                .stream()
                .filter(e -> e.getMyStartNodeName().equals(myName))
                .forEach(GameData::addArgumentListener); // add new ones
        if (myExecution.isEmpty()) return;
        try {
            GameData.shell().evaluate(myExecution);
            GameData.shell().evaluate(GameData.WIN_CONDITION);
            System.out.println(GameMethods.hasNoEntities(GameMethods.getCurrentPlayerName()));
            System.out.println(GameMethods.getCurrentPlayer().getMyEntities());
            GameData.updateViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}