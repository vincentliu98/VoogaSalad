package gameObjects;

/**
 *
 * This class encapsulates the Game Data for the GameObjects (Tiles and Entities). It provides methods to create, edit, and delete the Tile and Entity Classes.
 * It holds maps of GameObject Classes as well as GameObject Instances.
 * @author Jason Zhou
 */
public interface GameObjectsCRUDInterface {

    /**
     * This method creates a Tile Class and adds it to the map.
     * @param name the name of the Tile Class to be created
     * @return the Tile Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    TileClass createTileClass(String name);

    /**
     * This method gets the Tile Class from the map.
     * @param name the name of the Tile Class to be retrieved
     * @return the Tile Class with the name
     * @throws NoTileClassException if there is no such Tile Class
     */
    TileClass getTileClass(String name);

    /**
     * This method deletes the specified Tile Class.
     * @param name the name of the Tile Class to be deleted
     * @return True if the Tile Class exists
     */
    boolean deleteTileClass(String name);

    /**
     * This method deletes the specified Tile Instance.
     * @param instanceId the id of the instance
     * @return True if the Tile Instance with the instanceId exists
     */
    boolean deleteTileInstance(int instanceId);

    /**
     * 
     * @param name the name of the Entity Class to be created
     * @return the Entity Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    EntityClass createEntityClass(String name);

    /**
     * @param name the name of the Entity Class to be retrieved
     * @return the Entity Class with the name
     * @throws NoEntityClassException if there is no such Entity Class
     */
    EntityClass getEntityClass(String name);

    /**
     *
     * @param name the name of the Entity Class to be deleted
     * @return True if the Entity Class exists
     */
    boolean deleteEntityClass(String name);

    /**
     *
     * @param instanceId
     * @return
     */
    boolean deleteEntityInstance(int instanceId);

    /**
     *
     * @return Entity Data of the Game in XML format
     */
    String toXML();
}
