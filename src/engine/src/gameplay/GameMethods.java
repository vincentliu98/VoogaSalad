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
    private static final int DEFAULT_PLAYER_ID = 0;
    /**
     *  Grid
     */
    public static int gridWidth() { return GRID_WIDTH; }
    public static int gridHeight() { return GRID_HEIGHT; }

    /**
     *  dot to function
     */
    public static int getId(GameObject object) { return object.getID(); }
    public static String getName(GameObject object) { return object.getName(); }
    public static double getX(GameObject object) { return object.getX(); }
    public static double getY(GameObject object) { return object.getY(); }
    public static void setProperty(PropertyHolder object, String key, Object value) { object.set(key, value); }
    public static Object getProperty(PropertyHolder object, String key) { return object.get(key); }

    /**
     *  Entity
     */
    public static boolean isEntity(GameObject object) {
        return GameData.ENTITY_PROTOTYPES.keySet().contains(object.getName());
    }
    public static Entity getEntity(int entityID){ return ENTITIES.get(entityID); }

    public static Entity createEntity(String entityName, int x, int y, int ownerID){
        var nextID = ENTITIES.keySet().stream().max(Comparator.comparingInt(a -> a)).orElse(0)+1;
        var newEntity = ENTITY_PROTOTYPES.get(entityName).build(nextID, x, y);
        newEntity.adjustViewSize(ROOT.getWidth(), ROOT.getHeight());
        ENTITIES.put(nextID, newEntity);
        PLAYERS.get(ownerID).addEntity(nextID);
        newEntity.setLocation(x, y);
        ROOT.getChildren().add(newEntity.getImageView());
        return newEntity;
    }
    public static Entity createEntity(String entityName, int x, int y){
        return createEntity(entityName, x, y, DEFAULT_PLAYER_ID);
    }

    public static void removeEntity(Entity entity) {
        ROOT.getChildren().remove(entity.getImageView());
        ENTITIES.remove(entity.getID());
    }
    public static void moveEntity(Entity entity, double x, double y) {
        entity.setLocation(x, y);
    }
    public static void moveEntity(Entity entity, Tile tile) {
        moveEntity(entity, tile.getX(), tile.getY());
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

    public static boolean hasNoIntersectingEntities(int tileID) { return hasNoIntersectingEntities(TILES.get(tileID)); }
    public static boolean hasNoIntersectingEntities(Tile tile) {
        return ENTITIES.values().stream().noneMatch(e -> {
            boolean verdictX =
                (tile.getX() <= e.getX() && e.getX() < tile.getX() + tile.getWidth()) ||
                (e.getX() <= tile.getX() && tile.getX() < e.getX() + e.getWidth());
            boolean verdictY =
                (tile.getY() <= e.getY() && e.getY() < tile.getY() + tile.getHeight()) ||
                    (e.getY() <= tile.getY() && tile.getY() < e.getY() + e.getHeight());
            return verdictX && verdictY;
        });
    }
    public static boolean hasNoEntities(int x, int y) {
        return ENTITIES.values().stream().noneMatch(e -> {
            boolean verdictX = (e.getX() <= x && x < e.getX() + e.getWidth());
            boolean verdictY = (e.getY() <= y && y < e.getY() + e.getHeight());
            return verdictX && verdictY;
        });
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
        System.out.println(Math.sqrt(dx*dx+dy*dy));
        return Math.sqrt(dx*dx+dy*dy);
    }
}
