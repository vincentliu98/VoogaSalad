package gameObjects;

import gameObjects.exception.DuplicateIdException;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;

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
    private List<Integer> returnedTileInstanceIds;
    private List<Integer> returnedEntityInstanceIds;

    private int classCount;
    private int tileInstanceCount;
    private int entityInstanceCount;

    public IdManagerClass() {
        classCount = 0;
        tileInstanceCount = 0;
        entityInstanceCount = 0;
        returnedClassIds = new ArrayList<>();
        returnedTileInstanceIds = new ArrayList<>();
        returnedEntityInstanceIds = new ArrayList<>();
    }

    @Override
    public Consumer<GameObjectClass> requestClassIdFunc() {
        int id;
        if (!returnedClassIds.isEmpty()) {
            id = returnedClassIds.remove(0);
        } else {
            id = classCount;
            classCount++;
        }
        return gameObjectClass -> gameObjectClass.setClassId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
    }

    @Override
    public Consumer<GameObjectInstance> requestTileInstanceIdFunc() {
        return tileInstance -> tileInstance.setInstanceId(simpleIntegerProperty -> {
            int id;
            if (!returnedTileInstanceIds.isEmpty()) {
                id = returnedTileInstanceIds.remove(0);
            } else {
                id = tileInstanceCount;
                tileInstanceCount++;
            }
            simpleIntegerProperty.setValue(id);
        });
    }

    @Override
    public Consumer<GameObjectInstance> requestEntityInstanceIdFunc() {
        return entityInstance -> entityInstance.setInstanceId(simpleIntegerProperty -> {
            int id;
            if (!returnedEntityInstanceIds.isEmpty()) {
                id = returnedEntityInstanceIds.remove(0);
            } else {
                id = entityInstanceCount;
                entityInstanceCount++;
            }
            simpleIntegerProperty.setValue(id);
        });
    }

    @Override
    public Consumer<GameObjectClass> returnClassIdFunc() {

        return (gameObjectClass) -> {
            int returnedId = gameObjectClass.getClassId().getValue();
            if (classCount < returnedId || returnedClassIds.contains(returnedId)) {
                throw new DuplicateIdException();
            }
            returnedClassIds.add(returnedId);
        };
    }

    @Override
    public Consumer<GameObjectInstance> returnTileInstanceIdFunc() {
        return (tileInstance) -> {
            int returnedId = tileInstance.getInstanceId().getValue();
            if (tileInstanceCount < returnedId || returnedTileInstanceIds.contains(returnedId)) {
                throw new DuplicateIdException();
            }
            returnedTileInstanceIds.add(returnedId);
        };
    }

    @Override
    public Consumer<GameObjectInstance> returnEntityInstanceIdFunc() {
        return (entityInstance) -> {
            int returnedId = entityInstance.getInstanceId().getValue();
            if (entityInstanceCount < returnedId || returnedEntityInstanceIds.contains(returnedId)) {
                throw new DuplicateIdException();
            }
            returnedEntityInstanceIds.add(returnedId);
        };
    }

    @Override
    public Function<Integer, Boolean> verifyClassIdFunc() {
        return (i) -> classCount >= i && !returnedClassIds.contains(i);
    }

    @Override
    public Function<Integer, Boolean> verifyTileInstanceIdFunc() {
        return (i) -> tileInstanceCount >= i && !returnedTileInstanceIds.contains(i);
    }

    @Override
    public Function<Integer, Boolean> verifyEntityInstanceIdFunc() {
        return (i) -> entityInstanceCount >= i && !returnedEntityInstanceIds.contains(i);
    }
}
