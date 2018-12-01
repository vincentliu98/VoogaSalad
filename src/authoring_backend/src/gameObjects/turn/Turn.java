package gameObjects.turn;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public interface Turn {
    SimpleStringProperty getPhaseName();

    void setPhaseName(String newPhaseName);

    SimpleIntegerProperty getPlayerId();

    void setPlayerId(int newPlayerId);
}
