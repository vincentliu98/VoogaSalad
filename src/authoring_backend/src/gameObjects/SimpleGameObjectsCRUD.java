package gameObjects;

import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


public class SimpleEntitiesCRUD implements EntitiesCRUDInterface {

    private int numRow;
    private int numCol;
    private ObservableMap<String, TileClass> tileClassMap;
    private ObservableMap<String, SpriteClass> spriteClassMap;
    private ObservableMap<Integer, TileInstance> tileInstanceMap;
    private ObservableMap<Integer, SpriteInstance> spriteInstanceMap;

    private Consumer<EntityClass> returnClassId;
    private IdManager myIdManager;

    public SimpleEntitiesCRUD(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;
        tileClassMap = FXCollections.observableHashMap();
        spriteClassMap = FXCollections.observableHashMap();
        myIdManager = new IdManagerClass();
        returnClassId = myIdManager.returnClassIdFunc();
    }

    public SimpleEntitiesCRUD(int numRow, int numCol, ObservableMap<String, TileClass> tileClasses, ObservableMap<String, SpriteClass> spriteClasses) {
        this(numRow, numCol);
        tileClassMap = tileClasses;
        spriteClassMap = spriteClasses;
    }

    @Override
    public TileClass createTileClass(String name) {
        if (tileClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }
        TileClass newTileClass = new SimpleTileClass(
                numRow,
                numCol,
                Point.verifyPointsFunc(),
                myIdManager.requestTileInstanceIdFunc(),
                myIdManager.returnTileInstanceIdFunc(),
                addTileInstanceToMapFunc(),
                getTileInstancesFunc());

        myIdManager.requestClassIdFunc().accept(newTileClass);
        tileClassMap.put(name, newTileClass);
        return newTileClass;
    }

    @Override
    public TileClass getTileClass(String name) {
        if (!tileClassMap.containsKey(name)) {
            throw new NoTileClassException();
        }
        return tileClassMap.get(name);
    }

    @Override
    public boolean deleteTileClass(String name) {
        if (!tileClassMap.containsKey(name)) {
            return false;
        }
        returnClassId.accept(tileClassMap.remove(name));
        for (Map.Entry<Integer, TileInstance> e : tileInstanceMap.entrySet()) {
            if (e.getValue().getClassName().equals(name)) {
                deleteTileInstance(e.getKey());
            }
        }
        return true;
    }

    @Override
    public boolean deleteTileInstance(int instanceId) {
        if (!tileInstanceMap.containsKey(instanceId)) {
            return false;
        }
        TileInstance tileInstance = tileInstanceMap.remove(instanceId);
        tileInstance.getReturnInstanceIdFunc().accept(tileInstance);
        return true;
    }

    private Consumer<TileInstance> addTileInstanceToMapFunc() {
        return tileInstance -> tileInstanceMap.put(tileInstance.getInstanceId().getValue(), tileInstance);
    }

    private Consumer<Integer> deleteTileInstanceFromMapFunc() {
        return instanceId -> deleteTileInstance(instanceId);
    }

    private Function<String, Set<EntityInstance>> getTileInstancesFunc() {
        return name -> {
            Set<EntityInstance> instancesSet = new HashSet<>();
            for (Map.Entry<Integer, TileInstance> entry : tileInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().getName().equals(name)) {
                    instancesSet.add(entry.getValue());
                }
            }
            return instancesSet;
        };
    }






    @Override
    public SpriteClass createSpriteClass(String name) {
        if (spriteClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }

        SpriteClass newSpriteClass = new SimpleSpriteClass(
                myIdManager.verifyTileInstanceIdFunc(),
                myIdManager.requestSpriteInstanceIdFunc(),
                myIdManager.returnSpriteInstanceIdFunc(),
                addSpriteInstanceToMapFunc(),
                deleteSpriteInstanceFromMapFunc(),
                getSpriteInstancesFunc(),
                addSpritePropertyFunc(),
                removeSpritePropertyFunc());

        myIdManager.requestClassIdFunc().accept(newSpriteClass);
        spriteClassMap.put(name, newSpriteClass);
        return newSpriteClass;
    }

    @Override
    public SpriteClass getSpriteClass(String name) {
        if (!spriteClassMap.containsKey(name)) {
            throw new NoSpriteClassException();
        }
        return spriteClassMap.get(name);
    }

    @Override
    public boolean deleteSpriteClass(String name) {
        if (!spriteClassMap.containsKey(name)) {
            return false;
        }
        returnClassId.accept(spriteClassMap.remove(name));
        for (Map.Entry<Integer, SpriteInstance> e : spriteInstanceMap.entrySet()) {
            if (e.getValue().getClassName().equals(name)) {
                deleteSpriteInstance(e.getKey());
            }
        }
        return true;
    }

    @Override
    public boolean deleteSpriteInstance(int instanceId) {
        if (!spriteInstanceMap.containsKey(instanceId)) {
            return false;
        }
        SpriteInstance spriteInstance = spriteInstanceMap.remove(instanceId);
        spriteInstance.getReturnInstanceIdFunc().accept(spriteInstance);
        return true;
    }


    private Consumer<SpriteInstance> addSpriteInstanceToMapFunc() {
        return spriteInstance -> spriteInstanceMap.put(spriteInstance.getInstanceId().getValue(), spriteInstance);
    }

    private Function<Integer, Boolean> deleteSpriteInstanceFromMapFunc() {
        return instanceId -> deleteSpriteInstance(instanceId);
    }

    private Function<String, Set<EntityInstance>> getSpriteInstancesFunc() {
        return name -> {
            Set<EntityInstance> instancesSet = new HashSet<>();
            for (Map.Entry<Integer, SpriteInstance> entry : spriteInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().getName().equals(name)) {
                    instancesSet.add(entry.getValue());
                }
            }
            return instancesSet;
        };
    }

    private TriConsumer<String, String, String> addSpritePropertyFunc() {
        return (className, propertyName, defaultValue) -> {
            for (Map.Entry<Integer, SpriteInstance> entry : spriteInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().equals(className)) {
                    entry.getValue().addProperty(propertyName, defaultValue);
                }
            }
        };
    }

    private BiConsumer<String, String> removeSpritePropertyFunc() {
        return (className, propertyName) -> {
            for (Map.Entry<Integer, SpriteInstance> entry : spriteInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().equals(className)) {
                    entry.getValue().removeProperty(propertyName);
                }
            }
        };
    }

    @Override
    public String toXML() {

        // TODO??
        return null;
    }

}
