package gameObjects.player;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectInstance;


import gameObjects.gameObject.GameObjectType;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Yunhao Qing
 */


public class SimplePlayerClass implements PlayerClass {

    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleStringProperty imagePath;
    private ObservableMap<String, String> propertiesMap;
    private ObservableSet<GameObjectInstance> gameObjectInstancesSet;

    private PlayerInstanceFactory myFactory;
    private ThrowingBiConsumer<String, String, InvalidOperationException> changePlayerClassNameFunc;
    private Function<String, Collection<GameObjectInstance>> getAllPlayerInstancesFunc;
    private Function<Integer, Boolean> deletePlayerInstanceFunc;

    public SimplePlayerClass(String className) {
        this.className = new ReadOnlyStringWrapper(className);
        classId = new ReadOnlyIntegerWrapper();
        imagePath = new SimpleStringProperty();
        propertiesMap = FXCollections.observableHashMap();
        gameObjectInstancesSet = FXCollections.observableSet();
    }

    public SimplePlayerClass(String className,
                      PlayerInstanceFactory playerInstanceFactory,
                      ThrowingBiConsumer<String, String, InvalidOperationException> changePlayerClassNameFunc,
                      Function<String, Collection<GameObjectInstance>> getAllPlayerInstancesFunc,
                      Function<Integer, Boolean> deletePlayerInstanceFunc) {
        this(className);
        this.myFactory = playerInstanceFactory;
        this.changePlayerClassNameFunc = changePlayerClassNameFunc;
        this.getAllPlayerInstancesFunc = getAllPlayerInstancesFunc;
        this.deletePlayerInstanceFunc = deletePlayerInstanceFunc;
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
    public void changeClassName(String newClassName)
            throws InvalidOperationException {
        changePlayerClassNameFunc.accept(className.getValue(), newClassName);
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
        Collection<PlayerInstance> playerInstances = getAllInstances();
        for (PlayerInstance c : playerInstances) {
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
        Collection<PlayerInstance> playerInstances = getAllInstances();
        for (PlayerInstance c : playerInstances) {
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
    public Set<PlayerInstance> getAllInstances() {
        ObservableSet<PlayerInstance> s = FXCollections.observableSet();
        Collection<GameObjectInstance> instances = getAllPlayerInstancesFunc.apply(getClassName().getValue());
        for (GameObjectInstance i : instances) {
            if (i.getType() == GameObjectType.PLAYER) {
                s.add((PlayerInstance) i);
            }
        }
        return s;
    }

    /**
     * This method removes the instance with the specified instance id
     *
     * @param playerInstanceId of the instance
     * @return true if the instance exists
     */
    @Override
    public boolean deleteInstance(int playerInstanceId) {
        return deletePlayerInstanceFunc.apply(playerInstanceId);
    }

    @Override
    public PlayerInstance createInstance()
            throws GameObjectTypeException, InvalidIdException {
        return myFactory.createInstance(this);
    }

    @Override
    public boolean addGameObjectInstances(GameObjectInstance gameObjectInstance) {
        if (!gameObjectInstancesSet.contains(gameObjectInstance)){
            gameObjectInstancesSet.add(gameObjectInstance);
            return true;
        }
        else return false;
    }

    @Override
    public boolean removeGameObjectInstances(GameObjectInstance gameObjectInstance) {
        return gameObjectInstancesSet.remove(gameObjectInstance);
    }

    @Override
    public boolean isOwnedByPlayer(GameObjectInstance gameObjectInstance) {
        return gameObjectInstancesSet.contains(gameObjectInstance);
    }

    @Override
    public void removeAllGameObjectInstances() {
        gameObjectInstancesSet.clear();
    }

    @Override
    public Set<Integer> getAllGameObjectInstanceIDs() {
        return gameObjectInstancesSet.stream().map(i -> i.getInstanceId().get()).collect(Collectors.toSet());
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
