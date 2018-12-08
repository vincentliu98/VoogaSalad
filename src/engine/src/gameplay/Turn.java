package gameplay;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.List;
import java.util.Optional;

public class Turn {
    private String myCurrentPhaseName;
    private int playerIdx;
    private List<Integer> playersOrder;

    public Turn(String phaseID, List<Integer> playersOrder){
        myCurrentPhaseName = phaseID;
        this.playersOrder = playersOrder;
        this.playerIdx = 0;
    }

    public int getCurrentPlayerID(){ return playersOrder.get(playerIdx); }
    public void setPhase(String phaseName){ myCurrentPhaseName = phaseName; }
    public int nextPlayerID() {
        return playersOrder.get((playerIdx+1)%playersOrder.size());
    }
    public int toNextPlayer() { // returns id of the next player after changing current player to that player
        playerIdx = (++playerIdx)%playersOrder.size();
        return playersOrder.get(playerIdx);
    }
    public void setPlayerOrder(List<Integer> newOrder){
        playersOrder = newOrder;
        playerIdx = 0;
    }

    public void setCurrentPlayer(int playerID) {
        playerIdx = playersOrder.indexOf(playerID);
    }

    public void startPhase(){ GameData.getPhase(myCurrentPhaseName).startTraversal(); }

    public void endGame(String message){
        // end the game
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(message);
        alert.setContentText("Restart?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            GameData.restartGame();
        } else { }
    }
}