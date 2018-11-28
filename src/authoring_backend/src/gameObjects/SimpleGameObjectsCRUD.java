package gameObjects;

import gameObjects.category.CategoryClass;
import gameObjects.category.SimpleCategoryClass;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.entity.SimpleEntityClass;
import gameObjects.exception.*;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.tile.SimpleTileClass;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


public class SimpleGameObjectsCRUD implements GameObjectsCRUDInterface {
    private int numRow;
    private int numCol;
    private ObservableMap<String, TileClass> tileClassMap;
    private ObservableMap<String, EntityClass> entityClassMap;
    private ObservableMap<String, CategoryClass> categoryClassMap;
    private ObservableMap<Integer, TileInstance> tileInstanceMap;
    private ObservableMap<Integer, EntityInstance> entityInstanceMap;

    private Consumer<GameObjectClass> returnClassId;
    private IdManager myIdManager;

    public SimpleGameObjectsCRUD(int numRow, int numCol) {
        this.numRow = numRow;
        this.numCol = numCol;
        tileClassMap = FXCollections.observableHashMap();
        entityClassMap = FXCollections.observableHashMap();
        categoryClassMap = FXCollections.observableHashMap();
        tileInstanceMap = FXCollections.observableHashMap();
        entityInstanceMap = FXCollections.observableHashMap();

        myIdManager = new IdManagerClass();
        returnClassId = myIdManager.returnClassIdFunc();
    }

    public SimpleGameObjectsCRUD(int numRow, int numCol, ObservableMap<String, TileClass> tileClasses, ObservableMap<String, EntityClass> entityClasses) {
        this(numRow, numCol);
        tileClassMap = tileClasses;
        entityClassMap = entityClasses;
    }

    // TODO: check for duplicate class name in entity map
    @Override
    public TileClass createTileClass(String name) {
        if (tileClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }
        TileClass newTileClass = new SimpleTileClass(
                name,
                numRow, numCol,
                Point.verifyPointsFunc(),
                myIdManager.requestTileInstanceIdFunc(),
                myIdManager.returnTileInstanceIdFunc(),
                addTileInstanceToMapFunc(),
                deleteTileInstanceFromMapFunc(),
                getTileInstancesFunc(),
                addTilePropertyFunc(),
                removeTilePropertyFunc());

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

    private boolean changeTileClassName(String oldName, String newName) {
        if (!tileClassMap.containsKey(oldName)) {
            return false;
        }
        tileClassMap.put(newName, tileClassMap.get(oldName));
        tileClassMap.remove(oldName);
        for (Map.Entry<Integer, TileInstance> e : tileInstanceMap.entrySet()) {
            if (e.getValue().getClassName().equals(oldName)) {
                e.getValue().setClassName(newName);
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

    private Function<Integer, Boolean> deleteTileInstanceFromMapFunc() {
        return instanceId -> deleteTileInstance(instanceId);
    }

    private Function<String, Set<GameObjectInstance>> getTileInstancesFunc() {
        return name -> {
            Set<GameObjectInstance> instancesSet = new HashSet<>();
            for (Map.Entry<Integer, TileInstance> entry : tileInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().getName().equals(name)) {
                    instancesSet.add(entry.getValue());
                }
            }
            return instancesSet;
        };
    }

    private TriConsumer<String, String, String> addTilePropertyFunc() {
        return (className, propertyName, defaultValue) -> {
            for (Map.Entry<Integer, TileInstance> entry : tileInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().equals(className)) {
                    entry.getValue().addProperty(propertyName, defaultValue);
                }
            }
        };
    }

    private BiConsumer<String, String> removeTilePropertyFunc() {
        return (className, propertyName) -> {
            for (Map.Entry<Integer, TileInstance> entry : tileInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().equals(className)) {
                    entry.getValue().removeProperty(propertyName);
                }
            }
        };
    }

    // TODO: check for duplicate class name in tile map
    @Override
    public EntityClass createEntityClass(String name) {
        if (entityClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }

        EntityClass newEntityClass = new SimpleEntityClass(
                name,
                myIdManager.verifyTileInstanceIdFunc(),
                myIdManager.requestEntityInstanceIdFunc(),
                myIdManager.returnEntityInstanceIdFunc(),
                addEntityInstanceToMapFunc(),
                deleteEntityInstanceFromMapFunc(),
                getEntityInstancesFunc(),
                addEntityPropertyFunc(),
                removeEntityPropertyFunc());

        myIdManager.requestClassIdFunc().accept(newEntityClass);
        entityClassMap.put(name, newEntityClass);
        return newEntityClass;
    }

    @Override
    public EntityClass getEntityClass(String name) {
        if (!entityClassMap.containsKey(name)) {
            throw new NoEntityClassException();
        }
        return entityClassMap.get(name);
    }

    @Override
    public boolean deleteEntityClass(String name) {
        if (!entityClassMap.containsKey(name)) {
            return false;
        }
        returnClassId.accept(entityClassMap.remove(name));
        for (Map.Entry<Integer, EntityInstance> e : entityInstanceMap.entrySet()) {
            if (e.getValue().getClassName().equals(name)) {
                deleteEntityInstance(e.getKey());
            }
        }
        return true;
    }

    private boolean changeEntityClassName(String oldName, String newName) {
        if (!entityClassMap.containsKey(oldName)) {
            return false;
        }
        entityClassMap.put(newName, entityClassMap.get(oldName));
        entityClassMap.remove(oldName);
        for (Map.Entry<Integer, EntityInstance> e : entityInstanceMap.entrySet()) {
            if (e.getValue().getClassName().equals(oldName)) {
                e.getValue().setClassName(newName);
            }
        }
        return true;
    }

    @Override
    public boolean deleteEntityInstance(int instanceId) {
        if (!entityInstanceMap.containsKey(instanceId)) {
            return false;
        }
        EntityInstance entityInstance = entityInstanceMap.remove(instanceId);
        entityInstance.getReturnInstanceIdFunc().accept(entityInstance);
        return true;
    }

    // TODO: propagate changes
    @Override
    public void setDimension(int width, int height) {
        numCol = width;
        numRow = height;
    }

    @Override
    public GameObjectClass getGameObjectClass(String className) {
        if (!entityClassMap.containsKey(className) && !tileClassMap.containsKey(className)) {
            throw new NoGameObjectClassException();
        }
        if (entityClassMap.containsKey(className)) {
            return entityClassMap.get(className);
        }
        else {
            return tileClassMap.get(className);
        }
    }

    @Override
    public boolean changeGameObjectClassName(String oldName, String newName) {
        if (!entityClassMap.containsKey(oldName) && !tileClassMap.containsKey(oldName)) {
            return false;
        }
        if (entityClassMap.containsKey(oldName)) {
            changeTileClassName(oldName, newName);
        }
        else {
            changeEntityClassName(oldName, newName);
        }
        return true;
    }


    private Consumer<EntityInstance> addEntityInstanceToMapFunc() {
        return entityInstance -> entityInstanceMap.put(entityInstance.getInstanceId().getValue(), entityInstance);
    }

    private Function<Integer, Boolean> deleteEntityInstanceFromMapFunc() {
        return instanceId -> deleteEntityInstance(instanceId);
    }

    private Function<String, Set<GameObjectInstance>> getEntityInstancesFunc() {
        return name -> {
            Set<GameObjectInstance> instancesSet = new HashSet<>();
            for (Map.Entry<Integer, EntityInstance> entry : entityInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().getName().equals(name)) {
                    instancesSet.add(entry.getValue());
                }
            }
            return instancesSet;
        };
    }

