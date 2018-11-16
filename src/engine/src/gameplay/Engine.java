package gameplay;

import java.util.Set;

public class Engine {
    Set<Phase> myPhases;
    Set<Player> myPlayers;
    // also contains global data
    Turn myTurn;

    public Engine(Set<Phase> phases, Set<Player> players, GlobalData globalData){
        this.myPhases = phases;
        this.myPlayers = players;
    }

    public void play(Phase currentPhase, Player currentPlayer){
        // initialize Turn class here
    }
}