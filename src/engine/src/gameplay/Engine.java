package gameplay;

import java.util.Set;

public class Engine {
    Set<gameplay.Phase> myPhases;
    Set<gameplay.Player> myPlayers;
    // also contains global data
    gameplay.Turn myTurn;

    public Engine(Set<gameplay.Phase> phases, Set<gameplay.Player> players, gameplay.GlobalData globalData){
        this.myPhases = phases;
        this.myPlayers = players;
    }

    public void play(gameplay.Phase currentPhase, gameplay.Player currentPlayer){
        // initialize Turn class here
    }
}