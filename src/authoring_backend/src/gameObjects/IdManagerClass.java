package entities;

import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This Class implements the IdManager Interface.
 * This Class assigns an id to every Entity Class and Tile or Sprite Instance.
 * It maintains lists of returned ids for classes, tile instances and sprite instances.
 * @author Jason Zhou
 */


public class IdManagerClass implements IdManager {
    private List<Integer> returnedClassIds;
    private List<Integer> returnedTileInstanceIds;
    private List<Integer> returnedSpriteInstanceIds;

    private int classCount;
    private int tileInstanceCount;
    private int spriteInstanceCount;

    IdManagerClass() {
        classCount = 0;
        tileInstanceCount = 0;
        spriteInstanceCount = 0;
        returnedClassIds = new ArrayList<>();
        returnedTileInstanceIds = new ArrayList<>();
        returnedSpriteInstanceIds = new ArrayList<>();
    }

    @Override
    public Consumer<EntityClass> requestClassIdFunc() {
        int id;
        if (!returnedClassIds.isEmpty()) {
            id = returnedClassIds.remove(0);
        } else {
            id = classCount;
            classCount++;
        }
        return entityClass -> entityClass.setClassId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
    }

    @Override
    public Consumer<EntityInstance> requestTileInstanceIdFunc() {
        int id;
        if (!returnedTileInstanceIds.isEmpty()) {
            id = returnedTileInstanceIds.remove(0);
        } else {
            id = tileInstanceCount;
            tileInstanceCount++;
        }
        return tileInstance -> tileInstance.setInstanceId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
    }

    @Override
    public Consumer<EntityInstance> requestSpriteInstanceIdFunc() {
        int id;
        if (!returnedSpriteInstanceIds.isEmpty()) {
            id = returnedSpriteInstanceIds.remove(0);
        } else {
            id = spriteInstanceCount;
            spriteInstanceCount++;
        }
        return spriteInstance -> spriteInstance.setInstanceId(simpleIntegerProperty -> simpleIntegerProperty.setValue(id));
    }

    @Override
    public Consumer<EntityClass> returnClassIdFunc() {

        return (entityClass) -> {
            int returnedId = entityClass.getClassId().getValue();
            if (classCount < returnedId || returnedClassIds.contains(returnedId)) {
                throw new DuplicateIdException();
            }
            returnedClassIds.add(returnedId);
        };
    }

    @Override
    public Consumer<EntityInstance> returnTileInstanceIdFunc() {
        return (tileInstance) -> {
            int returnedId = tileInstance.getInstanceId().getValue();
            if (tileInstanceCount < returnedId || returnedTileInstanceIds.contains(returnedId)) {
                throw new DuplicateIdException();
            }
            returnedTileInstanceIds.add(returnedId);
        };
    }

    @Override
    public Consumer<EntityInstance> returnSpriteInstanceIdFunc() {
        return (spriteInstance) -> {
            int returnedId = spriteInstance.getInstanceId().getValue();
            if (spriteInstanceCount < returnedId || returnedSpriteInstanceIds.contains(returnedId)) {
                throw new DuplicateIdException();
            }
            returnedSpriteInstanceIds.add(returnedId);
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
    public Function<Integer, Boolean> verifySpriteInstanceIdFunc() {
        return (i) -> spriteInstanceCount >= i && !returnedSpriteInstanceIds.contains(i);
    }
}
