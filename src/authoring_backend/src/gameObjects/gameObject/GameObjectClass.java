package gameObjects.gameObject;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import java.util.Set;
import java.util.function.Consumer;

/**
 * This interface is implemented by Tile and Entity Classes.
 * Supports editing of properties of the GameObject Classes
 * @author Jason Zhou
 *
 */
public interface GameObjectClass {

    /**
     * This method sets the id of the GameObject Class.
     * @return classId
     */
    ReadOnlyIntegerProperty getClassId();

    /**
     * This method receives a function that sets the id of the GameObject Class.
     * The id of the GameObject Class is set by the received function.
     * @param setFunc the function from IdManager that sets the id
     */
    void setClassId(Consumer<SimpleIntegerProperty> setFunc);

    /**
     * This method gets the name of this GameObject Class.
     * @return class name
     */
    ReadOnlyStringProperty getClassName();

    /**
     * This method receives a function that sets the name of the GameObject Class.
     * The name of the GameObject Class is set by the received function.
     * @param setFunc the function that sets the class name
     */
    void setClassName(Consumer<SimpleStringProperty> setFunc);

    /**
     * This method gets the properties map of the GameObject Class.
     * @return properties map
     */
    ObservableMap<String, String> getPropertiesMap();

    /**
     * This method adds the property to the GameObject Class and to all instances of the class.
     * @param propertyName property name
     * @param defaultValue default value of the property in GroovyCode
     * @return true if property is successfully added
     */
    boolean addProperty(String propertyName, String defaultValue);

    /**
     * This method removes the property from the GameObject Class and from all instances of the class.
     * @param propertyName property name to be removed
     * @return true if the property name exists in the properties map
     */
    boolean removeProperty(String propertyName);

    /**
     * This method adds the image path to the GameObject Class and to all instances of the class.
     * @param path file path of the image
     */
    void addImagePath(String path);

    /**
     * This method gets the image path list of the GameObject Class.
     * @return a list of the file paths of the images
     */
    ObservableList<String> getImagePathList();

    /**
     * This method removes the image path from the Entity Class and from all instances of the class.
     * @param index index of the image file path in the list
     * @return true if the image file path successfully removed
     */
    boolean removeImagePath(int index);

    /**
     * This method sets the GroovyCode for choosing the image to display from the list of images.
     * @param blockCode GroovyCode
     */
    void setImageSelector(String blockCode);

    /**
     * This method gets the image selector code.
     * @return image selector code
     */
    String getImageSelectorCode();

    /**
     * This method returns all of the instance of the GameObject Class.
     * @return the set of all instances of the class
     */
    Set<GameObjectInstance> getInstances();

    /**
     * This method removes the instance with the specified instance id
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
