package gameObjects.crud;

import authoringUtils.exception.*;
import gameObjects.category.CategoryClass;
import gameObjects.category.CategoryInstance;
import gameObjects.entity.EntityClass;
import gameObjects.entity.EntityInstance;
import gameObjects.gameObject.GameObjectClass;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import gameObjects.player.PlayerClass;
import gameObjects.player.PlayerInstance;
import gameObjects.tile.TileClass;
import gameObjects.tile.TileInstance;
import grids.Point;

import java.util.Collection;
import java.util.Set;

/**
 * This class encapsulates the Game Data for the GameObjects (Tiles and Entities). It provides methods to create, edit, and delete the Tile and Entity Classes.
 * It holds maps of GameObject Classes and GameObject Instances.
 *
 * @author Jason Zhou
 * @author Haotian Wang
 * @author Yunhao Qing
 */
public interface GameObjectsCRUDInterface {

    int getNumCols();

    int getNumRows();

    /**
     * This method creates a Tile Class and adds it to the map.
     *
     * @param className the name of the Tile Class to be created
     * @return the Tile Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    TileClass createTileClass(String className)
            throws DuplicateGameObjectClassException;

    /**
     * This method gets the Tile Class from the map.
     *
     * @param className the name of the Tile Class to be retrieved
     * @return the Tile Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Tile Class
     */
    TileClass getTileClass(String className)
            throws GameObjectClassNotFoundException;

    /**
     * @param className
     * @return
     */
    TileInstance createTileInstance(String className, Point topLeftCoord)
            throws GameObjectClassNotFoundException, GameObjectTypeException;


    /**
     * @param tileClass
     * @return
     */
    TileInstance createTileInstance(TileClass tileClass, Point topLeftCoord)
            throws GameObjectTypeException;

    /**
     * This method creates an Entity Class and adds it to the map.
     *
     * @param className the name of the Entity Class to be created
     * @return the Entity Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    EntityClass createEntityClass(String className)
            throws DuplicateGameObjectClassException;

    /**
     * This method gets the Entity Class from the map.
     *
     * @param className the name of the Entity Class to be retrieved
     * @return the Entity Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Entity Class
     */
    EntityClass getEntityClass(String className)
            throws GameObjectClassNotFoundException;

    /**
     * @param className
     * @return
     */
    EntityInstance createEntityInstance(String className, Point point)
            throws GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     * @param entityClass
     * @return
     */
    EntityInstance createEntityInstance(EntityClass entityClass, Point point)
            throws GameObjectTypeException;


    /**
     * This method creates an Category Class and adds it to the map.
     *
     * @param className the name of the Class to be created
     * @return the Category Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    CategoryClass createCategoryClass(String className)
            throws DuplicateGameObjectClassException;

    /**
     * This method gets the Class from the map.
     *
     * @param className the name of the Class to be retrieved
     * @return the Category Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Category Class
     */
    CategoryClass getCategoryClass(String className) throws GameObjectClassNotFoundException;

    /**
     * @param className
     * @return
     */
    CategoryInstance createCategoryInstance(String className) throws GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     * @param categoryClass
     * @return
     */
    CategoryInstance createCategoryInstance(CategoryClass categoryClass) throws GameObjectTypeException;

//    /**
//     * This method creates an Sound Class and adds it to the map.
//     * @param className the name of the Class to be created
//     * @return the Sound Class if there is no name collision and the method is successful
//     * @throws DuplicateGameObjectClassException if a class with the same name exists
//     */
//    SoundClass createSoundClass(String className) throws DuplicateGameObjectClassException;
//
//    /**
//     * This method gets the Sound Class from the map.
//     * @param className the name of the Class to be retrieved
//     * @return the Sound Class with the name
//     * @throws GameObjectClassNotFoundException if there is no such Sound Class
//     */
//    SoundClass getSoundClass(String className) throws GameObjectClassNotFoundException;

//    /**
//     *
//     * @param className
//     * @return
//     */
//    SoundInstance createSoundInstance(String className) throws GameObjectClassNotFoundException, GameObjectTypeException;
//
//    /**
//     *
//     * @param soundClass
//     * @return
//     */
//    SoundInstance createSoundInstance(SoundClass soundClass) throws GameObjectTypeException;

    PlayerClass createPlayerClass(String className)
            throws DuplicateGameObjectClassException;

    PlayerClass getPlayerClass(String className)
            throws GameObjectClassNotFoundException;

    PlayerInstance createPlayerInstance(String className)
            throws GameObjectClassNotFoundException, GameObjectTypeException;

    PlayerInstance createPlayerInstance(PlayerClass playerClass) throws GameObjectTypeException;

