package gameObjects.gameObject;

import authoringUtils.exception.InvalidOperationException;

import java.util.Collection;
import java.util.Map;

/**
 * This interface is implemented by Tile and Entity Classes.
 * Supports editing of properties of the GameObject Classes
 *
 * @author Jason Zhou
 */
public interface GameObjectClass {

    /**
     * This method sets the id of the GameObject Class.
     *
     * @return classId
     */
    int getClassId();

    /**
     * This method receives a function that sets the id of the GameObject Class.
     * The id of the GameObject Class is set by the received function.
     */
    void setClassId(int newId);

    /**
     * This method gets the name of this GameObject Class.
     *
     * @return class name
     */
    String getClassName();

    /**
     * This method sets the name of the GameObject Class.
     * This method should only be used by the CRUD Interface.
     * This method should not be used externally.
     * The name of the GameObject Class is set by the received function.
     *
     * @param newClassName the function that sets the class name
     */
    void setClassName(String newClassName);

    void changeClassName(String newClassName) throws InvalidOperationException;

    /**
     * This method gets the properties map of the GameObject Class.
     *
     * @return properties map
     */
    Map<String, String> getPropertiesMap();

    /**
     * This method adds the property to the GameObject Class and to all instances of the class.
     *
     * @param propertyName property name
     * @param defaultValue default value of the property in GroovyCode
     * @return true if property is successfully added
     */
    boolean addProperty(String propertyName, String defaultValue);

    /**
     * This method removes the property from the GameObject Class and from all instances of the class.
     *
     * @param propertyName property name to be removed
     * @return true if the property name exists in the properties map
     */
    boolean removeProperty(String propertyName);

    /**
     * This method returns all of the instance of the GameObject Class.
     *
     * @return the set of all instances of the class
     */
    Collection<? extends GameObjectInstance> getAllInstances();

    /**
     * This method removes the instance with the specified instance id
     *
     * @param id id of the instance
     * @return true if the instance exists
     */
    boolean deleteInstance(int id);


    /**
     * @return The GameObjectType enum variable for this GameObjectClass
     */
    default GameObjectType getType() {
        return GameObjectType.UNSPECIFIED;
    }
}
