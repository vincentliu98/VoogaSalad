package gameObjects.player;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PlayerInstanceFactory {
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;

    public PlayerInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public PlayerInstance createInstance(PlayerClass playerPrototype)
            throws GameObjectTypeException, InvalidIdException {
        // TODO locality
        if (playerPrototype.getType() != GameObjectType.PLAYER) {
            throw new GameObjectTypeException("PlayerPrototype", "Player");
        }
        SimpleStringProperty imagePathCopy = new SimpleStringProperty();
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        imagePathCopy.setValue(playerPrototype.getImagePath().getValue());
        propertiesMapCopy.putAll(playerPrototype.getPropertiesMap());
        Supplier<PlayerClass> getPlayerClassFunc = () -> playerPrototype;
        PlayerInstance playerInstance = new SimplePlayerInstance(playerPrototype.getClassName().getValue(), imagePathCopy, propertiesMapCopy, getPlayerClassFunc);
        requestInstanceIdFunc.accept(playerInstance);
        addInstanceToMapFunc.accept(playerInstance);
        return playerInstance;

    }
}
