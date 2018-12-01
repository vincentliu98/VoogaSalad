package gameObjects.crud;

import gameObjects.IdManager;
import gameObjects.IdManagerClass;
import gameObjects.category.CategoryClass;
import gameObjects.category.CategoryInstance;
import gameObjects.category.CategoryInstanceFactory;
import gameObjects.category.SimpleCategoryClass;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.entity.EntityInstanceFactory;
import gameObjects.entity.SimpleEntityClass;
import gameObjects.exception.*;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import gameObjects.player.PlayerInstance;
import gameObjects.player.PlayerInstanceFactory;
import gameObjects.sound.SimpleSoundClass;
import gameObjects.sound.SoundClass;
import gameObjects.sound.SoundInstance;
import gameObjects.sound.SoundInstanceFactory;
import gameObjects.tile.SimpleTileClass;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import gameObjects.tile.TileInstanceFactory;
import grids.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;


public class SimpleGameObjectsCRUD implements GameObjectsCRUDInterface {
    private int numRows;
    private int numCols;
    private ObservableMap<String, GameObjectClass> gameObjectClassMapByName;
    private ObservableMap<Integer, GameObjectClass> gameObjectClassMapById;
    private ObservableMap<Integer, GameObjectInstance> gameObjectInstanceMapById;


    private TileInstanceFactory myTileInstanceFactory;
    private EntityInstanceFactory myEntityInstanceFactory;
    private CategoryInstanceFactory myCategoryInstanceFactory;
    private SoundInstanceFactory mySoundInstanceFactory;
    private PlayerInstanceFactory myPlayerInstanceFactory;

    private IdManager myIdManager;

    public SimpleGameObjectsCRUD(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        gameObjectClassMapByName = FXCollections.observableHashMap();
        gameObjectClassMapById = FXCollections.observableHashMap();
        gameObjectInstanceMapById = FXCollections.observableHashMap();

        myIdManager = new IdManagerClass(getGameObjectClassFromMapFunc(), getGameObjectInstanceFromMapFunc());

        myTileInstanceFactory = instantiateTileInstanceFactory();
        myEntityInstanceFactory = instantiateEntityInstanceFactory();
        myCategoryInstanceFactory = instantiateCategoryInstanceFactory();
        mySoundInstanceFactory = instantiateSoundInstanceFactory();
        myPlayerInstanceFactory = instantiatePlayerInstanceFactory();

    }

//    public SimpleGameObjectsCRUD(int numRows, int numCols, ObservableMap<String, TileClass> tileClasses, ObservableMap<String, EntityClass> entityClasses) {
//        this(numRows, numCols);
//        tileClassMap = tileClasses;
//        entityClassMap = entityClasses;
//    }




    private TileInstanceFactory instantiateTileInstanceFactory() {
        TileInstanceFactory f = new TileInstanceFactory(
                numRows,
                numCols,
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
        return f;
    }

    @Override
    public TileClass createTileClass(String className) {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateClassException();
        }
        TileClass newTileClass = new SimpleTileClass(
                className,
                myTileInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newTileClass);
        return newTileClass;
    }

