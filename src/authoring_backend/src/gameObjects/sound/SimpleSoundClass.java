package gameObjects.sound;

import authoringUtils.exception.GameObjectTypeException;
import authoringUtils.exception.InvalidIdException;
import authoringUtils.exception.InvalidOperationException;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import gameObjects.ThrowingBiConsumer;
import gameObjects.gameObject.GameObjectInstance;
import gameObjects.gameObject.GameObjectType;

import java.util.*;
import java.util.function.Function;

public class SimpleSoundClass implements SoundClass {
    private String className;
    private int classId;
    private String mediaPath;
    private double duration;
    private Map<String, String> propertiesMap;

    @XStreamOmitField
    private transient SoundInstanceFactory myFactory;
    @XStreamOmitField
    private transient ThrowingBiConsumer<String, String, InvalidOperationException> changeSoundClassNameFunc;
    @XStreamOmitField
    private transient Function<String, Collection<GameObjectInstance>> getAllSoundInstancesFunc;
    @XStreamOmitField
    private transient Function<Integer, Boolean> deleteSoundInstanceFunc;

    public SimpleSoundClass(String className) {
        this.className = className;
        classId = 0;
        duration = 0;
        mediaPath = "";
        propertiesMap = new HashMap<>();
        duration = 0;
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
    public int getClassId() {
        return classId;
    }

    /**
     * This method receives a function that sets the id of the GameObject Class.
     * The id of the GameObject Class is set by the received function.
     */
    @Override
    public void setClassId(int classId) {
        this.classId = classId;
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
    public void changeClassName(String newClassName) throws InvalidOperationException {
        changeSoundClassNameFunc.accept(className, newClassName);
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
        Set<SoundInstance> s = new HashSet<>();
        Collection<GameObjectInstance> instances = getAllSoundInstancesFunc.apply(getClassName());
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
    public String getMediaFilePath() {
        return mediaPath;
    }

    @Override
    public void setMediaFilePath(String mediaFilePath) {

    }

    @Override
    public double getDuration() {
        return duration;
    }

    @Override
    public void setDuration(double newDuration) {
        duration = newDuration;
    }

    @Override
    public void equipContext(
            SoundInstanceFactory soundInstanceFactory,
            ThrowingBiConsumer<String, String, InvalidOperationException> changeSoundClassNameFunc,
            Function<String, Collection<GameObjectInstance>> getAllSoundInstancesFunc,
            Function<Integer, Boolean> deleteSoundInstanceFunc
    ) {
        this.myFactory = soundInstanceFactory;
        this.changeSoundClassNameFunc = changeSoundClassNameFunc;
        this.getAllSoundInstancesFunc = getAllSoundInstancesFunc;
        this.deleteSoundInstanceFunc = deleteSoundInstanceFunc;
    }
}