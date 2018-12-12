import authoring.AuthoringTools;
import authoringUtils.exception.*;
import grids.PointImpl;

public class ValidationTest {
    public static void main(String[] args) throws DuplicateGameObjectClassException, GameObjectTypeException, InvalidGameObjectInstanceException, InvalidIdException, GameObjectClassNotFoundException {
        var authTools = new AuthoringTools(5, 5);

        var entityDB = authTools.entityDB();
        var phaseDB = authTools.phaseDB();
        var factory = authTools.factory();

        var grassClass = entityDB.createTileClass("grass");
        var tile1 = grassClass.createInstance(new PointImpl(0, 0));
        var tile2 = grassClass.createInstance(new PointImpl(1, 0));
        var tile3 = grassClass.createInstance(new PointImpl(2, 0));
        var tile4 = grassClass.createInstance(new PointImpl(3, 0));

        var playerA = entityDB.createPlayerInstance("A");
        var goblinClass = entityDB.createEntityClass("goblin");
        goblinClass.getPropertiesMap().put("hp", "1");

        var goblinInstance =
                goblinClass.createInstance(PointImpl.ZERO);

        var source = phaseDB.createPhaseGraph("A").get(null).source();

        // TEST1: creating ref block referencing hp (success)
        System.out.println(factory.refBlock(0, 0, "$clicked.props.hp").isSuccess());

        // TEST1: creating ref block referencing hpp (failure)
        System.out.println(factory.refBlock(0, 0, "$clicked.props.hpp").isSuccess());
    }
}
