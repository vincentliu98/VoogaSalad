package gameObjects.entity;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import grids.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class EntityInstanceFactory {

    private Function<Integer, Boolean> verifyEntityInstanceIdFunc;
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;

    public EntityInstanceFactory(
            Function<Integer, Boolean> verifyEntityInstanceIdFunc,
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc
    ) {

        this.verifyEntityInstanceIdFunc = verifyEntityInstanceIdFunc;
        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public EntityInstance createInstance(EntityClass entityPrototype, Point point)
            throws GameObjectTypeException, InvalidIdException {
        // TODO locality
//        if (!verifyEntityInstanceIdFunc.apply(tileId)) {
//            throw new InvalidGameObjectInstanceException("Entity cannot be created on Tile Instance with invalid Tile Id");
//        }
        if (entityPrototype.getType() != GameObjectType.ENTITY) {
            throw new GameObjectTypeException(GameObjectType.ENTITY);
        }
        var imagePathListCopy = new ArrayList<>(entityPrototype.getImagePathList());
        var propertiesMapCopy = new HashMap<>(entityPrototype.getPropertiesMap());
        EntityInstance entityInstance = new SimpleEntityInstance(entityPrototype.getClassName(), imagePathListCopy, propertiesMapCopy, entityPrototype);
        entityInstance.setCoord(point);
        entityInstance.setHeight(entityPrototype.getHeight());
        entityInstance.setWidth(entityPrototype.getWidth());
        requestInstanceIdFunc.accept(entityInstance);
        addInstanceToMapFunc.accept(entityInstance);
        return entityInstance;

    }

}
