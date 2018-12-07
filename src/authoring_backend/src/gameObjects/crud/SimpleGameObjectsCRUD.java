package gameObjects.crud;

import authoringUtils.exception.*;
import gameObjects.*;
import gameObjects.category.*;
import gameObjects.entity.*;
import gameObjects.gameObject.*;
import gameObjects.player.*;
import gameObjects.sound.*;
import gameObjects.tile.*;
import gameObjects.turn.*;
import grids.Point;
import javafx.collections.*;

import java.util.*;
import java.util.function.Function;

public class SimpleGameObjectsCRUD implements GameObjectsCRUDInterface {
    private static final String DEFAULT_PLAYER_NAME = "$default$";

    private int numRows;
    private int numCols;
    private ObservableMap<String, GameObjectClass> gameObjectClassMapByName;
    private ObservableMap<Integer, GameObjectClass> gameObjectClassMapById;
    private ObservableMap<Integer, GameObjectInstance> gameObjectInstanceMapById;
    private ObservableMap<String, Turn> turnMap;

    private TileInstanceFactory myTileInstanceFactory;
    private EntityInstanceFactory myEntityInstanceFactory;
    private CategoryInstanceFactory myCategoryInstanceFactory;
    private SoundInstanceFactory mySoundInstanceFactory;
    private PlayerInstanceFactory myPlayerInstanceFactory;

    private IdManager myIdManager;
    private PlayerInstance defaultPlayer;

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

