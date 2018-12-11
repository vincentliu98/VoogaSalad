package gameObjects.category;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.*;
import java.util.function.Function;

/**
 * @author Haotian Wang
 */
public class SimpleCategoryClass implements CategoryClass {
    private String className;
    private int classId;
    private String imagePath;
    private Map<String, String> propertiesMap;

    @XStreamOmitField
    private transient CategoryInstanceFactory myFactory;
    @XStreamOmitField
    private transient ThrowingBiConsumer<String, String, InvalidOperationException> changeCategoryClassNameFunc;
    @XStreamOmitField
    private transient Function<String, Collection<GameObjectInstance>> getAllCategoryInstancesFunc;
    @XStreamOmitField
    private transient Function<Integer, Boolean> deleteCategoryInstanceFunc;

    public SimpleCategoryClass(String className) {
        this.className = className;
        classId = 0;
        imagePath = "";
        propertiesMap = new HashMap<>();
    }

    public SimpleCategoryClass(
            String className,
            CategoryInstanceFactory categoryInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeCategoryClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllCategoryInstancesFunc,
            Function<Integer, Boolean> deleteCategoryInstanceFunc
    ) {
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
    public int getClassId() {
        return classId;
    }

    /**
     * This method receives a function that sets the id of the GameObject Class.
     * The id of the GameObject Class is set by the received function.
     */
    @Override
    public void setClassId(int newId) {
        classId = newId;
    }

    /**
     * This method gets the name of this GameObject Class.
     *
     * @return class name
     */
    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String newClassName) {
        className = newClassName;
    }

    @Override
    public void changeClassName(String newClassName)
            throws InvalidOperationException {
        changeCategoryClassNameFunc.accept(className, newClassName);
    }

    /**
     * This method gets the properties map of the GameObject Class.
     *
     * @return properties map
     */
    @Override
    public Map<String, String> getPropertiesMap() {
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
        Set<CategoryInstance> s = new HashSet<>();
        Collection<GameObjectInstance> instances = getAllCategoryInstancesFunc.apply(getClassName());
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
    public CategoryInstance createInstance()
            throws GameObjectTypeException, InvalidIdException {
        return myFactory.createInstance(this);
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String newImagePath) {
        imagePath = newImagePath;
    }

    @Override
    public void equipContext(
            CategoryInstanceFactory categoryInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeCategoryClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllCategoryInstancesFunc,
            Function<Integer, Boolean> deleteCategoryInstanceFunc
    ) {
        this.myFactory = categoryInstanceFactory;
        this.changeCategoryClassNameFunc = changeCategoryClassNameFunc;
        this.getAllCategoryInstancesFunc = getAllCategoryInstancesFunc;
        this.deleteCategoryInstanceFunc = deleteCategoryInstanceFunc;
    }


}
