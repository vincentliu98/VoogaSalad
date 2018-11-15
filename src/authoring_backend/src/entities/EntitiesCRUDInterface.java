package entities;

/**
 *
 * This class encapsulates the Game Data for the Entities (Tiles and Sprites). It provides methods to create, edit, and delete the Sprites and Tiles Classes.
 * @author Jason Zhou
 */
public interface EntitiesCRUDInterface {

    /**
     * @param name the name of the Tile Class to be created
     * @return True if there is no name collision and the method is successful
     */
    boolean createTileClass(String name);

    /**
     * @param name the name of the Tile Class to be retrieved
     * @return the Tile Class with the name
     * @throws NoTileClassException if there is no such Tile Class
     */
    TileClass getTileClass(String name);

    /**
     * @param name the name of the Sprite Class to be created
     * @return True if there is no name collision and the method is successful
     */
    boolean createSpriteClass(String name);

    /**
     * @param name the name of the Sprite Class to be retrieved
     * @return the Sprite Class with the name
     * @throws NoSpriteClassException if there is no such Sprite Class
     */
    SpriteClass getSpriteClass(String name);

    /**
     *
     * @param name the name of the Tile Class to be deleted
     * @return True if the Tile Class exists
     */
    boolean deleteTileClass(String name);

    /**
     *
     * @param name the name of the Tile Class to be deleted
     * @return True if the Sprite Class exists
     */
    boolean deleteSpriteClass(String name);

    /**
     *
     * @return Entity Data of the Game in XML format
     */
    String toXML();
}
