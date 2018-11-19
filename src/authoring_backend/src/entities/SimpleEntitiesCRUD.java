package entities;

import grids.Grid;
import grids.GridImpl;
import grids.GridShape;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;


public class SimpleEntitiesCRUD implements EntitiesCRUDInterface {

    private ObservableMap<String, TileClass> tileClassMap;
    private ObservableMap<String, SpriteClass> spriteClassMap;
    private ObservableMap<Integer, TileInstance> tileInstanceMap;
    private ObservableMap<Integer, SpriteInstance> spriteInstanceMap;

    private Grid grid;
    private Consumer<EntityClass> returnClassId;
    private IdManager myIdManager;

    private SimpleEntitiesCRUD() {
        tileClassMap = FXCollections.observableHashMap();
        spriteClassMap = FXCollections.observableHashMap();
        myIdManager = new IdManagerClass();
        returnClassId = myIdManager.returnClassIdFunc();
    }

    public SimpleEntitiesCRUD(Grid g) {
        this();
        grid = g;
    }

    public SimpleEntitiesCRUD(ObservableMap<String, TileClass> tileClasses, ObservableMap<String, SpriteClass> spriteClasses, Grid g) {
        this();
        tileClassMap = tileClasses;
        spriteClassMap = spriteClasses;
        grid = g;
    }

    @Override
    public TileClass createTileClass(String name) {
        if (tileClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }
        TileClass newTileClass = new SimpleTileClass(
                grid.verifyPointsFunc(),
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
                getSpriteInstancesFunc());

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

    private Consumer<TileInstance> addTileInstanceToMapFunc() {
        return tileInstance -> tileInstanceMap.put(tileInstance.getInstanceId().getValue(), tileInstance);
    }

    private Consumer<SpriteInstance> addSpriteInstanceToMapFunc() {
        return spriteInstance -> spriteInstanceMap.put(spriteInstance.getInstanceId().getValue(), spriteInstance);
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

    @Override
    public String toXML() {

        // TODO??
        return null;
    }

}
