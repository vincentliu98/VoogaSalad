package gameObjects.category;

import gameObjects.exception.InvalidClassException;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CategoryInstanceFactory {
    private Consumer<GameObjectInstance> requestInstanceIdFunc;
    private Consumer<GameObjectInstance> addInstanceToMapFunc;


    public CategoryInstanceFactory(
            Consumer<GameObjectInstance> requestInstanceIdFunc,
            Consumer<GameObjectInstance> addInstanceToMapFunc) {

        this.requestInstanceIdFunc = requestInstanceIdFunc;
        this.addInstanceToMapFunc = addInstanceToMapFunc;
    }

    public CategoryInstance createInstance(CategoryClass categoryPrototype) {
        // TODO locality
        if (categoryPrototype.getType() != GameObjectType.CATEGORY) {
            throw new InvalidClassException();
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
