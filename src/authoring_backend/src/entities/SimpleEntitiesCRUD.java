package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SimpleEntitiesCRUD implements EntitiesCRUDInterface {
    private String CONST_TILEMAP = "tileMap";
    private String CONST_SPRITEMAP = "spriteMap";

    private SimpleMapProperty<String, TileClass> tileMap;
    private SimpleMapProperty<String, SpriteClass> spriteMap;
    private IdManager myIdManager;

    public SimpleEntitiesCRUD() {
        tileMap = new SimpleMapProperty<>(this, CONST_TILEMAP);
        spriteMap = new SimpleMapProperty<>(this, CONST_SPRITEMAP);
        myIdManager = new IdManagerClass();
    }

    public SimpleEntitiesCRUD(SimpleMapProperty<String, TileClass> tileClasses, SimpleMapProperty<String, SpriteClass> spriteClasses) {
        tileMap = tileClasses;
        spriteMap = spriteClasses;
    }

    @Override
    public boolean createTileClass(String name) {
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
