package entities;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Set;
import java.util.function.Consumer;

public interface EntityClass {

    ReadOnlyIntegerProperty getClassId();

    void setClassId(Consumer<SimpleIntegerProperty> setFunc);

    ReadOnlyStringProperty getClassName();

    void setClassName(Consumer<SimpleStringProperty> setFunc);

    ObservableMap getPropertiesMap();

    /**
     * This method adds the property to the Entity Class and to all instances of the class.
     * @param propertyName
     * @param defaultValue
     * @return
     */
    boolean addProperty(String propertyName, String defaultValue);

    /**
     * This method removes the property from the Entity Class and from all instances of the class.
     * @param propertyName
     * @return
     */
    boolean removeProperty(String propertyName);

    ObservableList getImagePathList();

    /**
     * This method adds the image path to the Entity Class and to all instances of the class.
     * @param path
     */
    void addImagePath(String path);

    /**
     * This method removes the image path from the Entity Class and from all instances of the class.
     * @param index
     * @return
     */
    boolean removeImagePath(int index);

    void setImageSelector(String blockCode);

    String getImageSelectorCode();

    Set<EntityInstance> getInstances();

}
