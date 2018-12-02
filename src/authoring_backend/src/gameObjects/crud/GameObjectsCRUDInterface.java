package gameObjects.crud;

import gameObjects.category.*;
import gameObjects.entity.*;
import gameObjects.exception.*;
import gameObjects.gameObject.*;
import gameObjects.player.PlayerInstance;
import gameObjects.tile.*;
import gameObjects.sound.*;
import gameObjects.turn.Turn;
import grids.Point;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

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
     * @throws DuplicateClassException if a class with the same name exists
     */
    TileClass createTileClass(String className);

    /**
     * This method gets the Tile Class from the map.
     * @param className the name of the Tile Class to be retrieved
     * @return the Tile Class with the name
     * @throws NoTileClassException if there is no such Tile Class
     */
    TileClass getTileClass(String className);

    /**
     *
     * @param className
     * @return
     */
    TileInstance createTileInstance(String className, Point topLeftCoord);


    /**
     *
     * @param tileClass
     * @return
     */
    TileInstance createTileInstance(TileClass tileClass, Point topLeftCoord);

    /**
     * This method creates an Entity Class and adds it to the map.
     * @param className the name of the Entity Class to be created
     * @return the Entity Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    EntityClass createEntityClass(String className);

    /**
     * This method gets the Entity Class from the map.
     * @param className the name of the Entity Class to be retrieved
     * @return the Entity Class with the name
     * @throws NoEntityClassException if there is no such Entity Class
     */
    EntityClass getEntityClass(String className);

    /**
     *
     * @param className
     * @return
     */
    EntityInstance createEntityInstance(String className, int tileId, int playerID);

    /**
     *
     * @param entityClass
     * @return
     */
    EntityInstance createEntityInstance(EntityClass entityClass, int tileId, int playerID);


    /**
     * This method creates an Category Class and adds it to the map.
     * @param className the name of the Class to be created
     * @return the Category Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    CategoryClass createCategoryClass(String className);

    /**
     * This method gets the Class from the map.
     * @param className the name of the Class to be retrieved
     * @return the Category Class with the name
     * @throws NoCategoryClassException if there is no such Category Class
     */
    CategoryClass getCategoryClass(String className);

    /**
     *
     * @param className
     * @return
     */
    CategoryInstance createCategoryInstance(String className);

    /**
     *
     * @param categoryClass
     * @return
     */
    CategoryInstance createCategoryInstance(CategoryClass categoryClass);

    /**
     * This method creates an Sound Class and adds it to the map.
     * @param className the name of the Class to be created
     * @return the Sound Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    SoundClass createSoundClass(String className);

    /**
     * This method gets the Sound Class from the map.
     * @param className the name of the Class to be retrieved
     * @return the Sound Class with the name
     * @throws NoSoundClassException if there is no such Sound Class
     */
    SoundClass getSoundClass(String className);

    /**
     *
     * @param className
     * @return
     */
    SoundInstance createSoundInstance(String className);

    /**
     *
     * @param soundClass
     * @return
     */
    SoundInstance createSoundInstance(SoundClass soundClass);


    PlayerInstance createPlayerInstance(String playerName);

    Turn createTurn(String phaseName);

    Turn getTurn(String phaseName);


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
    GameObjectClass getGameObjectClass(String className);

    Collection<GameObjectClass> getAllClasses();

    /**
     * Returns the GameObject Instance with the specified name
     * @param instanceId
     * @return
     */
    GameObjectInstance getGameObjectInstance(int instanceId);

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
    boolean changeGameObjectClassName(String oldClassName, String newClassName);


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
     * @return ObservableList of things
     */
    ObservableList<EntityClass> getEntityClasses();
    ObservableList<TileClass> getTileClasses();
    ObservableList<CategoryClass> getCategoryClasses();
    ObservableList<SoundClass> getSoundClasses();

    ObservableList<EntityInstance> getEntityInstances();
    ObservableList<TileInstance> getTileInstances();
    ObservableList<CategoryInstance> getCategoryInstances();
    ObservableList<SoundInstance> getSoundInstances();
    ObservableList<PlayerInstance> getPlayerInstances();

    int getDefaultPlayerID();
}
