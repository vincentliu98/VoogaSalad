package gameObjects;

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
 *
 * @author Jason Zhou
 *
 */
public interface GameObjectClass {

    /**
     *
     * This method sets the id of the GameObject Class
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
     *
     * 
     * @return
     */
    ObservableMap getPropertiesMap();

    /**
     * This method adds the property to the GameObject Class and to all instances of the class.
     * @param propertyName
     * @param defaultValue
     * @return
     */
    boolean addProperty(String propertyName, String defaultValue);

    /**
     * This method removes the property from the GameObject Class and from all instances of the class.
     * @param propertyName
     * @return
     */
    boolean removeProperty(String propertyName);

    ObservableList getImagePathList();

    /**
     * This method adds the image path to the GameObject Class and to all instances of the class.
     * @param path
     */
    void addImagePath(String path);

    /**
     * This method removes the image path from the Entity Class and from all instances of the class.
     * @param index
     * @return
     */
    boolean removeImagePath(int index);

    /**
     *
     * @param blockCode
     */
    void setImageSelector(String blockCode);

    /**
     *
     * @return
     */
    String getImageSelectorCode();

    /**
     *
     * @return
     */
    Set<GameObjectInstance> getInstances();

    /**
     *
     * @param id
     * @return
     */
    boolean deleteInstance(int id);

}
