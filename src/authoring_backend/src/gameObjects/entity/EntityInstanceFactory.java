package gameObjects.entity;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.effect.Light;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityInstanceFactory {

    private Function<Integer, Boolean> verifyEntityInstanceIdFunc;
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;
    private BiConsumer<Integer, Integer> addInstanceToPlayer;


    public EntityInstanceFactory(
            Function<Integer, Boolean> verifyEntityInstanceIdFunc,
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc,
            BiConsumer<Integer, Integer> addInstanceToPlayer
    ) {

        this.verifyEntityInstanceIdFunc = verifyEntityInstanceIdFunc;
        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
        this.addInstanceToPlayer = addInstanceToPlayer;
    }

    public EntityInstance createInstance(EntityClass entityPrototype, int playerID, Point point)
            throws GameObjectTypeException, InvalidIdException {
        // TODO locality
//        if (!verifyEntityInstanceIdFunc.apply(tileId)) {
//            throw new InvalidGameObjectInstanceException("Entity cannot be created on Tile Instance with invalid Tile Id");
//        }
        if (entityPrototype.getType() != GameObjectType.ENTITY) {
            throw new GameObjectTypeException("entityPrototype","Entity");
        }
        ObservableList imagePathListCopy = FXCollections.observableArrayList();
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        imagePathListCopy.addAll(entityPrototype.getImagePathList());
        propertiesMapCopy.putAll(entityPrototype.getPropertiesMap());
        Supplier<EntityClass> getEntityClassFunc = () -> entityPrototype;
        EntityInstance entityInstance = new SimpleEntityInstance(entityPrototype.getClassName().getValue(), imagePathListCopy, propertiesMapCopy, getEntityClassFunc);
        entityInstance.setCoord(point);
        entityInstance.setHeight(entityPrototype.getHeight().getValue());
        entityInstance.setWidth(entityPrototype.getWidth().getValue());
        requestInstanceIdFunc.accept(entityInstance);
        addInstanceToMapFunc.accept(entityInstance);
        addInstanceToPlayer.accept(entityInstance.getInstanceId().get(), playerID);
        return entityInstance;

    }

}
