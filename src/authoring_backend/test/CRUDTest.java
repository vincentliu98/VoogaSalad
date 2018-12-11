import authoringUtils.exception.*;
import gameObjects.category.CategoryClass;
import gameObjects.category.CategoryInstance;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.crud.SimpleGameObjectsCRUD;
import gameObjects.gameObject.GameObjectInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CRUDTest {
    GameObjectsCRUDInterface crud;
    CategoryClass catClass;
    CategoryInstance catInstance;

    @BeforeEach
    public void setupTestData()
            throws DuplicateGameObjectClassException, GameObjectClassNotFoundException, GameObjectTypeException, InvalidIdException {
        crud = new SimpleGameObjectsCRUD(10, 10, false);
        for (int i = 0; i < 5; i++) {
            crud.createCategoryClass(Integer.toString(i));
        }
        catClass = crud.getCategoryClass(Integer.toString(1));
        for (int i = 0; i < 5; i++) {
            catClass.createInstance();
        }
    }

    @Test
    public void testGetAllInstances() {
        System.out.println(catClass.getAllInstances());
    }

    @Test
    public void testChangeClassName() throws GameObjectClassNotFoundException, InvalidOperationException {
        catClass = crud.getCategoryClass(Integer.toString(1));
        catClass.changeClassName("hello");
        for (GameObjectInstance g : crud.getAllInstances("hello")) {
            System.out.println(g.getClassName());
        }

    }

    @Test
    public void testDeleteInstances() throws GameObjectClassNotFoundException, GameObjectInstanceNotFoundException {
        catClass = crud.getCategoryClass(Integer.toString(1));
        crud.deleteGameObjectInstance(3);
        crud.deleteGameObjectInstance(2);
        for (GameObjectInstance g : crud.getCategoryInstances()) {
            System.out.println(g.getInstanceId());
        }
    }

}
