package gameObjects.turn;

import javafx.beans.property.*;

public class SimpleTurn implements Turn {
    private SimpleStringProperty myPhaseName;
    private SimpleIntegerProperty myPlayerId;

    public SimpleTurn(String phaseName) {
        myPhaseName = new SimpleStringProperty(phaseName);
        myPlayerId = new SimpleIntegerProperty();
    }

    @Override
    public SimpleStringProperty getPhaseName() {
        return myPhaseName;
    }

    @Override
    public void setPhaseName(String newPhaseName) {
        myPhaseName.setValue(newPhaseName);
    }


    @Override
    public SimpleIntegerProperty getPlayerId() {
        return myPlayerId;
    }

    @Override
    public void setPlayerId(int newPlayerId) {
        myPlayerId.setValue(newPlayerId);
    }

}