    /**
     * Sets the dimension of the entire grid
     *
     * @param width
     * @param height
     */
    void setDimension(int width, int height);



    /*
        New methods!!!
     */

    /**
     * Returns the GameObject Class with the specified name
     *
     * @param className
     * @return
     */
    <T extends GameObjectClass> T getGameObjectClass(String className) throws GameObjectClassNotFoundException;

    Collection<GameObjectClass> getAllClasses();

    /**
     * Returns the GameObject Instance with the specified name
     *
     * @param instanceId
     * @return
     */
    <T extends GameObjectInstance> T getGameObjectInstance(int instanceId) throws GameObjectInstanceNotFoundException;

    Collection<GameObjectInstance> getAllInstances();

    /**
     * @param className
     * @return
     */
    Collection<GameObjectInstance> getAllInstances(String className);

    /**
     * @param gameObjectClass
     * @return
     */
    Collection<GameObjectInstance> getAllInstances(GameObjectClass gameObjectClass);

    /**
     * @param x
     * @param y
     * @return
     */
    Collection<GameObjectInstance> getAllInstancesAtPoint(int x, int y);


    /**
     * @param point
     * @return
     */
    Collection<GameObjectInstance> getAllInstancesAtPoint(Point point);

    /**
     * @param oldClassName
     * @param newClassName
     * @return
     */
    boolean changeGameObjectClassName(String oldClassName, String newClassName) throws InvalidOperationException;


    /**
     * This method deletes the GameObjectClasses with the input String name. It scans through all possible maps of the String -> GameObjectClass.
     *
     * @param className: The name of the GameObjectClass to be deleted.
     * @return: true if the GameObjectClass is successfully deleted and false otherwise.
     */
    boolean deleteGameObjectClass(String className);

    boolean deleteGameObjectClass(int classId);

    /**
     * @param gameObjectClass
     */
    boolean deleteGameObjectClass(GameObjectClass gameObjectClass);


    boolean deleteGameObjectInstance(int instanceId);


    /**
     * Delete all instances currently in the CRUD.
     */
    void deleteAllInstances() throws InvalidIdException, GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     * This method is a convenient method that creates different GameObjectClasses, depending on the class name and the gameObjectType.
     *
     * @param <E>
     * @param gameObjectType : The GameObjectType that determines the type of GameObjectClass that is to be created.
     * @param name           : The name of the GameObjectClass to be created.
     * @return A Subclass of GameObjectClass depending on the String name and the GameObjectType.
     * @throws DuplicateGameObjectClassException
     */
    <E extends GameObjectClass> E createGameObjectClass(GameObjectType gameObjectType, String name) throws DuplicateGameObjectClassException;

    /**
     * This method is a convenient method that creates concrete GameObjectInstances, depending on the type of GameObjectClass that is passed in or inferred from class name.
     *
     * @param name:    The String class name of the input GameObjectClass.
     * @param topleft: A Point representing the topleft of the GameObjectInstance deployed.
     * @param <E>:     A subclass of GameObjectInstance.
     * @return A concrete GameObjectInstance inferred from input.
     * @throws GameObjectClassNotFoundException
     * @throws GameObjectTypeException
     */
    <E extends GameObjectInstance> E createGameObjectInstance(String name, Point topleft) throws GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     * This method is a convenient method that creates concrete GameObjectInstances, depending on the type of GameObjectClass that is passed in or inferred from class name.
     *
     * @param gameObjectClass: The input GameObjectClass.
     * @param topleft:         A Point representing the topleft of the GameObjectInstance deployed.
     * @param <E>:             A subclass of GameObjectInstance.
     * @return A concrete GameObjectInstance inferred from input.
     * @throws GameObjectTypeException
     */
    <E extends GameObjectInstance> E createGameObjectInstance(GameObjectClass gameObjectClass, Point topleft) throws GameObjectTypeException;

    /**
     * Getters
     *
     * @return Iterable of things
     */
    Iterable<EntityClass> getEntityClasses();

    String getBGMpath();
    void setBGMpath(String path);

    Iterable<TileClass> getTileClasses();

    Iterable<CategoryClass> getCategoryClasses();

    //    Iterable<SoundClass> getSoundClasses();
    Iterable<PlayerClass> getPlayerClasses();

    Iterable<EntityInstance> getEntityInstances();

    Iterable<TileInstance> getTileInstances();

    Iterable<CategoryInstance> getCategoryInstances();
//    Iterable<SoundInstance> getSoundInstances();

    @Deprecated
    Iterable<PlayerInstance> getPlayerInstances();

    Set<String> getPlayerNames(GameObjectInstance gameObjectInstance);

    String toXML();
}
