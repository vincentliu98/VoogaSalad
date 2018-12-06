package gameplay;

import java.util.Comparator;
import java.util.List;

import static gameplay.GameData.*;

/**
 *  Methods that are intended to be used by the authors via groovy blocks
 *  Separated from GameData to use reflection
 *  https://hackmd.io/kO3SRcCeQFyQJ_3VwEv1BQ?both#
 */
public class GameMethods {
    /**
     *  Grid
     */
    public static int gridWidth() { return GRID_WIDTH; }
    public static int gridHeight() { return GRID_HEIGHT; }

    /**
     *  dot to function
     */
    public static int id(GameObject object) { return object.getID(); }
    public static String name(GameObject object) { return object.getName(); }
    public static double x(GameObject object) { return object.getX(); }
    public static double y(GameObject object) { return object.getY(); }
    public static void setProperty(PropertyHolder object, String key, Object value) { object.set(key, value); }
    public static Object getProperty(PropertyHolder object, String key) { return object.get(key); }

    /**
     *  Entity
     */
    public static boolean isEntity(GameObject object) {
        return GameData.ENTITY_PROTOTYPES.keySet().contains(object.getName());
    }
    public static Entity getEntity(int entityID){
        return ENTITIES.get(entityID);
    }
    public static Entity createEntity(String entityName, int tileID, int ownerID){
        var nextID = ENTITIES.keySet().stream().max(Comparator.comparingInt(a -> a)).orElse(0)+1;
        var newEntity = ENTITY_PROTOTYPES.get(entityName).build(nextID, tileID);
        newEntity.adjustViewSize(ROOT.getWidth(), ROOT.getHeight());
        ENTITIES.put(nextID, newEntity);
        PLAYERS.get(ownerID).addEntity(nextID);
        newEntity.setLocation(tileID);
        ROOT.getChildren().add(newEntity.getImageView());
        return newEntity;
    }
    public static void removeEntity(Entity entity) {
        ROOT.getChildren().remove(entity.getImageView());
        ENTITIES.remove(entity.getID());
    }
    public static void moveEntity(Entity entity, Tile to) {
        entity.setLocation(to.getID());
    }

    /**
     *  Tile
     */
    public static boolean isTile(GameObject object) {
        return !ENTITY_PROTOTYPES.keySet().contains(object.getName());
    }
    public static Tile getTile(int tileID){
        return TILES.get(tileID);
    }
    public static boolean hasNoEntities(Tile t) {
        return ENTITIES.values().stream().noneMatch(e -> t.getID() == e.getTileID());
    }

    /**
     *  Player/Turn
     */
    public static Player getCurrentPlayer() { return PLAYERS.get(TURN.getCurrentPlayerID()); }
    public static void setCurrentPlayer(int playerID) { TURN.setCurrentPlayer(playerID);}
    public static int getCurrentPlayerID(){ return TURN.getCurrentPlayerID(); }
    public static int getNextPlayerID() { return TURN.nextPlayerID(); }
    public static int toNextPlayer() { return TURN.toNextPlayer(); }
    public static void setPlayerOrder(List<Integer> newOrder){ TURN.setPlayerOrder(newOrder); }
    public static void endGame(int playerID){ TURN.endGame(playerID);}

    /**
     *  Phase
     */
    public static void goTo(int nodeID) { getNode(nodeID).execute(); }

    /**
     *  Meta
     */
    public static double distance(GameObject a, GameObject b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx*dx+dy*dy);
    }
}
