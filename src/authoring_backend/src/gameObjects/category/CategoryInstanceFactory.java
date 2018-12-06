package gameObjects.category;

import authoringUtils.exception.*;
import gameObjects.ThrowingConsumer;
import gameObjects.gameObject.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;

import java.util.function.Consumer;
import java.util.function.Supplier;

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
            throw new GameObjectTypeException("categoryPrototype is not of Category Type");
        }
        SimpleStringProperty imagePathCopy = new SimpleStringProperty();
        ObservableMap propertiesMapCopy = FXCollections.observableHashMap();
        imagePathCopy.setValue(categoryPrototype.getImagePath().getValue());
        propertiesMapCopy.putAll(categoryPrototype.getPropertiesMap());
        Supplier<CategoryClass> getCategoryClassFunc = () -> categoryPrototype;
        CategoryInstance categoryInstance = new SimpleCategoryInstance(categoryPrototype.getClassName().getValue(), imagePathCopy, propertiesMapCopy, getCategoryClassFunc);
        requestInstanceIdFunc.accept(categoryInstance);
        addInstanceToMapFunc.accept(categoryInstance);
        return categoryInstance;

    }
}
