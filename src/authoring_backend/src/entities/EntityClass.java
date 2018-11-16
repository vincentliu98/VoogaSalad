package entities;

import groovy.api.BlockGraph;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface EntityClass {

    void setDefaultHeightWidth(int defaultHeight, int defaultWidth);

    SimpleIntegerProperty getDefaultHeight();

    SimpleIntegerProperty getDefaultWidth();

    ReadOnlyIntegerProperty getClassId();

    void setClassId(Consumer<SimpleIntegerProperty> setFunc);

    Supplier<ReadOnlyIntegerProperty> returnClassId();

    ObservableMap getPropertiesMap();

    /**
     * This method adds the property to the Entity Class and to all instances of the class.
     * @param propertyName
     * @param defaultValue
     * @return
     */
    boolean addProperty(String propertyName, BlockGraph defaultValue);

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

    void setImageSelector(BlockGraph blockCode);

    BlockGraph getImageSelectorCode();

    EntityInstance createInstance();


}
