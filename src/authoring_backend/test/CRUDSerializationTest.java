import authoring.AuthoringTools;
import authoringUtils.exception.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import conversion.authoring.SavedEntityDB;
import gameObjects.crud.SimpleGameObjectsCRUD;
import grids.PointImpl;

public class CRUDSerializationTest {
    public static void main(String[] args) throws DuplicateGameObjectClassException, GameObjectTypeException, InvalidIdException, InvalidGameObjectInstanceException, GameObjectClassNotFoundException {
        var authTools = new AuthoringTools(4, 3);
        var entityDB = authTools.entityDB();

        var playerA = entityDB.createPlayerClass("PlayerA");
        playerA.addProperty("ha", "5");

        var goblinClass = entityDB.createEntityClass("goblin");
        var trollClass = entityDB.createEntityClass("troll");
        var goblinInstance = goblinClass.createInstance(new PointImpl(1, 1));
        var trollInstance = trollClass.createInstance(new PointImpl(2, 2));

        System.out.println("-------------BEFORE SERIALIZATION-----------");
        System.out.println(entityDB.toXML());

        var recoveredDB = new SimpleGameObjectsCRUD((SavedEntityDB) new XStream(new DomDriver()).fromXML(entityDB.toXML()));
        System.out.println("-------------AFTER  SERIALIZATION-----------");
        var recoveredGoblinClass = recoveredDB.getEntityClass("goblin");
        System.out.println(recoveredGoblinClass.createInstance(new PointImpl(2, 1)));
        System.out.println(recoveredDB.getAllInstances("goblin"));
        recoveredGoblinClass.addProperty("hp ", "[   ]");

        recoveredGoblinClass.getAllInstances().forEach(c -> System.out.println(c.getPropertiesMap()));
    }
}
