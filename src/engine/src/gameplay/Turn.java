package gameplay;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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

    public void endGame(int winnerID){
        // end the game
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(String.format("Player %d has won!", winnerID));
        alert.setContentText("Restart?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

        } else { }
    }
}