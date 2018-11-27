package gameObjects;

/**
 *
 * This class encapsulates the Game Data for the Entities (Tiles and Sprites). It provides methods to create, edit, and delete the Sprites and Tiles Classes.
 * @author Jason Zhou
 */
public interface GameObjectsCRUDInterface {

    /**
     * @param name the name of the Tile Class to be created
     * @return the Tile Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    TileClass createTileClass(String name);

    /**
     * @param name the name of the Tile Class to be retrieved
     * @return the Tile Class with the name
     * @throws NoTileClassException if there is no such Tile Class
     */
    TileClass getTileClass(String name);

    /**
     *
     * @param name the name of the Tile Class to be deleted
     * @return True if the Tile Class exists
     */
    boolean deleteTileClass(String name);

    /**
     *
     * @param instanceId
     * @return
     */
    boolean deleteTileInstance(int instanceId);

    /**
     * @param name the name of the Entity Class to be created
     * @return the Entity Class if there is no name collision and the method is successful
     * @throws DuplicateClassException if a class with the same name exists
     */
    SpriteClass createEntityClass(String name);

    /**
     * @param name the name of the Entity Class to be retrieved
     * @return the Entity Class with the name
     * @throws NoSpriteClassException if there is no such Entity Class
     */
    SpriteClass getEntityClass(String name);

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
