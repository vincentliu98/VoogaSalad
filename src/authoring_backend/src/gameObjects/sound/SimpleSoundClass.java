package gameObjects.sound;

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
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleSoundClass implements SoundClass {
    private ReadOnlyStringWrapper className;
    private ReadOnlyIntegerWrapper classId;
    private SimpleStringProperty mediaPath;
    private SimpleDoubleProperty duration;
    private ObservableMap<String, String> propertiesMap;

    private SoundInstanceFactory myFactory;
    private ThrowingBiConsumer<String, String, InvalidOperationException> changeSoundClassNameFunc;
    private Function<String, Collection<GameObjectInstance>> getAllSoundInstancesFunc;
    private Function<Integer, Boolean> deleteSoundInstanceFunc;

    public SimpleSoundClass(String className) {
        this.className = new ReadOnlyStringWrapper(className);
        classId = new ReadOnlyIntegerWrapper();
        duration = new SimpleDoubleProperty();
        mediaPath = new SimpleStringProperty();
        propertiesMap = FXCollections.observableHashMap();
        duration = new SimpleDoubleProperty();
    }

    public SimpleSoundClass(
            String className,
            SoundInstanceFactory soundInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeSoundClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllSoundInstancesFunc,
            Function<Integer, Boolean> deleteSoundInstanceFunc) {
        this(className);
        this.myFactory = soundInstanceFactory;
        this.changeSoundClassNameFunc = changeSoundClassNameFunc;
        this.getAllSoundInstancesFunc = getAllSoundInstancesFunc;
        this.deleteSoundInstanceFunc = deleteSoundInstanceFunc;
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
        changeSoundClassNameFunc.accept(className.getValue(), newClassName);
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
        Collection<SoundInstance> soundInstances = getAllInstances();
        for (SoundInstance s : soundInstances) {
            s.addProperty(propertyName, defaultValue);
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
        Collection<SoundInstance> soundInstances = getAllInstances();
        for (SoundInstance s : soundInstances) {
            s.removeProperty(propertyName);
        }
        return true;
    }

    /**
     * This method returns all of the instance of the GameObject Class.
     *
     * @return the set of all instances of the class
     */
    @Override
    public Collection<SoundInstance> getAllInstances() {
        ObservableSet<SoundInstance> s = FXCollections.observableSet();
        Collection<GameObjectInstance> instances = getAllSoundInstancesFunc.apply(getClassName().getValue());
        for (GameObjectInstance i : instances) {
            if (i.getType() == GameObjectType.SOUND) {
                s.add((SoundInstance) i);
            }
        }
        return s;
    }

    /**
     * This method removes the instance with the specified instance id
     *
     * @param soundInstanceId id of the instance
     * @return true if the instance exists
     */
    @Override
    public boolean deleteInstance(int soundInstanceId) {
        return deleteSoundInstanceFunc.apply(soundInstanceId);
    }

    @Override
    public SoundInstance createInstance()
            throws GameObjectTypeException, InvalidIdException {
        return myFactory.createInstance(this);
    }

    @Override
    public SimpleStringProperty getMediaFilePath() {
        return mediaPath;
    }

    @Override
    public void setMediaFilePath(String mediaFilePath) {

    }

    @Override
    public SimpleDoubleProperty getDuration() {
        return duration;
    }

    @Override
    public void setDuration(double newDuration) {
        duration.setValue(newDuration);
    }
}