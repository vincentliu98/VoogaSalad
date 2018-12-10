package gameObjects.category;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CategoryInstanceFactory {
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc;

    public CategoryInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            ThrowingConsumer<GameObjectInstance, InvalidIdException> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public CategoryInstance createInstance(CategoryClass categoryPrototype)
            throws GameObjectTypeException, InvalidIdException {
        // TODO locality
        if (categoryPrototype.getType() != GameObjectType.CATEGORY) {
            throw new GameObjectTypeException(GameObjectType.CATEGORY);
        }
        String imagePathCopy = categoryPrototype.getImagePath();
        Map<String, String> propertiesMapCopy = new HashMap<>(categoryPrototype.getPropertiesMap());
        CategoryInstance categoryInstance = new SimpleCategoryInstance(categoryPrototype.getClassName(), imagePathCopy, propertiesMapCopy, categoryPrototype);
        requestInstanceIdFunc.accept(categoryInstance);
        addInstanceToMapFunc.accept(categoryInstance);
        return categoryInstance;
    }
}