    private TriConsumer<String, String, String> addEntityPropertyFunc() {
        return (className, propertyName, defaultValue) -> {
            for (Map.Entry<Integer, EntityInstance> entry : entityInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().equals(className)) {
                    entry.getValue().addProperty(propertyName, defaultValue);
                }
            }
        };
    }

    private BiConsumer<String, String> removeEntityPropertyFunc() {
        return (className, propertyName) -> {
            for (Map.Entry<Integer, EntityInstance> entry : entityInstanceMap.entrySet()) {
                if (entry.getValue().getClassName().equals(className)) {
                    entry.getValue().removeProperty(propertyName);
                }
            }
        };
    }

    public int getWidth() { return numCol; }
    public int getHeight() { return numRow; }
    public Collection<TileClass> getTileClasses() { return tileClassMap.values(); }
    public Collection<EntityClass> getEntityClasses() { return entityClassMap.values(); }
    public Collection<TileInstance> getTileInstances() { return tileInstanceMap.values(); }
    public Collection<EntityInstance> getEntityInstances() { return entityInstanceMap.values(); }





    @Override
    public CategoryClass createCategoryClass(String name) {
        if (categoryClassMap.containsKey(name)) {
            throw new DuplicateClassException();
        }

        CategoryClass newCategoryClass = new SimpleCategoryClass(name);
        return newCategoryClass;
    }

    @Override
    public CategoryClass getCategoryClass(String name) {
        if (!categoryClassMap.containsKey(name)) {
            throw new NoCategoryClassException();
        }
        return categoryClassMap.get(name);
    }

    @Override
    public boolean deleteCategoryClass(String name) {
        if (!categoryClassMap.containsKey(name)) {
            return false;
        }
        categoryClassMap.remove(name);
        return true;
    }
}
