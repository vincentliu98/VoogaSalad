package entities;

import javafx.beans.property.SimpleMapProperty;

import java.util.HashMap;
import java.util.Map;

public class SimpleEntitiesCRUD implements EntitiesCRUDInterface {
    private SimpleMapProperty<String, TileClass> tileMap;
    private SimpleMapProperty<String, SpriteClass> spriteMap;

    public SimpleEntitiesCRUD() {
        tileMap = new SimpleMapProperty<>(this, "tileMap");
        spriteMap = new SimpleMapProperty<>(this, "spriteMap");
    }

    public SimpleEntitiesCRUD(SimpleMapProperty tileClasses, SimpleMapProperty spriteClasses) {
        tileMap = tileClasses;
        spriteMap = spriteClasses;
    }

    @Override
    public boolean createTileClass(String name) {
        if (!tileMap.containsKey(name)) {
            tileMap.put(name, new SimpleTileClass());
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
        // TODO
        return false;
    }

    @Override
    public boolean createSpriteClass(String name) {
        if (!spriteMap.containsKey(name)) {
            spriteMap.put(name, new SimpleSpriteClass());
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
        // TODO
        return false;
    }
}
