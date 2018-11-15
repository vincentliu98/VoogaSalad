package gameplay;

import java.util.Set;

public class Turn {
    private Phase currentPhase;
    private Player currentPlayer;

    public void setPhase(Phase phase){
        currentPhase = phase;
    }

    public void startPhase(){
        currentPhase.startTraversal();
    }

    public void endGame(){
        // end the game
    }
}
