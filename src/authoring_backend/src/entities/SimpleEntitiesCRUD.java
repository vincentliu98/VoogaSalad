package entities;

import grids.Grid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;


public class SimpleEntitiesCRUD implements EntitiesCRUDInterface {

    private ObservableMap<String, TileClass> tileClassMap;
    private ObservableMap<String, SpriteClass> spriteClassMap;
    private ObservableMap<Integer, TileInstance> tileInstanceMap;
    private ObservableMap<Integer, SpriteInstance> spriteInstanceMap;

    private Grid grid;
    private Consumer<EntityClass> returnClassId;
    private IdManager myIdManager;

    public SimpleEntitiesCRUD() {
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
        TileClass newTileClass = new SimpleTileClass(myIdManager.requestTileInstanceIdFunc());
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
    public SpriteClass createSpriteClass(String name) {
        if (spriteClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }

        SpriteClass newSpriteClass = new SimpleSpriteClass(
                myIdManager.verifyTileInstanceIdFunc(),
                myIdManager.requestSpriteInstanceIdFunc(),
                myIdManager.returnSpriteInstanceIdFunc(),
                addSpriteInstanceToMapFunc(),
                removeSpriteInstanceFromMapFunc());

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
    public String toXML() {
        return null;
    }



    private Consumer<SpriteInstance> addSpriteInstanceToMapFunc() {
        return spriteInstance -> spriteInstanceMap.put(spriteInstance.getInstanceId().getValue(), spriteInstance);
    }

    private Consumer<SpriteInstance> removeSpriteInstanceFromMapFunc() {
        return spriteInstance -> spriteInstanceMap.remove(spriteInstance.getInstanceId().getValue(), spriteInstance);
    }

}
