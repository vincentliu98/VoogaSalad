package gameObjects;


import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * This Class implements the IdManager Interface.
 * This Class assigns an id to every GameObject Class and Tile or Sprite Instance.
 * It maintains lists of returned ids for classes, tile instances and sprite instances.
 *
 * @author Jason Zhou
 */


public class IdManagerClass implements IdManager {
//    private LinkedList<Integer> validClassIds;
//    private LinkedList<Integer> validInstanceIds;

    private Function<Integer, GameObjectClass> getClassFromMapFunc;
    private Function<Integer, GameObjectInstance> getInstanceFromMapFunc;
    private Map<Integer, GameObjectClass> gameObjectClassMap;
    private Map<Integer, GameObjectInstance> gameObjectInstanceMap;

    public IdManagerClass(
            Function<Integer, GameObjectClass> getClassFromMapFunc,
            Function<Integer, GameObjectInstance> getInstanceFromMapFunc,
            Map<Integer, GameObjectClass> gameObjectClassMap,
            Map<Integer, GameObjectInstance> gameObjectInstanceMap
    ) {

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
                gameObjectClass.setClassId(newClassID());
            }
        };
    }

    @Override
    public Consumer<GameObjectInstance> requestInstanceIdFunc() {
        return gameObjectInstance -> {
            // Only set Ids for Instances without Ids
            if (gameObjectInstance.getInstanceId() == 0) {
                gameObjectInstance.setInstanceId(newInstanceID());
            }
        };
    }

    private int newClassID() {
        return Stream.iterate(1, a -> a + 1).filter(this::notIssuedClassID).findFirst().get();
    }

    private int newInstanceID() {
        return Stream.iterate(1, a -> a + 1).filter(this::notIssuedInstanceID).findFirst().get();
    }

    private boolean notIssuedClassID(int id) {
        return gameObjectClassMap.values()
                .stream()
                .noneMatch(c -> c.getClassId() == id);
    }

    private boolean notIssuedInstanceID(int id) {
        return gameObjectInstanceMap.values()
                .stream()
                .noneMatch(c -> c.getInstanceId() == id);
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
