package gameObjects.crud;

import authoringUtils.exception.*;
import conversion.authoring.SavedEntityDB;
import conversion.authoring.SerializerCRUD;
import gameObjects.IdManager;
import gameObjects.IdManagerClass;
import gameObjects.ThrowingBiConsumer;
import gameObjects.ThrowingConsumer;
import gameObjects.category.CategoryClass;
import gameObjects.category.CategoryInstance;
import gameObjects.category.CategoryInstanceFactory;
import gameObjects.category.SimpleCategoryClass;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.entity.EntityInstanceFactory;
import gameObjects.entity.SimpleEntityClass;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;
import gameObjects.player.PlayerInstanceFactory;
import gameObjects.player.SimplePlayerClass;
import gameObjects.tile.SimpleTileClass;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import gameObjects.tile.TileInstanceFactory;
import grids.Point;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleGameObjectsCRUD implements GameObjectsCRUDInterface {
    private static final String ROOT_NAME = "Game Objects";

    private int numCols;
    private int numRows;
    private String bgmPath;
    private Map<String, GameObjectClass> gameObjectClassMapByName;
    private Map<Integer, GameObjectClass> gameObjectClassMapById;
    private Map<Integer, GameObjectInstance> gameObjectInstanceMapById;

    private TileInstanceFactory myTileInstanceFactory;
    private EntityInstanceFactory myEntityInstanceFactory;
    private CategoryInstanceFactory myCategoryInstanceFactory;
    //    private SoundInstanceFactory mySoundInstanceFactory;
    private PlayerInstanceFactory myPlayerInstanceFactory;

    private IdManager myIdManager;

    public SimpleGameObjectsCRUD(int numCols, int numRows, boolean fromXML) {
        this.numCols = numCols;
        this.numRows = numRows;
        gameObjectClassMapByName = new HashMap<>();
        gameObjectClassMapById = new HashMap<>();
        gameObjectInstanceMapById = new HashMap<>();

        myIdManager = new IdManagerClass(
                getGameObjectClassFromMapFunc(),
                getGameObjectInstanceFromMapFunc(),
                gameObjectClassMapById,
                gameObjectInstanceMapById
        );

        myTileInstanceFactory = instantiateTileInstanceFactory();
        myEntityInstanceFactory = instantiateEntityInstanceFactory();
        myCategoryInstanceFactory = instantiateCategoryInstanceFactory();
//        mySoundInstanceFactory = instantiateSoundInstanceFactory();
        myPlayerInstanceFactory = instantiatePlayerInstanceFactory();

        if (fromXML) return;

        try {
            createCategoryClass(ROOT_NAME);

            createCategoryClass("ENTITY");
            createCategoryClass("TILE");
//            createCategoryClass("SOUND");
            createCategoryClass("PLAYER");

            createEntityClass("O");
            createEntityClass("X");
            createTileClass("Default Grid");
            createPlayerClass("Default Player");
//            createSoundClass("Sound file");
        } catch (DuplicateGameObjectClassException e) {
            // TODO: proper error handling
            e.printStackTrace();
        }
    }

    public SimpleGameObjectsCRUD(SavedEntityDB saved) {
        this(saved.numCols(), saved.numRows(), true);
        bgmPath = saved.bgmPath();
        for (var c : saved.classes()) {
            switch (c.getType()) {
                case CATEGORY:
                    createCategoryClass((CategoryClass) c);
                    break;
                case PLAYER:
                    createPlayerClass((PlayerClass) c);
                    break;
                case ENTITY:
                    createEntityClass((EntityClass) c);
                    break;
                case TILE:
                    createTileClass((TileClass) c);
                    break;
//                case SOUND: createSoundClass((SoundClass) c); break;
                case UNSPECIFIED: // fuck it. honestly
            }
        }
        for (var i : saved.instances()) {
            try {
                addGameObjectInstanceToMapFunc().accept(i);
            } catch (InvalidIdException e) {
                e.printStackTrace();
            } // not gonna happen in normal... circumstances
        }
    }

    @Override
    public int getNumCols() {
        return numCols;
    }

    @Override
    public int getNumRows() {
        return numRows;
    }

    private TileInstanceFactory instantiateTileInstanceFactory() {
        return new TileInstanceFactory(
                numRows,
                numCols,
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
    }

    private EntityInstanceFactory instantiateEntityInstanceFactory() {
        return new EntityInstanceFactory(
                // TODO
                myIdManager.verifyTileInstanceIdFunc(),
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc()
        );
    }

    private CategoryInstanceFactory instantiateCategoryInstanceFactory() {
        return new CategoryInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());
    }

    private PlayerInstanceFactory instantiatePlayerInstanceFactory() {
        return new PlayerInstanceFactory(
                myIdManager.requestInstanceIdFunc(),
                addGameObjectInstanceToMapFunc());

    }

//    private SoundInstanceFactory instantiateSoundInstanceFactory() {
//        return new SoundInstanceFactory(
//                myIdManager.requestInstanceIdFunc(),
//                addGameObjectInstanceToMapFunc());
//    }

    private void checkDuplicate(String className)
            throws DuplicateGameObjectClassException {
        System.out.println(gameObjectClassMapByName.keySet());
        if (gameObjectClassMapByName.containsKey(className)) {
            throw new DuplicateGameObjectClassException();
        }
    }

    @Override
    public TileClass createTileClass(String className)
            throws DuplicateGameObjectClassException {
        checkDuplicate(className);
        TileClass newTileClass = new SimpleTileClass(
                className,
                myTileInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newTileClass);
        return newTileClass;
    }

    private TileClass createTileClass(TileClass tile) {
        tile.equipContext(
                myTileInstanceFactory,
                this::changeAllGameObjectInstancesClassName,
                this::getAllInstances,
                this::deleteGameObjectInstance
        );
        addGameObjectClassToMaps(tile);
        return tile;
    }

    @Override
    public CategoryClass createCategoryClass(String className)
            throws DuplicateGameObjectClassException {
        checkDuplicate(className);
        CategoryClass newCategoryClass = new SimpleCategoryClass(
                className,
                myCategoryInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newCategoryClass);
        return newCategoryClass;
    }

    private CategoryClass createCategoryClass(CategoryClass category) {
        category.equipContext(
                myCategoryInstanceFactory,
                this::changeAllGameObjectInstancesClassName,
                this::getAllInstances,
                this::deleteGameObjectInstance
        );
        addGameObjectClassToMaps(category);
        return category;
    }

//    @Override
//    public SoundClass createSoundClass(String className)
//            throws DuplicateGameObjectClassException {
//        checkDuplicate(className);
//        SoundClass newSoundClass = new SimpleSoundClass(
//                className,
//                mySoundInstanceFactory,
//                changeGameObjectClassNameFunc(),
//                getAllInstancesFunc(),
//                deleteGameObjectInstanceFunc());
//        addGameObjectClassToMaps(newSoundClass);
//        return newSoundClass;
//    }
//
//    private SoundClass createSoundClass(SoundClass sound) {
//        sound.equipContext(
//            mySoundInstanceFactory,
//            this::changeAllGameObjectInstancesClassName,
//            this::getAllInstances,
//            this::deleteGameObjectInstance
//        );
//        addGameObjectClassToMaps(sound);
//        return sound;
//    }

    @Override
    public EntityClass createEntityClass(String className)
            throws DuplicateGameObjectClassException {
        checkDuplicate(className);
        EntityClass newEntityClass = new SimpleEntityClass(
                className,
                myEntityInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newEntityClass);
        return newEntityClass;
    }

    private EntityClass createEntityClass(EntityClass entity) {
        entity.equipContext(
                myEntityInstanceFactory,
                this::changeAllGameObjectInstancesClassName,
                this::getAllInstances,
                this::deleteGameObjectInstance
        );
        addGameObjectClassToMaps(entity);
        return entity;
    }

    @Override
    public PlayerClass createPlayerClass(String className)
            throws DuplicateGameObjectClassException {
        checkDuplicate(className);
        PlayerClass newPlayerClass = new SimplePlayerClass(
                className,
                myPlayerInstanceFactory,
                changeGameObjectClassNameFunc(),
                getAllInstancesFunc(),
                deleteGameObjectInstanceFunc());
        addGameObjectClassToMaps(newPlayerClass);
        return newPlayerClass;
    }

    private PlayerClass createPlayerClass(PlayerClass player) {
        player.equipContext(
                myPlayerInstanceFactory,
                this::changeAllGameObjectInstancesClassName,
                this::getAllInstances,
                this::deleteGameObjectInstance
        );
        addGameObjectClassToMaps(player);
        return player;
    }

    /**
     * Reformate for example TILE to Tile.
     *
     * @param str original string
     * @return reformatted string
     */
    private String reformat(String str) {
        return str.charAt(0) + str.substring(1).toLowerCase();
    }

    private GameObjectClass getSpecificClass(String className, GameObjectType objectType)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException(reformat(objectType.name()));
        }
        return gameObjectClassMapByName.get(className);
    }

    @Override
    public TileClass getTileClass(String className)
            throws GameObjectClassNotFoundException {
        return (TileClass) getSpecificClass(className, GameObjectType.TILE);
    }

    @Override
    public EntityClass getEntityClass(String className)
            throws GameObjectClassNotFoundException {
        return (EntityClass) getSpecificClass(className, GameObjectType.ENTITY);
    }

