package gameObjects.crud;

import authoringUtils.exception.*;
import gameObjects.category.*;
import gameObjects.entity.*;
import gameObjects.gameObject.*;
import gameObjects.player.PlayerInstance;
import gameObjects.tile.*;
import gameObjects.sound.*;
import gameObjects.turn.Turn;
import grids.Point;


import java.util.Collection;

/**
 *
 * This class encapsulates the Game Data for the GameObjects (Tiles and Entities). It provides methods to create, edit, and delete the Tile and Entity Classes.
 * It holds maps of GameObject Classes and GameObject Instances.
 * @author Jason Zhou
 */
public interface GameObjectsCRUDInterface {


    /**
     * This method creates a Tile Class and adds it to the map.
     * @param className the name of the Tile Class to be created
     * @return the Tile Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    TileClass createTileClass(String className)
            throws DuplicateGameObjectClassException;

    /**
     * This method gets the Tile Class from the map.
     * @param className the name of the Tile Class to be retrieved
     * @return the Tile Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Tile Class
     */
    TileClass getTileClass(String className)
            throws GameObjectClassNotFoundException;

    /**
     *
     * @param className
     * @return
     */
    TileInstance createTileInstance(String className, Point topLeftCoord)
            throws GameObjectClassNotFoundException, GameObjectTypeException;


    /**
     *
     * @param tileClass
     * @return
     */
    TileInstance createTileInstance(TileClass tileClass, Point topLeftCoord)
            throws GameObjectTypeException;

    /**
     * This method creates an Entity Class and adds it to the map.
     * @param className the name of the Entity Class to be created
     * @return the Entity Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    EntityClass createEntityClass(String className)
            throws DuplicateGameObjectClassException;

    /**
     * This method gets the Entity Class from the map.
     * @param className the name of the Entity Class to be retrieved
     * @return the Entity Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Entity Class
     */
    EntityClass getEntityClass(String className)
            throws GameObjectClassNotFoundException;

    /**
     *
     * @param className
     * @return
     */
    EntityInstance createEntityInstance(String className, int playerID)
            throws GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     *
     * @param entityClass
     * @return
     */
    EntityInstance createEntityInstance(EntityClass entityClass, int playerID)
            throws GameObjectTypeException;


    /**
     * This method creates an Category Class and adds it to the map.
     * @param className the name of the Class to be created
     * @return the Category Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    CategoryClass createCategoryClass(String className)
            throws DuplicateGameObjectClassException;

    /**
     * This method gets the Class from the map.
     * @param className the name of the Class to be retrieved
     * @return the Category Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Category Class
     */
    CategoryClass getCategoryClass(String className) throws GameObjectClassNotFoundException;

    /**
     *
     * @param className
     * @return
     */
    CategoryInstance createCategoryInstance(String className) throws GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     *
     * @param categoryClass
     * @return
     */
    CategoryInstance createCategoryInstance(CategoryClass categoryClass) throws GameObjectTypeException;

    /**
     * This method creates an Sound Class and adds it to the map.
     * @param className the name of the Class to be created
     * @return the Sound Class if there is no name collision and the method is successful
     * @throws DuplicateGameObjectClassException if a class with the same name exists
     */
    SoundClass createSoundClass(String className) throws DuplicateGameObjectClassException;

    /**
     * This method gets the Sound Class from the map.
     * @param className the name of the Class to be retrieved
     * @return the Sound Class with the name
     * @throws GameObjectClassNotFoundException if there is no such Sound Class
     */
    SoundClass getSoundClass(String className) throws GameObjectClassNotFoundException;

    /**
     *
     * @param className
     * @return
     */
    SoundInstance createSoundInstance(String className) throws GameObjectClassNotFoundException, GameObjectTypeException;

    /**
     *
     * @param soundClass
     * @return
     */
    SoundInstance createSoundInstance(SoundClass soundClass) throws GameObjectTypeException;


    PlayerInstance createPlayerInstance(String playerName);

    Turn createTurn(String phaseName);

    Turn getTurn(String phaseName) throws TurnNotFoundException;


    boolean deleteTurn(String phaseName);


    /**
     *  Sets the dimension of the entire grid
     * @param width
     * @param height
     */
    void setDimension(int width, int height);



    /*
        New methods!!!
     */

    /**
     * Returns the GameObject Class with the specified name
     * @param className
     * @return
     */
    GameObjectClass getGameObjectClass(String className) throws GameObjectClassNotFoundException;

    Collection<GameObjectClass> getAllClasses();

    /**
     * Returns the GameObject Instance with the specified name
     * @param instanceId
     * @return
     */
    GameObjectInstance getGameObjectInstance(int instanceId) throws GameObjectInstanceNotFoundException;

    /**
     *
     * @param className
     * @return
     */
    Collection<GameObjectInstance> getAllInstances(String className);

    /**
     *
     * @param gameObjectClass
     * @return
     */
    Collection<GameObjectInstance> getAllInstances(GameObjectClass gameObjectClass);

    /**
     *
     * @param x
     * @param y
     * @return
     */
    Collection<GameObjectInstance> getAllInstancesAtPoint(int x, int y);


    /**
     *
     * @param point
     * @return
     */
    Collection<GameObjectInstance> getAllInstancesAtPoint(Point point);

    /**
     *
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
     *
     * @param gameObjectClass
     */
    boolean deleteGameObjectClass(GameObjectClass gameObjectClass);


    boolean deleteGameObjectInstance(int instanceId);


    /**
     * Delete all instances currently in the CRUD.
     */
    void deleteAllInstances();




    /**
     * Getters
     *
     * @return Iterable of things
     */
    Iterable<EntityClass> getEntityClasses();



    Iterable<TileClass> getTileClasses();
    Iterable<CategoryClass> getCategoryClasses();
    Iterable<SoundClass> getSoundClasses();

    Iterable<EntityInstance> getEntityInstances();
    Iterable<TileInstance> getTileInstances();
    Iterable<CategoryInstance> getCategoryInstances();
    Iterable<SoundInstance> getSoundInstances();
    Iterable<PlayerInstance> getPlayerInstances();

    int getDefaultPlayerID();
}
