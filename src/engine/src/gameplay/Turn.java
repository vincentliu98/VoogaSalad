package gameplay;

public class Turn {
    private int myCurrentPhaseID;
    private int myCurrentPlayerID;

    public Turn(int phaseID, int playerID){
        myCurrentPhaseID = phaseID;
        myCurrentPlayerID = playerID;
    }

    public int getCurrentPlayerID(){ return myCurrentPlayerID; }
    public void setPhase(int phaseID){ myCurrentPhaseID = phaseID; }
    public void setPlayer(int playerID){ myCurrentPlayerID = playerID; }
    public void startPhase(){
        System.out.println(myCurrentPhaseID);
        System.out.println(GameData.getPhase(myCurrentPhaseID));
        GameData.getPhase(myCurrentPhaseID).startTraversal();
    }

    public void endGame(){
        // end the game
    }
}