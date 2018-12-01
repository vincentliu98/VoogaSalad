package gameObjects.entity;

import gameObjects.exception.InvalidClassException;
import gameObjects.exception.InvalidTileException;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityInstanceFactory {

    private Function<Integer, Boolean> verifyEntityInstanceIdFunc;
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private Consumer<GameObjectInstance> addInstanceToMapFunc;
    private BiConsumer<Integer, Integer> addInstanceToPlayer;


    public EntityInstanceFactory(
            Function<Integer, Boolean> verifyEntityInstanceIdFunc,
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            Consumer<GameObjectInstance> addInstanceToMapFunc,
            BiConsumer<Integer, Integer> addInstanceToPlayer
    ) {

        this.verifyEntityInstanceIdFunc = verifyEntityInstanceIdFunc;
        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
        this.addInstanceToPlayer = addInstanceToPlayer;
    }

    public EntityInstance createInstance(EntityClass entityPrototype, int tileId, int playerID) {
        // TODO locality
        if (!verifyEntityInstanceIdFunc.apply(tileId)) {
            throw new InvalidTileException();
        }
        if (entityPrototype.getType() != GameObjectType.ENTITY) {
            throw new InvalidClassException();
        }
        ObservableList imagePathListCopy = FXCollections.observableArrayList();
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        imagePathListCopy.addAll(entityPrototype.getImagePathList());
        propertiesMapCopy.putAll(entityPrototype.getPropertiesMap());
        Supplier<EntityClass> getEntityClassFunc = () -> entityPrototype;
        EntityInstance entityInstance = new SimpleEntityInstance(entityPrototype.getClassName().getValue(), tileId, imagePathListCopy, propertiesMapCopy, getEntityClassFunc);
        requestInstanceIdFunc.accept(entityInstance);
        addInstanceToMapFunc.accept(entityInstance);
        addInstanceToPlayer.accept(entityInstance.getInstanceId().get(), playerID);
        return entityInstance;

    }

}
