package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
            Consumer<SimpleIntegerProperty> setIdFunc = myIdManager.requestSetIdFunc();
            tileMap.put(name, new SimpleTileClass(setIdFunc));
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
        return tileMap.remove(name) != null;
    }

    @Override
    public boolean createSpriteClass(String name) {
        if (!spriteMap.containsKey(name)) {
            Consumer<SimpleIntegerProperty> setIdFunc = myIdManager.requestSetIdFunc();
            spriteMap.put(name, new SimpleSpriteClass(setIdFunc));
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
        return spriteMap.remove(name) != null;
    }
}
