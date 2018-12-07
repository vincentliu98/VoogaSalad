import authoringUtils.exception.DuplicateGameObjectClassException;
import authoringUtils.exception.GameObjectClassNotFoundException;
import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.category.CategoryClass;
import gameObjects.category.CategoryInstance;
import gameObjects.crud.GameObjectsCRUDInterface;
import gameObjects.crud.SimpleGameObjectsCRUD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;


public class CRUDTest {
    GameObjectsCRUDInterface crud;
    CategoryClass catClass;

    @BeforeEach
    public void setupTestData()
            throws DuplicateGameObjectClassException, GameObjectClassNotFoundException, GameObjectTypeException, InvalidIdException {
        crud = new SimpleGameObjectsCRUD(10,10);
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

}