    @Override
    public TileClass getTileClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new NoTileClassException();
        }
        return (TileClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public TileInstance createTileInstance(String className, Point topLeftCoord) {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new NoTileClassException();
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != GameObjectType.TILE) {
            throw new InvalidClassException();
        }
        return myTileInstanceFactory.createInstance((TileClass) t, topLeftCoord);
    }

    @Override
    public TileInstance createTileInstance(TileClass tileClass, Point topLeftCoord) {
        return myTileInstanceFactory.createInstance(tileClass, topLeftCoord);
    }




    private EntityInstanceFactory instantiateEntityInstanceFactory() {
        EntityInstanceFactory f = new EntityInstanceFactory(
                // TODO
                myIdManager.verifyTileInstanceIdFunc(),
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
        return f;
    }

    @Override
    public EntityClass createEntityClass(String className) {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateClassException();
        }
        EntityClass newEntityClass = new SimpleEntityClass(
                className,
                myEntityInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newEntityClass);
        return newEntityClass;
    }

    @Override
    public EntityClass getEntityClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new NoEntityClassException();
        }
        return (EntityClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public EntityInstance createEntityInstance(String className, int tileId) {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new NoEntityClassException();
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != GameObjectType.ENTITY) {
            throw new InvalidClassException();
        }
        return myEntityInstanceFactory.createInstance((EntityClass) t, tileId);
    }

    @Override
    public EntityInstance createEntityInstance(EntityClass entityClass, int tileId) {
        return myEntityInstanceFactory.createInstance(entityClass, tileId);
    }


    private CategoryInstanceFactory instantiateCategoryInstanceFactory() {
        CategoryInstanceFactory f = new CategoryInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
        return f;
    }


    @Override
    public CategoryClass createCategoryClass(String className) {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateClassException();
        }
        CategoryClass newCategoryClass = new SimpleCategoryClass(
                className,
                myCategoryInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newCategoryClass);
        return newCategoryClass;
    }

    @Override
    public CategoryClass getCategoryClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new NoCategoryClassException();
        }
        return (CategoryClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public CategoryInstance createCategoryInstance(String className) {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new NoCategoryClassException();
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != GameObjectType.CATEGORY) {
            throw new InvalidClassException();
        }
        return myCategoryInstanceFactory.createInstance((CategoryClass) t);
    }

    @Override
    public CategoryInstance createCategoryInstance(CategoryClass categoryClass) {
        return myCategoryInstanceFactory.createInstance(categoryClass);
    }






    private SoundInstanceFactory instantiateSoundInstanceFactory() {
        SoundInstanceFactory f = new SoundInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
        return f;
    }

    @Override
    public SoundClass createSoundClass(String className) {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateClassException();
        }
        SoundClass newSoundClass = new SimpleSoundClass(
                className,
                mySoundInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newSoundClass);
        return newSoundClass;
    }

    @Override
    public SoundClass getSoundClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new NoSoundClassException();
        }
        return (SoundClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public SoundInstance createSoundInstance(String className) {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new NoSoundClassException();
        }
        GameObjectClass c = gameObjectClassMapByName.get(className);
        if (c.getType() != GameObjectType.SOUND) {
            throw new InvalidClassException();
        }
        return mySoundInstanceFactory.createInstance((SoundClass) c);
    }

    @Override
    public SoundInstance createSoundInstance(SoundClass soundClass) {
        return mySoundInstanceFactory.createInstance(soundClass);
    }



    private PlayerInstanceFactory instantiatePlayerInstanceFactory() {
        PlayerInstanceFactory f = new PlayerInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
        return f;

    }


    @Override
    public PlayerInstance createPlayerInstance(String playerName) {
        return null;
    }






    @Override
    public GameObjectClass getGameObjectClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new NoGameObjectClassException();
        }
        return gameObjectClassMapByName.get(className);
    }

    @Override
    public GameObjectInstance getGameObjectInstance(int instanceId) {
        if (!gameObjectInstanceMapById.containsKey(instanceId)) {
            throw new NoGameObjectClassException();
        }
        return gameObjectInstanceMapById.get(instanceId);
    }

    @Override
    public Collection<GameObjectInstance> getAllInstances(String className) {
        Set<GameObjectInstance> instancesSet = new HashSet<>();
        for (Map.Entry<Integer, GameObjectInstance> entry : gameObjectInstanceMapById.entrySet()) {
            if (entry.getValue().getClassName().getName().equals(className)) {
                instancesSet.add(entry.getValue());
            }
        }
        return instancesSet;
    }

    @Override
    public Collection<GameObjectInstance> getAllInstances(GameObjectClass gameObjectClass) {
        String className = gameObjectClass.getClassName().getValue();
        return getAllInstances(className);
    }

    // TODO
    @Override
    public Collection<GameObjectInstance> getAllInstancesAtPoint(int x, int y) {
        return null;
    }

    // TODO
    @Override
    public Collection<GameObjectInstance> getAllInstancesAtPoint(Point point) {
        return null;
    }


    /**
     * This method deletes the GameObjectClasses with the input String name. It scans through all possible maps of the String -> GameObjectClass.
     *
     * @param className : The name of the GameObjectClass to be deleted.
     * @return: true if the GameObjectClass is successfully deleted and false otherwise.
     */
    @Override
    public boolean deleteGameObjectClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            return false;
        }
        GameObjectClass gameObjectClass = gameObjectClassMapByName.get(className);
        removeGameObjectClassFromMaps(gameObjectClass);
        removeAllGameObjectInstancesFromMap(className);
        return true;
    }

    @Override
    public boolean deleteGameObjectClass(int classId) {
        if (!gameObjectClassMapById.containsKey(classId)) {
            return false;
        }
        GameObjectClass gameObjectClass = gameObjectClassMapById.get(classId);
        removeGameObjectClassFromMaps(gameObjectClass);
        removeAllGameObjectInstancesFromMap(gameObjectClass.getClassName().getValue());
        return true;
    }

    @Override
    public boolean deleteGameObjectClass(GameObjectClass gameObjectClass) {
        removeGameObjectClassFromMaps(gameObjectClass);
        return removeAllGameObjectInstancesFromMap(gameObjectClass.getClassName().getValue());
    }

    @Override
    public boolean deleteGameObjectInstance(int instanceId) {
        if (!gameObjectInstanceMapById.containsKey(instanceId)) {
            return false;
        }
        removeGameObjectInstanceFromMap(instanceId);
        return true;
    }

    /**
     * Delete all instances currently in the CRUD.
     */
    @Override
    public void deleteAllInstances() {
        gameObjectInstanceMapById.keySet().forEach(this::removeGameObjectInstanceFromMap);
    }

    @Override
    public boolean changeGameObjectClassName(String oldClassName, String newClassName) {
        if (!gameObjectClassMapByName.containsKey(oldClassName)) {
            return false;
        }
        if (newClassName.equals(oldClassName)) {
            return true;
        }
        GameObjectClass gameObjectClass = gameObjectClassMapByName.get(oldClassName);
        gameObjectClass.setClassName(newClassName);
        gameObjectClassMapByName.put(newClassName, gameObjectClass);
        gameObjectClassMapByName.remove(oldClassName);
        changeAllGameObjectInstancesClassName(oldClassName, newClassName);
        return true;
    }

    private void addGameObjectClassToMaps(GameObjectClass g) {
        myIdManager.requestClassIdFunc().accept(g);
        gameObjectClassMapByName.put(g.getClassName().getValue(), g);
        gameObjectClassMapById.put(g.getClassId().getValue(), g);
    }

    private void removeGameObjectClassFromMaps(GameObjectClass g) {
        myIdManager.returnClassIdFunc().accept(g);
        gameObjectClassMapByName.remove(g.getClassName().getValue());
        gameObjectClassMapById.remove(g.getClassId().getValue());
    }

    private void removeGameObjectInstanceFromMap(int instanceId) {
        GameObjectInstance gameObjectInstance = gameObjectInstanceMapById.get(instanceId);
        myIdManager.returnInstanceIdFunc().accept(gameObjectInstance);
        gameObjectInstanceMapById.remove(instanceId);
    }


    private boolean removeAllGameObjectInstancesFromMap(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            return false;
        }
        for (Map.Entry<Integer, GameObjectInstance> e : gameObjectInstanceMapById.entrySet()) {
            if (e.getValue().getClassName().equals(className)) {
                removeGameObjectInstanceFromMap(e.getKey());
            }
        }
        return true;
    }

    private void changeAllGameObjectInstancesClassName(String oldClassName, String newClassName) {
        for (Map.Entry<Integer, GameObjectInstance> e : gameObjectInstanceMapById.entrySet()) {
            if (e.getValue().getClassName().equals(oldClassName)) {
                e.getValue().setClassName(newClassName);
            }
        }
    }

    private Function<Integer, GameObjectClass> getGameObjectClassFromMapFunc() {
        return classId -> {
            if (!gameObjectClassMapById.containsKey(classId)) {
                throw new NoGameObjectClassException();
            }
            return gameObjectClassMapById.get(classId);
        };
    }


    private Function<Integer, GameObjectInstance> getGameObjectInstanceFromMapFunc() {
        return instanceId -> {
            if (!gameObjectInstanceMapById.containsKey(instanceId)) {
                throw new NoGameObjectInstanceException();
            }
            return gameObjectInstanceMapById.get(instanceId);
        };
    }


    private Function<String, Collection<GameObjectInstance>> getAllInstancesFunc() {
        return this::getAllInstances;
    }



    private Function<Integer, Boolean> deleteGameObjectInstanceFunc() {
        return this::deleteGameObjectInstance;
    }

    private BiConsumer<String, String> changeGameObjectClassNameFunc() {
        return this::changeGameObjectClassName;
    }

    private Consumer<GameObjectInstance> addGameObjectInstanceToMapFunc() {
        return gameObjectInstance -> {
            int instanceId = gameObjectInstance.getInstanceId().getValue();
            if (instanceId == 0) {
                throw new InvalidIdException();
            }
            gameObjectInstanceMapById.put(instanceId, gameObjectInstance);
        };
    }





    // TODO: propagate changes
    @Override
    public void setDimension(int width, int height) {
        numCols = width;
        numRows = height;
    }

    public int getWidth() { return numCols; }
    public int getHeight() { return numRows; }








    /**
     * Getters
     *
     * @return ObservableList of things
     */
    @Override
    public ObservableList<EntityClass> getEntityClasses() {
        ObservableList<EntityClass> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.ENTITY) {
                ret.add((EntityClass) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<TileClass> getTileClasses() {
        ObservableList<TileClass> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.TILE) {
                ret.add((TileClass) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<CategoryClass> getCategoryClasses() {
        ObservableList<CategoryClass> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.CATEGORY) {
                ret.add((CategoryClass) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<SoundClass> getSoundClasses() {
        ObservableList<SoundClass> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.SOUND) {
                ret.add((SoundClass) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<EntityInstance> getEntityInstances() {
        ObservableList<EntityInstance> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.ENTITY) {
                ret.add((EntityInstance) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<TileInstance> getTileInstances() {
        ObservableList<TileInstance> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.TILE) {
                ret.add((TileInstance) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<CategoryInstance> getCategoryInstances() {
        ObservableList<CategoryInstance> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.CATEGORY) {
                ret.add((CategoryInstance) objectClass);
            }
        }
        return ret;
    }

    @Override
    public ObservableList<SoundInstance> getSoundInstances() {
        ObservableList<SoundInstance> ret = FXCollections.observableArrayList();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == GameObjectType.SOUND) {
                ret.add((SoundInstance) objectClass);
            }
        }
        return ret;
    }



}
