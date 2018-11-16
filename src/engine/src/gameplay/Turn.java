package gameplay;

import java.util.Set;

public class Turn {
    private Phase myCurrentPhase;
    private Player myCurrentPlayer;
    private Set<GlobalData> myGlobalData;

    public void setPhase(Phase phase){
        myCurrentPhase = phase;
    }

    public void startPhase(){
        myCurrentPhase.startTraversal();
    }

    public void endGame(){
        // end the game
    }

    public <T> T getData(String key){
        for (GlobalData gb : myGlobalData){
            if (gb.getKeySet().contains(key)){
                Class valueType = gb.get(key).getClass();
                return (T) gb.get(key); // TODO: FIX THIS
            }
        }
        return null;
    }
}