//    @Override
//    public SoundClass getSoundClass(String className)
//            throws GameObjectClassNotFoundException {
//        return (SoundClass) getSpecificClass(className, GameObjectType.SOUND);
//    }

    @Override
    public PlayerClass getPlayerClass(String className)
            throws GameObjectClassNotFoundException {
        return (PlayerClass) getSpecificClass(className, GameObjectType.PLAYER);
    }


    @Override
    public CategoryClass getCategoryClass(String className)
            throws GameObjectClassNotFoundException {
        return (CategoryClass) getSpecificClass(className, GameObjectType.CATEGORY);
    }

    private <T extends GameObjectInstance> T checkExist(String className, GameObjectType objectType)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException(reformat(objectType.name()));
        }
        GameObjectClass t = gameObjectClassMapByName.get(className);
        if (t.getType() != objectType) {
            throw new GameObjectTypeException(className, objectType);
        }
        return (T) t;
    }

    @Override
    public TileInstance createTileInstance(String className, Point topLeftCoord)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        TileClass t = checkExist(className, GameObjectType.TILE);
        return createTileInstance(t, topLeftCoord);
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

    @Override
    public EntityInstance createEntityInstance(String className, Point point)
            throws GameObjectClassNotFoundException, GameObjectTypeException {

        EntityClass t = checkExist(className, GameObjectType.ENTITY);
        return createEntityInstance(t, point);
    }

    @Override
    public EntityInstance createEntityInstance(EntityClass entityClass, Point point)
            throws GameObjectTypeException {
        try {
            return myEntityInstanceFactory.createInstance(entityClass, point);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CategoryInstance createCategoryInstance(String className)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        CategoryClass t = checkExist(className, GameObjectType.CATEGORY);
        return createCategoryInstance(t);
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

//    @Override
//    public SoundInstance createSoundInstance(String className)
//            throws GameObjectClassNotFoundException, GameObjectTypeException {
//        SoundClass t = checkExist(className, GameObjectType.SOUND);
//        return createSoundInstance(t);
//    }
//
//    @Override
//    public SoundInstance createSoundInstance(SoundClass soundClass)
//            throws GameObjectTypeException {
//        try {
//            return mySoundInstanceFactory.createInstance(soundClass);
//        } catch (InvalidIdException e) {
//            // TODO
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public PlayerInstance createPlayerInstance(String className)
            throws GameObjectClassNotFoundException, GameObjectTypeException {
        PlayerClass t = checkExist(className, GameObjectType.PLAYER);
        return createPlayerInstance(t);
    }

    @Override
    public PlayerInstance createPlayerInstance(PlayerClass playerClass)
            throws GameObjectTypeException {
        try {
            return myPlayerInstanceFactory.createInstance(playerClass);
        } catch (InvalidIdException e) {
            // TODO
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends GameObjectClass> T getGameObjectClass(String className)
            throws GameObjectClassNotFoundException {
        if (!gameObjectClassMapByName.containsKey(className)) {
            throw new GameObjectClassNotFoundException("GameObject");
        }
        return (T) gameObjectClassMapByName.get(className);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends GameObjectInstance> T getGameObjectInstance(int instanceId)
            throws GameObjectInstanceNotFoundException {
        if (!gameObjectInstanceMapById.containsKey(instanceId)) {
            throw new GameObjectInstanceNotFoundException("GameObject");
        }
        return (T) gameObjectInstanceMapById.get(instanceId);
    }

    @Override
    public Collection<GameObjectInstance> getAllInstances() {
        return gameObjectInstanceMapById.values();
    }

    @Override
    public Collection<GameObjectClass> getAllClasses() {
        return gameObjectClassMapById.values();
    }

    @Override
    public Collection<GameObjectInstance> getAllInstances(String className) {
        Set<GameObjectInstance> instancesSet = new HashSet<>();
        for (Map.Entry<Integer, GameObjectInstance> entry : gameObjectInstanceMapById.entrySet()) {

            if (entry.getValue().getClassName().equals(className)) {
                instancesSet.add(entry.getValue());
            }
        }
        return instancesSet;
    }

    @Override
    public Collection<GameObjectInstance> getAllInstances(GameObjectClass gameObjectClass) {
        String className = gameObjectClass.getClassName();
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
        return deleteGameObjectClass(gameObjectClassMapByName.get(className));
    }

    @Override
    public boolean deleteGameObjectClass(int classId) {
        if (!gameObjectClassMapById.containsKey(classId)) {
            return false;
        }
        return deleteGameObjectClass(gameObjectClassMapById.get(classId));
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
            return removeAllGameObjectInstancesFromMap(gameObjectClass.getClassName());
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
        gameObjectInstanceMapById.clear();
    }

    /**
     * This method is a convenient method that creates different GameObjectClasses, depending on the class name and the gameObjectType.
     *
     * @param gameObjectType : The GameObjectType that determines the type of GameObjectClass that is to be created.
     * @param name           : The name of the GameObjectClass to be created.
     * @return A Subclass of GameObjectClass depending on the String name and the GameObjectType.
     * @throws DuplicateGameObjectClassException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E extends GameObjectClass> E createGameObjectClass(GameObjectType gameObjectType, String name) throws DuplicateGameObjectClassException {
        switch (gameObjectType) {
            case CATEGORY:
                return (E) createCategoryClass(name);
//            case SOUND:
//                return (E) createSoundClass(name);
            case TILE:
                return (E) createTileClass(name);
            case ENTITY:
                return (E) createEntityClass(name);
            case UNSPECIFIED:
                // TODO
                break;
            case PLAYER:
                return (E) createPlayerClass(name);
        }
        return null;
    }

    /**
     * This method is a convenient method that creates concrete GameObjectInstances, depending on the type of GameObjectClass that is passed in or inferred from class name.
     *
     * @param name    : The String class name of the input GameObjectClass.
     * @param topleft : A Point representing the topleft of the GameObjectInstance deployed.
     * @return A concrete GameObjectInstance inferred from input.
     * @throws GameObjectTypeException
     * @throws GameObjectClassNotFoundException
     */
    @Override
    public <E extends GameObjectInstance> E createGameObjectInstance(String name, Point topleft) throws GameObjectClassNotFoundException, GameObjectTypeException {
        if (!gameObjectClassMapByName.containsKey(name)) {
            throw new GameObjectClassNotFoundException(String.format("%s is not a valid GameObjectClass", name));
        }
        GameObjectClass gameObjectClass = gameObjectClassMapByName.get(name);
        return createGameObjectInstance(gameObjectClass, topleft);
    }

    /**
     * This method is a convenient method that creates concrete GameObjectInstances, depending on the type of GameObjectClass that is passed in or inferred from class name.
     *
     * @param gameObjectClass : The input GameObjectClass.
     * @param topleft         : A Point representing the topleft of the GameObjectInstance deployed.
     * @return A concrete GameObjectInstance inferred from input.
     * @throws GameObjectTypeException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <E extends GameObjectInstance> E createGameObjectInstance(GameObjectClass gameObjectClass, Point topleft) throws GameObjectTypeException {
        switch (gameObjectClass.getType()) {
            case ENTITY:
                return (E) createEntityInstance((EntityClass) gameObjectClass, topleft);
            case PLAYER:
                // TODO: confirm Player API
                return (E) createPlayerInstance((PlayerClass) gameObjectClass);
            case UNSPECIFIED:
                // TODO
                break;
            case TILE:
                return (E) createTileInstance((TileClass) gameObjectClass, topleft);
//            case SOUND:
//                return (E) createSoundInstance((SoundClass) gameObjectClass);
            case CATEGORY:
                return (E) createCategoryInstance((CategoryClass) gameObjectClass);
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
        gameObjectClassMapByName.put(g.getClassName(), g);
        gameObjectClassMapById.put(g.getClassId(), g);
    }

    private void removeGameObjectClassFromMaps(GameObjectClass g)
            throws InvalidIdException {
        gameObjectClassMapByName.remove(g.getClassName());
        gameObjectClassMapById.remove(g.getClassId());
    }

    private void removeGameObjectInstanceFromMap(int instanceId)
            throws InvalidIdException {
        GameObjectInstance gameObjectInstance = gameObjectInstanceMapById.get(instanceId);
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
            int instanceId = gameObjectInstance.getInstanceId();
            if (instanceId == 0) {
                throw new InvalidIdException();
            }
            gameObjectInstanceMapById.put(instanceId, gameObjectInstance);
        };
    }

    @Override
    public void setDimension(int width, int height) {
        numCols = width;
        numRows = height;
    }

    public int getWidth() {
        return numCols;
    }

    public void setWidth(int width) {
        numCols = width;
    }

    public int getHeight() {
        return numRows;
    }

    public void setHeight(int height) {
        numRows = height;
    }

    @Override
    public Iterable<EntityClass> getEntityClasses() {
        return getSpecificClasses(GameObjectType.ENTITY);
    }

    @Override
    public String getBGMpath() {
        return bgmPath;
    }

    @Override
    public void setBGMpath(String path) {
        System.out.println(bgmPath);
        bgmPath = path;
    }

    @Override
    public Iterable<TileClass> getTileClasses() {
        return getSpecificClasses(GameObjectType.TILE);
    }

    @Override
    public Iterable<CategoryClass> getCategoryClasses() {
        return getSpecificClasses(GameObjectType.CATEGORY);
    }
//
//    @Override
//    public Iterable<SoundClass> getSoundClasses() { return getSpecificClasses(GameObjectType.SOUND); }

    @Override
    public Iterable<PlayerClass> getPlayerClasses() {
        return getSpecificClasses(GameObjectType.PLAYER);
    }

    private <T extends GameObjectClass> Set<T> getSpecificClasses(GameObjectType objectType) {
        Set<T> ret = new HashSet<>();
        for (GameObjectClass objectClass : gameObjectClassMapByName.values()) {
            if (objectClass.getType() == objectType) {
                ret.add((T) objectClass);
            }
        }
        return ret;
    }

    private <T extends GameObjectInstance> Set<T> getSpecificInstances(GameObjectType objectType) {
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
    @Deprecated
    public Iterable<PlayerInstance> getPlayerInstances() {
        return null;
    }

    @Override
    public Iterable<CategoryInstance> getCategoryInstances() {
        return getSpecificInstances(GameObjectType.CATEGORY);
    }

//    @Override
//    public Iterable<SoundInstance> getSoundInstances() {
//        return getSpecificInstances(GameObjectType.SOUND);
//    }

    @Override
    public Set<String> getPlayerNames(GameObjectInstance gameObjectInstance) {
        return gameObjectClassMapByName.values().stream()
                .filter(gameObjectClass -> gameObjectClass.getType() == GameObjectType.PLAYER)
                .filter(gameObjectClass -> ((PlayerClass) gameObjectClass).isOwnedByPlayer(gameObjectInstance))
                .map(GameObjectClass::getClassName)
                .collect(Collectors.toSet());
    }

    @Override
    public String toXML() {
        return new SerializerCRUD().getXMLString(this);
    }
}