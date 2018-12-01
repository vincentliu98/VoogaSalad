package gameObjects.category;

import gameObjects.entity.EntityInstance;
import gameObjects.entity.EntityInstanceFactory;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Haotian Wang
 */
public class SimpleCategoryClass implements CategoryClass {
    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleStringProperty imagePath;
    private ObservableMap<String, String> propertiesMap;

    private CategoryInstanceFactory myFactory;
    private BiConsumer<String, String> changeCategoryClassNameFunc;
    private Function<String, Collection<GameObjectInstance>> getAllCategoryInstancesFunc;
    private Function<Integer, Boolean> deleteCategoryInstanceFunc;

    public SimpleCategoryClass(String className) {
        this.className = new ReadOnlyStringWrapper(className);
        classId = new ReadOnlyIntegerWrapper();
        imagePath = new SimpleStringProperty();
        propertiesMap = FXCollections.observableHashMap();
    }

    public SimpleCategoryClass(
            String className,
            CategoryInstanceFactory categoryInstanceFactory,
            BiConsumer<String, String> changeCategoryClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllCategoryInstancesFunc,
            Function<Integer, Boolean> deleteCategoryInstanceFunc) {
        this(className);
        this.myFactory = categoryInstanceFactory;
        this.changeCategoryClassNameFunc = changeCategoryClassNameFunc;
        this.getAllCategoryInstancesFunc = getAllCategoryInstancesFunc;
        this.deleteCategoryInstanceFunc = deleteCategoryInstanceFunc;
    }
    /**
     * This method sets the id of the GameObject Class.
     *
     * @return classId
     */
    @Override
    public ReadOnlyIntegerProperty getClassId() {
        return classId.getReadOnlyProperty();
    }

    /**
     * This method receives a function that sets the id of the GameObject Class.
     * The id of the GameObject Class is set by the received function.
     *
     * @param setFunc the function from IdManager that sets the id
     */
    @Override
    public void setClassId(Consumer<SimpleIntegerProperty> setFunc) {
        setFunc.accept(classId);
    }

    /**
     * This method gets the name of this GameObject Class.
     *
     * @return class name
     */
    @Override
    public ReadOnlyStringProperty getClassName() {
        return className.getReadOnlyProperty();
    }

    @Override
    public void changeClassName(String newClassName) {
        changeCategoryClassNameFunc.accept(className.getValue(), newClassName);
    }

    @Override
    public void setClassName(String newClassName) {
        className.setValue(newClassName);
    }


    /**
     * This method gets the properties map of the GameObject Class.
     *
     * @return properties map
     */
    @Override
    public ObservableMap<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    /**
     * This method adds the property to the GameObject Class and to all instances of the class.
     *
     * @param propertyName property name
     * @param defaultValue default value of the property in GroovyCode
     * @return true if property is successfully added
     */
    @Override
    public boolean addProperty(String propertyName, String defaultValue) {
        if (propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.put(propertyName, defaultValue);
        Collection<CategoryInstance> categoryInstances = getAllInstances();
        for (CategoryInstance c : categoryInstances) {
            c.addProperty(propertyName, defaultValue);
        }
        return true;
    }

    /**
     * This method removes the property from the GameObject Class and from all instances of the class.
     *
     * @param propertyName property name to be removed
     * @return true if the property name exists in the properties map
     */
    @Override
    public boolean removeProperty(String propertyName) {
        if (!propertiesMap.containsKey(propertyName)) {
            return false;
        }
        propertiesMap.remove(propertyName);
        Collection<CategoryInstance> categoryInstances = getAllInstances();
        for (CategoryInstance c : categoryInstances) {
            c.removeProperty(propertyName);
        }
        return true;
    }


    /**
     * This method returns all of the instance of the GameObject Class.
     *
     * @return the set of all instances of the class
     */
    @Override
    public Set<CategoryInstance> getAllInstances() {
        ObservableSet<CategoryInstance> s = FXCollections.observableSet();
        Collection<GameObjectInstance> instances = getAllCategoryInstancesFunc.apply(getClassName().getValue());
        for (GameObjectInstance i : instances) {
            if (i.getType() == GameObjectType.CATEGORY) {
                s.add((CategoryInstance) i);
            }
        }
        return s;
    }

    /**
     * This method removes the instance with the specified instance id
     *
     * @param categoryInstanceId of the instance
     * @return true if the instance exists
     */
    @Override
    public boolean deleteInstance(int categoryInstanceId) {
        return deleteCategoryInstanceFunc.apply(categoryInstanceId);
    }

    @Override
    public CategoryInstance createInstance() {
        return myFactory.createInstance(this);
    }

    @Override
    public SimpleStringProperty getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String newImagePath) {
        imagePath.setValue(newImagePath);
    }
}