        defaultPlayer = createPlayerInstance(DEFAULT_PLAYER_NAME);
    }

    private TileInstanceFactory instantiateTileInstanceFactory() {
        return new TileInstanceFactory(
                numRows,
                numCols,
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
    }

    @Override
    public TileClass createTileClass(String className)
            throws DuplicateGameObjectClassException {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateGameObjectClassException();
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
    public TileClass getTileClass(String className)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException("Tile");
        }
        return (TileClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public TileInstance createTileInstance(String className, Point topLeftCoord)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new GameObjectClassNotFoundException("Tile");
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != GameObjectType.TILE) {
            throw new GameObjectTypeException(className, "Tile");
        }
        try {
            return myTileInstanceFactory.createInstance((TileClass) t, topLeftCoord);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public TileInstance createTileInstance(TileClass tileClass, Point topLeftCoord)
            throws GameObjectTypeException {
        try {
            return myTileInstanceFactory.createInstance(tileClass, topLeftCoord);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }


    private EntityInstanceFactory instantiateEntityInstanceFactory() {
        return new EntityInstanceFactory(
                // TODO
                myIdManager.verifyTileInstanceIdFunc(),
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc(),
                // TODO: separate function with error checking and handling
                (entityID, playerID) -> ((PlayerInstance) gameObjectInstanceMapById.get(playerID)).addEntity(entityID)
        );
    }

    @Override
    public EntityClass createEntityClass(String className)
            throws DuplicateGameObjectClassException {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateGameObjectClassException();
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
    public EntityClass getEntityClass(String className)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException("Entity");
        }
        return (EntityClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public EntityInstance createEntityInstance(String className, int playerID)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new GameObjectClassNotFoundException("Entity");
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != GameObjectType.ENTITY) {
            throw new GameObjectTypeException("className", "Entity");
        }
        try {
            return myEntityInstanceFactory.createInstance((EntityClass) t, playerID);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public EntityInstance createEntityInstance(EntityClass entityClass, int playerID)
            throws GameObjectTypeException {
        try {
            return myEntityInstanceFactory.createInstance(entityClass, playerID);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }


    private CategoryInstanceFactory instantiateCategoryInstanceFactory() {
        return new CategoryInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
    }


    @Override
    public CategoryClass createCategoryClass(String className)
            throws DuplicateGameObjectClassException {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateGameObjectClassException();
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
    public CategoryClass getCategoryClass(String className)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException("Category");
        }
        return (CategoryClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public CategoryInstance createCategoryInstance(String className)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new GameObjectClassNotFoundException("Category");
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != GameObjectType.CATEGORY) {
            throw new GameObjectTypeException(className, "Category");
        }
        try {
            return myCategoryInstanceFactory.createInstance((CategoryClass) t);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public CategoryInstance createCategoryInstance(CategoryClass categoryClass)
            throws GameObjectTypeException {
        try {
            return myCategoryInstanceFactory.createInstance(categoryClass);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }


    private SoundInstanceFactory instantiateSoundInstanceFactory() {
        return new SoundInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
    }

    @Override
    public SoundClass createSoundClass(String className)
            throws DuplicateGameObjectClassException {
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateGameObjectClassException();
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
    public SoundClass getSoundClass(String className)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException("Sound");
        }
        return (SoundClass) gameObjectClassMapByName.get(className);
    }

    @Override
    public SoundInstance createSoundInstance(String className)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        if (!gameObjectClassMapByName.containsKey(className) ) {
            throw new GameObjectClassNotFoundException("Sound");
        }
        GameObjectClass c = gameObjectClassMapByName.get(className);
        if (c.getType() != GameObjectType.SOUND) {
            throw new GameObjectTypeException(className, "Sound");
        }
        try {
            return mySoundInstanceFactory.createInstance((SoundClass) c);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SoundInstance createSoundInstance(SoundClass soundClass)
            throws GameObjectTypeException {
        try {
            return mySoundInstanceFactory.createInstance(soundClass);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }



    private PlayerInstanceFactory instantiatePlayerInstanceFactory() {
        return new PlayerInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());

    }

    @Override
    public PlayerInstance createPlayerInstance(String playerName) {
        try {
            return myPlayerInstanceFactory.createInstance(playerName);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Turn createTurn(String phaseName) {
        Turn t = new SimpleTurn(phaseName);
        turnMap.put(phaseName, t);
        return t;
    }

    @Override
    public Turn getTurn(String phaseName)
            throws TurnNotFoundException {
        if (!turnMap.containsKey(phaseName)) {
            throw new TurnNotFoundException("Turn not found in map");
        }
        return turnMap.get(phaseName);
    }

    @Override
    public boolean deleteTurn(String phaseName) {
        if (!turnMap.containsKey(phaseName)) {
            return false;
        }
        turnMap.remove(phaseName);
        return true;
    }


    @Override
    public GameObjectClass getGameObjectClass(String className)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException("GameObject");
        }
        return gameObjectClassMapByName.get(className);
    }

    @Override
    public GameObjectInstance getGameObjectInstance(int instanceId)
            throws GameObjectInstanceNotFoundException {
        if (!gameObjectInstanceMapById.containsKey(instanceId)) {
            throw new GameObjectInstanceNotFoundException("GameObject");
        }
        return gameObjectInstanceMapById.get(instanceId);
    }

    @Override
    public Collection<GameObjectClass> getAllClasses() {
        return gameObjectClassMapById.values();
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
     * @return true if the GameObjectClass is successfully deleted and false otherwise.
     */
    @Override
    public boolean deleteGameObjectClass(String className) {
        if (!gameObjectClassMapByName.containsKey(className)) {
            return false;
        }
        GameObjectClass gameObjectClass = gameObjectClassMapByName.get(className);
        try {
            removeGameObjectClassFromMaps(gameObjectClass);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        try {
            removeAllGameObjectInstancesFromMap(className);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteGameObjectClass(int classId) {
        if (!gameObjectClassMapById.containsKey(classId)) {
            return false;
        }
        GameObjectClass gameObjectClass = gameObjectClassMapById.get(classId);
        try {
            removeGameObjectClassFromMaps(gameObjectClass);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        try {
            removeAllGameObjectInstancesFromMap(gameObjectClass.getClassName().getValue());
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteGameObjectClass(GameObjectClass gameObjectClass) {
        try {
            removeGameObjectClassFromMaps(gameObjectClass);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        try {
            return removeAllGameObjectInstancesFromMap(gameObjectClass.getClassName().getValue());
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteGameObjectInstance(int instanceId) {
        if (!gameObjectInstanceMapById.containsKey(instanceId)) {
            return false;
        }
        try {
            removeGameObjectInstanceFromMap(instanceId);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Delete all instances currently in the CRUD.
     */
    @Override
    public void deleteAllInstances() {
        for (Integer integer : gameObjectInstanceMapById.keySet()) {
            try {
                removeGameObjectInstanceFromMap(integer);
            } catch (InvalidIdException e) {
                // TODO
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is a convenient method that creates different GameObjectClasses, depending on the class name and the gameObjectType.
     *
     * @param name           : The name of the GameObjectClass to be created.
     * @param gameObjectType : The GameObjectType that determines the type of GameObjectClass that is to be created.
     * @return A Subclass of GameObjectClass depending on the String name and the GameObjectType.
     * @throws DuplicateGameObjectClassException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E extends GameObjectClass> E createGameObjectClass(String name, GameObjectType gameObjectType) throws DuplicateGameObjectClassException {
        switch (gameObjectType) {
            case CATEGORY:
                return (E) createCategoryClass(name);
            case SOUND:
                return (E) createSoundClass(name);
            case TILE:
                return (E) createTileClass(name);
            case ENTITY:
                return (E) createEntityClass(name);
            case UNSPECIFIED:
                // TODO
                break;
            case PLAYER:
                // TODO
        }
        return null;
    }

    @Override
    public boolean changeGameObjectClassName(String oldClassName, String newClassName)
            throws InvalidOperationException {
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

    private void removeGameObjectClassFromMaps(GameObjectClass g)
            throws InvalidIdException {
        myIdManager.returnClassIdFunc().accept(g);
        gameObjectClassMapByName.remove(g.getClassName().getValue());
        gameObjectClassMapById.remove(g.getClassId().getValue());
    }

    private void removeGameObjectInstanceFromMap(int instanceId)
            throws InvalidIdException {
        GameObjectInstance gameObjectInstance = gameObjectInstanceMapById.get(instanceId);
        myIdManager.returnInstanceIdFunc().accept(gameObjectInstance);
        gameObjectInstanceMapById.remove(instanceId);
    }


    private boolean removeAllGameObjectInstancesFromMap(String className)
            throws InvalidIdException {
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

    private void changeAllGameObjectInstancesClassName(String oldClassName, String newClassName)
            throws InvalidOperationException {
        for (Map.Entry<Integer, GameObjectInstance> e : gameObjectInstanceMapById.entrySet()) {
            if (e.getValue().getClassName().equals(oldClassName)) {
                e.getValue().setClassName(newClassName);
            }
        }
    }

    private Function<Integer, GameObjectClass> getGameObjectClassFromMapFunc() {
        return classId -> gameObjectClassMapById.get(classId);
    }


    private Function<Integer, GameObjectInstance> getGameObjectInstanceFromMapFunc() {
        return instanceId -> gameObjectInstanceMapById.get(instanceId);
    }


    private Function<String, Collection<GameObjectInstance>> getAllInstancesFunc() {
        return this::getAllInstances;
    }



    private Function<Integer, Boolean> deleteGameObjectInstanceFunc() {
        return this::deleteGameObjectInstance;
    }

    private ThrowingBiConsumer<String, String, InvalidOperationException> changeGameObjectClassNameFunc() {
        return this::changeGameObjectClassName;
    }

    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addGameObjectInstanceToMapFunc() {
        return gameObjectInstance -> {
            int instanceId = gameObjectInstance.getInstanceId().getValue();
            if (instanceId == 0) {
                throw new InvalidIdException("GameObject Instance has an invalid Id");
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
    public Iterable<EntityClass> getEntityClasses() {
        return getSpecificClasses(GameObjectType.ENTITY);
    }

    @Override
    public Iterable<TileClass> getTileClasses() {
        return getSpecificClasses(GameObjectType.TILE);
    }

    @Override
    public Iterable<CategoryClass> getCategoryClasses() {
        return getSpecificClasses(GameObjectType.CATEGORY);
    }

    @Override
    public Iterable<SoundClass> getSoundClasses() {
        return getSpecificClasses(GameObjectType.SOUND);
    }

    private  <T extends GameObjectClass> Set<T> getSpecificClasses(GameObjectType objectType){
        Set<T> ret = new HashSet<>();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == objectType) {
                ret.add((T) objectClass);
            }
        }
        return ret;
    }

    private  <T extends GameObjectInstance> Set<T> getSpecificInstances(GameObjectType objectType){
        Set<T> ret = new HashSet<>();
        for (GameObjectInstance objectInstance : gameObjectInstanceMapById.values()) {
            if (objectInstance.getType() == objectType) {
                ret.add((T) objectInstance);
            }
        }
        return ret;
    }

    @Override
    public Iterable<EntityInstance> getEntityInstances() {
        return getSpecificInstances(GameObjectType.ENTITY);
    }

    @Override
    public Iterable<TileInstance> getTileInstances() {
        return getSpecificInstances(GameObjectType.TILE);
    }

    @Override
    public Iterable<PlayerInstance> getPlayerInstances() {
        ObservableList<PlayerInstance> ret = FXCollections.observableArrayList();
        for (GameObjectInstance objectInstance : gameObjectInstanceMapById.values()) {
            if (objectInstance.getType() == GameObjectType.PLAYER &&
                    !objectInstance.getInstanceName().get().equals(DEFAULT_PLAYER_NAME)) {
                ret.add((PlayerInstance) objectInstance);
            }
        }
        return ret;
    }

    @Override
    public int getDefaultPlayerID() { return defaultPlayer.getInstanceId().get(); }

    @Override
    public Iterable<CategoryInstance> getCategoryInstances() {
        return getSpecificInstances(GameObjectType.CATEGORY);
    }

    @Override
    public Iterable<SoundInstance> getSoundInstances() {
        return getSpecificInstances(GameObjectType.SOUND);
    }
}