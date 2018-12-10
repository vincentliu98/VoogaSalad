package gameObjects.gameObject;

import authoringUtils.exception.InvalidOperationException;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableMap;

import java.util.Map;
import java.util.function.Consumer;

/**
 *
 *
 * @author Jason Zhou
 */
public interface GameObjectInstance {

    /**
     *
     * @return
     */
    int getInstanceId();

    /**
     *
     * @param newId
     */
    void setInstanceId(int newId);

    /**
     *
     * @return
     */
    String getClassName();

    /**
     *
     * @param name
     */
    void setClassName(String name) throws InvalidOperationException;

    /**
     *
     * @return
     */
//    Consumer<GameObjectInstance> getReturnInstanceIdFunc();

    String getInstanceName();

    void setInstanceName(String instanceName);


    /**
     * This method gets the properties map of the GameObject Class.
     * @return properties map
     */
    Map<String, String> getPropertiesMap();

    /**
     * This method adds the property to the GameObject Class and to all instances of the class.
     * @param propertyName property name
     * @param defaultValue default value of the property in GroovyCode
     */
    void addProperty(String propertyName, String defaultValue);

    /**
     * This method removes the property from the GameObject Class and from all instances of the class.
     * @param propertyName property name to be removed
     */
    void removeProperty(String propertyName);


    /**
     *
     * @param propertyName
     * @param newValue
     * @return
     */
    boolean changePropertyValue(String propertyName, String newValue);

    /**
     * @return The GameObjectType enum variable for this GameObjectInstance object.
     */
    default GameObjectType getType() {
        return GameObjectType.UNSPECIFIED;
    }
}
