import authoring.AuthoringTools;
import authoringUtils.exception.DuplicateGameObjectClassException;

public class CRUDSerializationTest {
    public static void main(String[] args) throws DuplicateGameObjectClassException {
        var authTools = new AuthoringTools(3, 3);
        var entityDB = authTools.entityDB();

        var playerA = entityDB.createPlayerClass("PlayerA");
        var playerB = entityDB.createPlayerClass("PlayerB");

        playerA.addProperty("ha", "5");

        System.out.println(entityDB.toXML());
    }
}
