package gameObjects.sound;

import gameObjects.gameObject.GameObjectInstance;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
import java.util.function.Consumer;

public class SimpleSoundClass implements SoundClass {
    /**
     * This method sets the id of the GameObject Class.
     *
     * @return classId
     */
    @Override
    public ReadOnlyIntegerProperty getClassId() {
        return null;
    }

    /**
     * This method receives a function that sets the id of the GameObject Class.
     * The id of the GameObject Class is set by the received function.
     *
     * @param setFunc the function from IdManager that sets the id
     */
    @Override
    public void setClassId(Consumer<SimpleIntegerProperty> setFunc) {

    }

    /**
     * This method gets the name of this GameObject Class.
     *
     * @return class name
     */
    @Override
    public ReadOnlyStringProperty getClassName() {
        return null;
    }

    @Override
    public void changeClassName(String newClassName) {

    }

    @Override
    public void setClassName(String newClassName) {

    }

    /**
     * This method receives a function that sets the name of the GameObject Class.
     * The name of the GameObject Class is set by the received function.
     *
     * @param setFunc the function that sets the class name
     */

    /**
     * This method gets the properties map of the GameObject Class.
     *
     * @return properties map
     */
    @Override
    public ObservableMap<String, String> getPropertiesMap() {
        return null;
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
        return false;
    }

    /**
     * This method removes the property from the GameObject Class and from all instances of the class.
     *
     * @param propertyName property name to be removed
     * @return true if the property name exists in the properties map
     */
    @Override
    public boolean removeProperty(String propertyName) {
        return false;
    }

    /**
     * This method returns all of the instance of the GameObject Class.
     *
     * @return the set of all instances of the class
     */
    @Override
    public Set<GameObjectInstance> getAllInstances() {
        return null;
    }

    /**
     * This method removes the instance with the specified instance id
     *
     * @param id id of the instance
     * @return true if the instance exists
     */
    @Override
    public boolean deleteInstance(int id) {
        return false;
    }

    @Override
    public SoundInstance createInstance() {
        return null;
    }

    @Override
    public String getMediaFilePath() {
        return null;
    }

    @Override
    public void setMediaFilePath(String mediaFilePath) {

    }
}
