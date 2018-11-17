package entities;

import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;


public class SimpleEntitiesCRUD implements EntitiesCRUDInterface {
    private String CONST_TILEMAP = "tileMap";
    private String CONST_SPRITEMAP = "spriteMap";

    private ObservableMap<String, TileClass> tileMap;
    private ObservableMap<String, SpriteClass> spriteMap;
    private IdManager myIdManager;

    public SimpleEntitiesCRUD() {
        tileMap = FXCollections.observableHashMap();
        spriteMap = FXCollections.observableHashMap();
        myIdManager = new IdManagerClass();
    }

    public SimpleEntitiesCRUD(ObservableMap<String, TileClass> tileClasses, ObservableMap<String, SpriteClass> spriteClasses) {
        tileMap = tileClasses;
        spriteMap = spriteClasses;
    }

    @Override
    public boolean createTileClass(String name) {
//        System.out.println( == null);
        if (!tileMap.containsKey(name)) {
            tileMap.put(name, new SimpleTileClass(myIdManager.requestClassIdFunc()));
            return true;
        }
        return false;
    }

    @Override
    public TileClass getTileClass(String name) {
        if (!tileMap.containsKey(name)) {
            throw new NoTileClassException();
        }
        return tileMap.get(name);
    }

    @Override
    public boolean deleteTileClass(String name) {
        if (!tileMap.containsKey(name)) {
            return false;
        }
        myIdManager.returnClassIdFunc(tileMap.get(name).returnClassId());
        return tileMap.remove(name) != null;
    }

    @Override
    public boolean createSpriteClass(String name) {
        if (!spriteMap.containsKey(name)) {
            spriteMap.put(name, new SimpleSpriteClass(myIdManager.requestClassIdFunc()));
            return true;
        }
        return false;
    }

    @Override
    public SpriteClass getSpriteClass(String name) {
        if (!spriteMap.containsKey(name)) {
            throw new NoSpriteClassException();
        }
        return spriteMap.get(name);
    }

    @Override
    public boolean deleteSpriteClass(String name) {
        if (!spriteMap.containsKey(name)) {
            return false;
        }
        myIdManager.returnClassIdFunc(spriteMap.get(name).returnClassId());
        return spriteMap.remove(name) != null;
    }
    @Override
    public String toXML() {
        return null;
    }
}
