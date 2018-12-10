package gameObjects;


import authoringUtils.exception.InvalidIdException;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This Class implements the IdManager Interface.
 * This Class assigns an id to every GameObject Class and Tile or Sprite Instance.
 * It maintains lists of returned ids for classes, tile instances and sprite instances.
 * @author Jason Zhou
 */


public class IdManagerClass implements IdManager {
//    private LinkedList<Integer> validClassIds;
//    private LinkedList<Integer> validInstanceIds;

    private Function<Integer, GameObjectClass> getClassFromMapFunc;
    private Function<Integer, GameObjectInstance> getInstanceFromMapFunc;
    private Map<Integer, GameObjectClass> gameObjectClassMap;
    private Map<Integer, GameObjectInstance> gameObjectInstanceMap;


    private int classCount;
    private int instanceCount;


    public IdManagerClass(
            Function<Integer, GameObjectClass> getClassFromMapFunc,
            Function<Integer, GameObjectInstance> getInstanceFromMapFunc,
            Map<Integer, GameObjectClass> gameObjectClassMap,
            Map<Integer, GameObjectInstance> gameObjectInstanceMap) {

        classCount = 1;
        instanceCount = 1;
//        validClassIds = new LinkedList<>();
//        validInstanceIds = new LinkedList<>();

        this.gameObjectClassMap = gameObjectClassMap;
        this.gameObjectInstanceMap = gameObjectInstanceMap;
        this.getClassFromMapFunc = getClassFromMapFunc;
        this.getInstanceFromMapFunc = getInstanceFromMapFunc;
    }

    @Override
    public Consumer<GameObjectClass> requestClassIdFunc() {
        return gameObjectClass -> {
            // Only set Ids for Classes without Ids
            if (gameObjectClass.getClassId() == 0) {
                int id = classCount;
                classCount++;
                gameObjectClass.setClassId(id);
            }
        };
    }

    @Override
    public ThrowingConsumer<GameObjectClass, InvalidIdException> returnClassIdFunc() {

        return gameObjectClass -> {
            int returnedId = gameObjectClass.getClassId();
            if (returnedId != 0) {
                if (returnedId > classCount) {
                    throw new InvalidIdException("Deleted class contains invalid Id");
                }
                int nextId = returnedId + 1;
                for (int i = nextId; i < classCount; i++) {
                    GameObjectClass g = gameObjectClassMap.remove(i);
                    int newId = i - 1;
                    g.setClassId(newId);
                    gameObjectClassMap.put(newId, g);
                }
                classCount--;
            }
        };
    }




    @Override
    public Consumer<GameObjectInstance> requestInstanceIdFunc() {
        return gameObjectInstance -> {
            // Only set Ids for Instances without Ids
            if (gameObjectInstance.getInstanceId().getValue() == 0) {
                int id = instanceCount;
                instanceCount++;
                gameObjectInstance.setInstanceId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
            }
        };
    }

    @Override
    public ThrowingConsumer<GameObjectInstance, InvalidIdException> returnInstanceIdFunc() {

        return gameObjectInstance -> {
            int returnedId = gameObjectInstance.getInstanceId().getValue();
            if (returnedId != 0) {
                if (returnedId > instanceCount) {
                    throw new InvalidIdException("Deleted instance contains invalid Id");
                }
                int nextId = returnedId + 1;
                for (int i = nextId; i < instanceCount; i++) {
                    GameObjectInstance g = gameObjectInstanceMap.remove(i);
                    int newId = i - 1;
                    g.setInstanceId(simpleIntegerProperty -> simpleIntegerProperty.setValue(newId));
                    gameObjectInstanceMap.put(newId, g);
                }
                instanceCount--;
            }
        };
    }



    @Override
    public Function<Integer, Boolean> verifyClassIdFunc() {
        return i -> getClassFromMapFunc.apply(i) != null;
    }

    @Override
    public Function<Integer, Boolean> verifyTileInstanceIdFunc() {
        return i -> {
//            GameObjectInstance g = getInstanceFromMapFunc.apply(i);
//            return g != null && g.getType() == GameObjectType.TILE;
            return true;
        };
    }
}
