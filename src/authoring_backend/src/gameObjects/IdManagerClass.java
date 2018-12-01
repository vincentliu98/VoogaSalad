package gameObjects;


import gameObjects.exception.InvalidIdException;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This Class implements the IdManager Interface.
 * This Class assigns an id to every GameObject Class and Tile or Sprite Instance.
 * It maintains lists of returned ids for classes, tile instances and sprite instances.
 * @author Jason Zhou
 */


public class IdManagerClass implements IdManager {
    private List<Integer> returnedClassIds;
    private List<Integer> returnedInstanceIds;
    private List<Integer> returnedPlayerInstanceIds;

    Function<Integer, GameObjectClass> getClassFromMapFunc;
    Function<Integer, GameObjectInstance> getInstanceFromMapFunc;


    private int classCount;
    private int instanceCount;
    private int playerInstanceCount;

    public IdManagerClass(
            Function<Integer, GameObjectClass> getClassFromMapFunc,
            Function<Integer, GameObjectInstance> getInstanceFromMapFunc) {
        classCount = 1;
        instanceCount = 1;
        playerInstanceCount = 0;
        returnedClassIds = new ArrayList<>();
        returnedPlayerInstanceIds = new ArrayList<>();

        this.getClassFromMapFunc = getClassFromMapFunc;
        this.getInstanceFromMapFunc = getInstanceFromMapFunc;
    }

    @Override
    public Consumer<GameObjectClass> requestClassIdFunc() {
        return gameObjectClass -> {
            // Only set Ids for Classes without Ids
            if (gameObjectClass.getClassId().getValue() == 0) {
                int id;
                if (!returnedClassIds.isEmpty()) {
                    id = returnedClassIds.remove(0);
                } else {
                    id = classCount;
                    classCount++;
                }
                gameObjectClass.setClassId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
            }
        };
    }

    @Override
    public Consumer<GameObjectClass> returnClassIdFunc() {

        return gameObjectClass -> {
            int returnedId = gameObjectClass.getClassId().getValue();
            if (returnedId != 0) {
                if (classCount < returnedId || returnedId < 0 || returnedClassIds.contains(returnedId)) {
                    throw new InvalidIdException();
                }
                returnedClassIds.add(returnedId);
            }
        };
    }




    @Override
    public Consumer<GameObjectInstance> requestInstanceIdFunc() {
        return gameObjectInstance -> {
            // Only set Ids for Instances without Ids
            if (gameObjectInstance.getInstanceId().getValue() == 0) {
                int id;
                if (!returnedInstanceIds.isEmpty()) {
                    id = returnedInstanceIds.remove(0);
                } else {
                    id = instanceCount;
                    instanceCount++;
                }
                gameObjectInstance.setInstanceId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
            }
        };
    }

    @Override
    public Consumer<GameObjectInstance> returnInstanceIdFunc() {

        return gameObjectInstance -> {
            int returnedId = gameObjectInstance.getInstanceId().getValue();
            if (returnedId != 0) {
                if (instanceCount < returnedId || returnedId < 0 || returnedInstanceIds.contains(returnedId)) {
                    throw new InvalidIdException();
                }
                returnedInstanceIds.add(returnedId);
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
            GameObjectInstance g = getInstanceFromMapFunc.apply(i);
            return g != null && g.getType() == GameObjectType.TILE;
        };
    }
}
