import authoring.AuthoringTools;
import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidGameObjectInstanceException;
import authoringUtils.exception.InvalidIdException;
import grids.PointImpl;

public class CRUDSerializationTest {
    public static void main(String[] args) throws DuplicateGameObjectClassException, GameObjectTypeException, InvalidIdException, InvalidGameObjectInstanceException {
        var authTools = new AuthoringTools(3, 3);
        var entityDB = authTools.entityDB();

        var playerA = entityDB.createPlayerClass("PlayerA");
        playerA.addProperty("ha", "5");

        var goblinClass = entityDB.createEntityClass("goblin");
        var goblinInstance = goblinClass.createInstance(new PointImpl(1, 1));

        System.out.println(entityDB.toXML());
    }
}
